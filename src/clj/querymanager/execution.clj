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
    (Table. nil (apply concat headers) tuples)))

(defn- build-regex
  "Builds a regex from SQL pattern string s"
  [s]
  (-> s
      Pattern/quote
      (clojure.string/replace "''" "'")
      (clojure.string/replace "_" "\\E.\\Q")
      (clojure.string/replace "%" "\\E.*\\Q")
      re-pattern))

(defn- check-get-val
  "Check whether tuples contain only one tuple of a single attribute.
If so, return the value of the attribute.
Ohterwise, throw an exception"
  [tuples]
  (if (or (> (count tuples) 1)
          (> (count (first tuples)) 1))
    (throw (Exception. "Invalid subquery."))
    (ffirst tuples)))

(defn- extend-env [[header tuple] new-header new-tuple]
  [(concat (if header header []) new-header)
   (concat (if tuple tuple []) new-tuple)])

(def comp-ops
  {:EQ =,:NEQ not=,
   :LT #(< (compare %1 %2) 0),
   :GT #(> (compare %1 %2) 0),
   :LE #(<= (compare %1 %2) 0),
   :GE #(>= (compare %1 %2) 0)})
(declare exec)
(defmulti selection-test
  (fn [[test-type & _] tuples header outer-env]
    (if (comp-ops test-type)
      :COMP
      test-type)))
(defmethod selection-test :COMP
  [[test-type & args] tuples header outer-env]
  (let [[acc1 acc2] (map #(val-accessor % header outer-env) args)
        op (test-type comp-ops)]
    (separate #(op (acc1 %) (acc2 %)) tuples)))
(defmethod selection-test :RANGE
  [[_ f attr lower upper] tuples header outer-env]
  (let [op (comp-ops :LT)
        [acc accl accu] (map #(val-accessor % header outer-env)
                             [attr lower upper])]
    (separate #(f (and (op (accl lower) (acc %))
                       (op (acc %) (accu upper))))
              tuples)))
(defmethod selection-test :IN
  [[_ f attr valueset] tuples header outer-env]
  (let [acc (val-accessor attr header outer-env)]
    (separate #(f (valueset (acc %)))
              tuples)))
(defmethod selection-test :LIKE
  [[_ f attr pattern] tuples header outer-env]
  (let [acc (val-accessor attr header outer-env)]
    (separate #(f (re-matches (build-regex pattern)
                              (acc %)))
              tuples)))
(defmethod selection-test :QUERY
  [[_ test-type & args] tuples header outer-env]
  (cond (comp-ops test-type)
        (let [[attr qualifier query] args
              qualifier-f (cond (nil? qualifier)
                                (fn [op val ts]
                                  (op val (check-get-val ts)))
                                (= qualifier :ALL)
                                (fn [op val ts]
                                  (every? #(op val %) (apply concat ts)))
                                (= qualifier :ANY)
                                (fn [op val ts]
                                  (some #(op val %) (apply concat ts))))
              acc (val-accessor attr header outer-env)
              op (comp-ops test-type)]
          (separate #(qualifier-f op
                                  (acc %)
                                  (:tuples (exec query
                                                 (extend-env outer-env
                                                             header %))))
                    tuples))
        (= test-type :RANGE)
        (throw (Exception. "RANGE subquery not implemented yet"))
        (= test-type :IN)
        (let [[f attr query] args
              acc (val-accessor attr header outer-env)
              ]
          (separate #(f ((set (map first
                                   (:tuples (exec query
                                                  (extend-env outer-env
                                                              header %)))))
                         (acc %)))
                    tuples))))
(defmethod selection-test :default
  [_ _ _ _]
  (println "not implemented yet"))

(defn- selection-and [and-conds tuples header outer-env]
  (reduce (fn [res c]
            (let [[p f] (selection-test c (first res) header outer-env)]
              [p (concat (last res) f)]))
          [tuples []]
          and-conds))

(defmulti exec (fn [exp outer-env] (first exp)))
(defmethod exec :from [[_ & ts] outer-env]
  (let [tables
        (apply map (fn [t]
                     (cond (instance? Table t) t
                           (t :table)
                           (create-table (t :table) (t :alias))
                           (t :query)
                           (create-table (exec (t :query) outer-env)
                                         (t :alias))))
               ts)]
    (table-product tables)))
(defmethod exec :where [[_ conditions table] outer-env]
  (let [{:keys [tuples header] :as table} (exec table outer-env)]
    (if conditions
      (let [[tuples]
            (reduce (fn [res and-c]
                      (let [[p f]
                            (selection-and and-c (last res) header outer-env)]
                        [(concat (first res) p) f]))
                    [[] tuples]
                    conditions)]
        (Table. nil header tuples))
      table)))
(defmethod exec :query [[_ {:keys [select from where]}] outer-env]
  (let [{:keys [header tuples]} (exec [:where where [:from from]] outer-env)
        [attrs aliases] (reduce (fn [[r1 r2] [a1 a2]]
                                  [(conj r1 a1)
                                   (conj r2 (if a2 [nil a2] a1))])
                                [[] []]
                                select)
        val-accessors (map  #(val-accessor % header outer-env) attrs)
        project (fn [tuple]
                  (reduce #(conj %1 (%2 tuple))
                          [] val-accessors))]
    (Table. nil aliases (map project tuples))))

