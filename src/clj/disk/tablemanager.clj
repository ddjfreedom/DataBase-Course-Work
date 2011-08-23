(ns disk.tablemanager
  (:use [clojure.string :only [split]]
        [clojure.contrib.duck-streams :only [read-lines]]
        [clojure.contrib.seq-utils :only [positions]]))

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

(defn- attr-eq? [[table-name attr-name]
                 [header-table-name header-attr-name]]
  (and (or (nil? table-name)
           (= table-name header-table-name))
       (= attr-name header-attr-name)))

(defn attr? [v] (vector? v))

(defn val-accessor
  [val header]
  (if (attr? val)
    (let [column (first (positions #(attr-eq? val %) header))]
      #(nth % column))
    (fn [t] val)))