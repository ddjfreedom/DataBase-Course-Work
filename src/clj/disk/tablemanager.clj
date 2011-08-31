(ns disk.tablemanager
  (:use [clojure.string :only [split]]
        [clojure.contrib.duck-streams :only [read-lines]]
        [clojure.contrib.seq-utils :only [positions]]))

(defrecord Table [name header tuples])

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

(defmulti create-table (fn [table alias] (class table)))
(defmethod create-table String [name alias]
  (let [full-tuples (get-tuples name)
        header (first full-tuples)]
    (Table. name
            (map (fn [attr] [alias attr]) header)
            (rest full-tuples))))
(defmethod create-table disk.tablemanager.Table [table alias]
  (assoc table :header
         (map (fn [[t a]] [alias a]) (:header table))))
(defn- attr-eq? [[table-name attr-name]
                 [header-table-name header-attr-name]]
  (and (or (nil? table-name)
           (= table-name header-table-name))
       (= attr-name header-attr-name)))

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