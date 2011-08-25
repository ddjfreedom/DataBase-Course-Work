(ns database.core
  (:use [querymanager.execution :only [parse exec]]
        [clojure.pprint :only [pprint]])
  (:gen-class))

(defn -main [& args]
  (do (pprint (exec (parse (first args)) nil))
      (prn)))
