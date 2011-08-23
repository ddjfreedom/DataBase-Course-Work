(ns querymanager.execution
  (:use [querymanager.transform :only [java2cljmap transform]]
        [disk.tablemanager :only [create-table attr? val-accessor]]
        [clojure.contrib.combinatorics :only [cartesian-product]]
        [clojure.contrib.seq-utils :only [separate]])
  (:require [clojure.string :only [replace]])
  (:import [querymanager.lexical Yylex]
           [querymanager.syntax DBParser sym]
           [java.io FileInputStream]
           [java_cup.runtime Symbol]
           [java.util.regex Pattern]
           [disk.tablemanager Table]))

(defn lex [file-name]
  (with-open [fis (FileInputStream. file-name)]
    (let [lex (Yylex. fis)]
      (loop [tok (.next_token lex)]
        (when-not (= (.sym tok) sym/EOF)
          (println (.value tok) " " (.sym tok))
          (recur (.next_token lex)))))))

(defn parse
  ([file-name sel]
     (with-open [fis (FileInputStream. file-name)]
       (let [parse-tree (-> fis
                            Yylex.
                            DBParser.
                            .parse)]
         (if sel
           (java2cljmap (.value parse-tree))
           (transform (.value parse-tree))))))
  ([file-name] (parse file-name nil)))

(defn table-product [tables]
  (let [headers (map #(:header %) tables)
        tuples  (map (fn [t] (apply concat t))
                     (apply cartesian-product
                            (map #(:tuples %) tables)))]
    (Table. (apply concat headers) tuples)))

(defn- build-regex
  "Builds a regex from SQL pattern string s"
  [s]
  (-> s
      Pattern/quote
      (clojure.string/replace "''" "'")
      (clojure.string/replace "_" "\\E.\\Q")
      (clojure.string/replace "%" "\\E.*\\Q")
      re-pattern))

(let [comp-ops {:EQ =,:NEQ not=,
                :LT #(< (compare %1 %2) 0),
                :GT #(> (compare %1 %2) 0),
                :LE #(<= (compare %1 %2) 0),
                :GE #(>= (compare %1 %2) 0)}]
  (defn- selection-test [condition tuples header]
    (let [[test-type & args] condition]
      (cond (test-type comp-ops)
            (let [[acc1 acc2] (map #(val-accessor % header) args)
                  op (test-type comp-ops)]
              (separate #(op (acc1 %) (acc2 %)) tuples))
            ;; Range has the form [:RANGE f attr lower upper]
            ;; where f is either identity or not
            (= test-type :RANGE)
            (let [[f attr lower upper] args
                  op (comp-ops :LT)
                  [acc accl accu] (map #(val-accessor % header)
                                       [attr lower upper])]
              (separate #(f (and (op (accl lower) (acc %))
                                 (op (acc %) (accu upper))))
                        tuples))
            ;; In has the form [:IN f attr val-set]
            ;; where f is either identity or not
            (= test-type :IN)
            (let [[f attr valueset] args
                  acc (val-accessor attr header)]
              (separate #(f (valueset (acc %)))
                        tuples))
            (= test-type :LIKE)
            (let [[f attr pattern] args
                  acc (val-accessor attr header)]
              (separate #(f (re-matches (build-regex pattern)
                                        (acc %)))
                        tuples))
            :else (println "not implemented yet")))))

(defn- selection-and [and-conds tuples header]
  (reduce (fn [res c]
            (let [[p f] (selection-test c (first res) header)]
              [p (concat (last res) f)]))
          [tuples []]
          and-conds))

(defmulti exec first)
(defmethod exec :product [e]
  (let [tables (apply map (fn [t]
                            (if (instance? Table t)
                              t
                              (create-table t)))
                    (rest e))]
    (table-product tables)))
(defmethod exec :selection [e]
  (let [[conditions table] (rest e)
        {:keys [tuples header] :as table} (exec table)]
    (if conditions
      (let [[tuples] (reduce (fn [res and-c]
                       (let [[p f] (selection-and and-c (last res) header)]
                         [(concat (first res) p) f]))
                     [[] tuples]
                     conditions)]
        (Table. header tuples))
      table)))
(defmethod exec :projection [e]
  (let [[attrs table] (rest e)
        [attrs aliases] (reduce (fn [[r1 r2] [a1 a2]]
                                  [(conj r1 a1)
                                   (conj r2 (if a2 [nil a2] a1))])
                                [[] []]
                                attrs)
        {:keys [header tuples]} (exec table)
        val-accessors (map  #(val-accessor % header) attrs)
        project (fn [tuple]
                  (reduce #(conj %1 (%2 tuple))
                          [] val-accessors))]
    (Table. aliases (map project tuples))))

