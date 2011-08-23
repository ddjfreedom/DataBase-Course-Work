(ns querymanager.execution
  (:use [querymanager.transform :only [java2cljmap transform]]
        [clojure.contrib.duck-streams :only [read-lines]]
        [clojure.string :only [split]]
        [clojure.contrib.combinatorics :only [cartesian-product]]
        [clojure.contrib.seq-utils :only [positions separate]])
  (:require [clojure.string :only [replace]])
  (:import [querymanager.lexical Yylex]
           [querymanager.syntax DBParser sym]
           [java.io FileInputStream]
           [java_cup.runtime Symbol]
           [java.util.regex Pattern]))

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
       (let [lex (Yylex. fis)
             syn (DBParser. lex)
             parse-tree (.parse syn)]
         (if sel
           (java2cljmap (.value parse-tree))
           (transform (.value parse-tree))))))
  ([file-name] (parse file-name nil)))

(defrecord Table [header tuples])

(defn- get-tuples [name]
  (loop [tuples (map #(split % #":") (read-lines name))
         res []]
    (if (seq tuples)
      (recur (rest tuples)
             (conj res
                   (map #(try (Integer/parseInt %)
                              (catch NumberFormatException e %))
                        (first tuples))))
      res)))

(defn create-table [name]
  (let [full-tuples (get-tuples name)
        header (first full-tuples)]
    (Table. (map (fn [attr] [name attr]) header)
            (rest full-tuples))))

(defn table-product [tables]
  (let [headers (map (fn [{:keys [name header]}]
                       (if name
                         (map (fn [h] [name h]) header)
                         header))
                     tables)
        tuples  (map (fn [t] (apply concat t))
                     (apply cartesian-product
                            (map #(:tuples %) tables)))]
    (Table. (apply concat headers) tuples)))

(defn- attr-eq? [[tname aname] [htname haname]]
  (and (or (nil? tname)
           (= tname htname))
       (= aname haname)))

(defn- get-column [attr header]
  (first (positions #(attr-eq? attr %) header)))

(defn- attr? [v] (vector? v))

(defn- compare-test
  ([header comp-f [v1 v2] tuple]
     (compare-test header comp-f v1 v2 tuple))
  ([header comp-f v1 v2 tuple]
     (let [col1 (when (attr? v1) (get-column v1 header))
           col2 (when (attr? v2) (get-column v2 header))]
       (cond (and col1 col2)
             (comp-f (nth tuple col1) (nth tuple col2))
             col1 (comp-f (nth tuple col1) v2)
             col2 (comp-f v1 (nth tuple col2))
             :else (comp-f v1 v2)))))

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
    (let [[test-type & args] condition
          tuple-test (partial compare-test header)]
      (cond (test-type comp-ops)
            (separate #(tuple-test (test-type comp-ops) args %)
                      tuples)
            ;; Range has the form [:RANGE f attr lower upper]
            ;; where f is either identity or not
            (= test-type :RANGE)
            (let [[f attr lower upper] args
                  op (comp-ops :LT)]
              (separate #(f (and (tuple-test op lower attr %)
                                 (tuple-test op attr upper %)))
                        tuples))
            ;; In has the form [:IN f attr val-set]
            ;; where f is either identity or not
            (= test-type :IN)
            (let [[f attr r] args]
              (separate #(f (r (if (attr? attr)
                                 (nth % (get-column attr header))
                                 attr)))
                        tuples))
            (= test-type :LIKE)
            (let [[f attr pattern] args]
              (separate #(f (re-matches (build-regex pattern)
                                        (if (attr? attr)
                                          (nth % (get-column attr header))
                                          attr)))
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
                                   (if a2
                                     (conj r2 [nil a2])
                                     (conj r2 a1))])
                                [[] []]
                                attrs)
        {:keys [header tuples]} (exec table)
        attr-columns (map  #(get-column % header) attrs)
        project (fn [tuple]
                  (reduce #(conj %1 (nth tuple %2))
                          [] attr-columns))]
    (Table. aliases (map project tuples))))

