(use 'clojure.contrib.shell-out)

(deftask codegen #{"src/jvm/querymanager/lexical/DBLex.lex"}
  (println "begin")
  (let [lexpath "src/jvm/querymanager/lexical/"
        lexarr (into-array String [(str lexpath "DBLex.lex")
                                   (str lexpath "DBLex.java")])
        cuppath "src/jvm/querymanager/syntax/"
        cuparr (into-array String ["-package" "querymanager.syntax"
                                   "-expect" "2"
                                   "-parser" "DBParser"
                                   (str cuppath "DBParse.cup")])]
    (jlex.Main/main lexarr)
    (java_cup.Main/main cuparr)
    (sh "mv" "sym.java" "DBParser.java" cuppath))
  (println "done"))