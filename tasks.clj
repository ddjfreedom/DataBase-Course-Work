(use 'clojure.contrib.shell-out)
(let [lexpath "src/jvm/querymanager/lexical/"
      cuppath "src/jvm/querymanager/syntax/"]
  (deftask codegen
    (let [lexs (map #(str lexpath %) ["DBLex.lex" "Yylex.java"])
          cups (map #(str cuppath %) ["DBParse.cup" "DBParser.java"])
          cuparr (into-array String ["-expect" "8"
                                     "-parser" "DBParser"
                                     (first cups)])]
      (when (apply > (map #(-> % java.io.File. .lastModified) lexs))
        (jlex.Main/main (into-array String lexs)))
      (when (apply > (map #(-> % java.io.File. .lastModified) cups))
        (java_cup.Main/main cuparr)
        (sh "mv" "sym.java" "DBParser.java" cuppath)))))

(deftask compile-java #{codegen})

