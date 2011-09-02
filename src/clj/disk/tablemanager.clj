(ns disk.tablemanager
  (:use [clojure.string :only [split]]
        [clojure.contrib.duck-streams :only [read-lines]]
        [clojure.contrib.seq-utils :only [positions]]
        [clojure.java.shell :only [sh]])
  (:import [java.io BufferedWriter FileWriter]
           [java.util ArrayList]
           [disk RecordTable]))

(defrecord Table [name header type constraint tuples])

(def cache (atom {}))

(defn- get-tuples [name]
  (let [record (doto (RecordTable. name)
                 (.readFromDisk))]
    (into [(split (.getMetaData record) #":")]
          (map #(split % #":") (vec (.getRecords record))))))

(defn- type-cast [types tuples]
  (map #(map (fn [[t _] v]
               (cond (or (= t :INT) (= t :SMALLINT)) (Integer/parseInt v)
                     (or (= t :REAL) (= t :FLOAT)) (Double/parseDouble v)
                     :else v))
             types %)
       tuples))
(defmulti read-table (fn [table alias] (class table)))
(defmethod read-table String [name alias]
  (if (@cache name)
    (@cache name)
    (let [full-tuples (get-tuples name)
          [header types keycons] (reduce (fn [[ns ts ks] p]
                                           (let [[name t num k] (split p #",")]
                                             [(conj ns name)
                                              (conj ts [(keyword t)
                                                        (Integer/parseInt num)])
                                              (if (nil? k)
                                                ks
                                                (assoc ks (keyword k) name))]))
                                         [[] [] {}]
                                         (first full-tuples))]
      ((swap! cache assoc name
              (Table. name
                      (map (fn [attr] [alias attr]) header)
                      types
                      keycons
                      (type-cast types (rest full-tuples))))
       name))))
(defmethod read-table disk.tablemanager.Table [table alias]
  (assoc table :header
         (map (fn [[t a]] [alias a]) (:header table))))

(defn- attr-eq? [[table-name attr-name]
                 [header-table-name header-attr-name]]
  (and (or (nil? table-name)
           (= table-name header-table-name))
       (= attr-name header-attr-name)))

(defn-  cons-str-with-sep [sep coll]
  (apply str (interpose sep coll)))

(defn create-table [name attrs]
  (let [[attr-names types constraints] (map #(map % attrs)
                                            [:attr :type :restrict])
        properties  (map (fn [name [t num] c]
                           (str name "," (.getName t) "," num
                                (if (keyword? c)
                                  (str "," (.getName c))
                                  "")))
                         attr-names types constraints)
        record (doto (RecordTable. name)
                 (.setMetaData (cons-str-with-sep ":" properties)))]
    (.writeToDisk record)))

(defn attr? [v] (vector? v))

(defn val-accessor
  [val header [outer-header outer-tuple]]
  (if (attr? val)
    (if-let [column (first (positions #(attr-eq? val %) header))]
      #(nth % column)
      (if-let [column (first (positions #(attr-eq? val %)
                                 outer-header))]
        (fn [t] (nth outer-tuple column))
        (throw (Exception. "Invalid Attribute specification"))))
    (fn [t] val)))

(defn write-table [{:keys [name header type constraint tuples]}]
  (let [constraint (into {} (map (fn [[k v]] [v k]) constraint))
        properties (map (fn [[_ attr] [t num]]
                          (str attr "," (.getName t) "," num
                               (if-let [c (constraint attr)]
                                 (str "," (.getName c))
                                 "")))
                        header type)
        record (doto (RecordTable. name)
                 (.setMetaData (cons-str-with-sep ":" properties))
                 (.setRecords (ArrayList. (vec (map #(cons-str-with-sep ":" %)
                                                    tuples)))))]
    (.writeToDisk record)))

(defn insert [{:keys [name tuples] :as table} attrs values]
  (let [new-table (assoc table :tuples (conj tuples values))]
    (write-table ((swap! cache assoc name new-table) name))))

(defn drop-table [name]
  (.deleteTable (RecordTable. name)))

(defn create-view [name query]
  (swap! cache assoc name query))
(defn drop-view [name]
  (swap! cache dissoc name))