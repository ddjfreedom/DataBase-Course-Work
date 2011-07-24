(ns database.core
  (:gen-class))

(defn -main [& args]
  (let [arr (make-array String 1)]
    (do (aset arr 0 (first args))
        (querymanager.lexical.LexicalTest/main arr))))
