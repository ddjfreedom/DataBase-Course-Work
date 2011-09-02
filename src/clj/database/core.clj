(ns database.core
  (:use [querymanager.execution :only [parse exec]]
        [clojure.pprint :only [pprint]]
        [clojure.string :only [split blank?]])
  (:import [java.io FileInputStream]
           [disk DiskManager])
  (:gen-class))

(defn- read-exp [delim]
  (loop [line (read-line) res []]
    (if (= (last line) delim)
      (apply str (interpose " " (conj res line)))
      (recur (read-line) (conj res line)))))

(defn- print-table [{:keys [tuples]}]
  (println (apply str (take 10 (repeat \-))))
  (let [tuples (map #(map str %) (doall tuples))
        lens (map (fn [idx]
                    (apply max (map count (map #(nth % idx) tuples))))
                  (range (count (first tuples))))
        padding (fn [len s]
                  (str (apply str (vec (take (- len (count s))
                                             (repeat " "))))
                       s))]
    (doseq [tuple (map #(map padding lens %) tuples)]
      (apply println (interpose "|" tuple))))
  (println))

(defn- exec-expr [expr]
  (let [res (try (exec (parse expr) nil)
                 (catch Exception e (println "Error:" (.getMessage e))))]
    (when (instance? disk.tablemanager.Table res)
      (print-table res))))

(defn- driver []
  (println "1: [d]irect input" "2: [l]oad file" "3: [q]uit")
  (println "Please select: ")
  (let [choice (first (read-line))]
    (cond (some #(= choice %) [\1 \d])
          (do (println "Please enter expression:")
              (exec-expr (read-exp \;))
              (recur))
          (some #(= choice %) [\2 \l])
          (do (println "Which file: ")
              (doseq [val (remove blank? (split (slurp (read-line)) #";"))]
                (exec-expr val))
              (recur))
          (some #(= choice %) [\3 \q]) nil
          :else (do (println "Unknown operation, please try again")
                    (recur)))))

(defn -main [& args]
  (let [dbdisk (DiskManager/getInstance)]
    (do (.openDB dbdisk)
        (driver)
        (.closeDB dbdisk))))
