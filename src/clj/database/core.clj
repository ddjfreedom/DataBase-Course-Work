(ns database.core
  (:use [querymanager.execution :only [parse lex]]
        [clojure.pprint :only [pprint]])
  (:gen-class))

(defn -main [& args]
  (do (pprint (parse (first args)))
      (prn)))
