(ns disk.tablemanager
  (:use [clojure.string :only [split]]
        [clojure.contrib.duck-streams :only [read-lines]]
        [clojure.contrib.seq-utils :only [positions]])
  (:import [java.io BufferedWriter FileWriter]))

(defrecord Table [name header type constraint tuples])

(defn- get-tuples [name]
  (loop [tuples (map #(split % #":") (read-lines (str "tables/" name)))
         res []]
    (if (seq tuples)
      (recur (rest tuples)
             (conj res
                   (map #(try (Integer/parseInt %)
                              (catch NumberFormatException e %))
                        (first tuples))))
      res)))

(defmulti read-table (fn [table alias] (class table)))
(defmethod read-table String [name alias]
  (let [full-tuples (get-tuples name)
        [header types keycons] (reduce (fn [[ns ts ks] p]
                                         (let [[name t num k] (split p #",")]
                                           [(conj ns name)
                                            (conj ts [t num])
                                            (if (nil? k)
                                              ks
                                              (assoc ks (keyword k) name))]))
                                       [[]  [] {}]
                                       (first full-tuples))]
    (Table. name
            (map (fn [attr] [alias attr]) header)
            types
            keycons
            (rest full-tuples))))
(defmethod read-table disk.tablemanager.Table [table alias]
  (assoc table :header
         (map (fn [[t a]] [alias a]) (:header table))))

(defn- attr-eq? [[table-name attr-name]
                 [header-table-name header-attr-name]]
  (and (or (nil? table-name)
           (= table-name header-table-name))
       (= attr-name header-attr-name)))

(defn create-table [name attrs]
  (let [[attr-names types constraints] (map #(map % attrs)
                                            [:attr :type :restrict])
        properties  (map (fn [name [t num] c]
                           (str name "," (.getName t) "," num
                                (if (keyword? c)
                                  (str "," (.getName c))
                                  "")))
                         attr-names types constraints)
        cons-str-with-sep #(apply str (interpose %2 %1))]
    (with-open [wtr (BufferedWriter. (FileWriter. name))]
      (.write wtr
              (with-out-str
                (println (cons-str-with-sep properties ":")))))))

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