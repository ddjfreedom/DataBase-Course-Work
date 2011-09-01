(ns database.core
  (:use [querymanager.execution :only [parse exec]]
        [clojure.pprint :only [pprint]])
  (:import [disk DiskManager])
  (:gen-class))

(defn -main [& args]
  (let [dbdisk (DiskManager/getInstance)]
    (do (.openDB dbdisk)
        (pprint (exec (parse (first args)) nil))
        (prn)
        (.closeDB dbdisk))))
