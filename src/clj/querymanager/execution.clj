(ns querymanager.execution
  (:use [querymanager.transform :only [transform]])
  (:import [querymanager.lexical Yylex]
           [querymanager.syntax DBParser sym]
           [java.io FileInputStream]
           [java_cup.runtime Symbol]))

(defn lex [file-name]
  (with-open [fis (FileInputStream. file-name)]
    (let [lex (Yylex. fis)]
      (loop [tok (.next_token lex)]
        (when-not (= (.sym tok) sym/EOF)
          (println (.value tok) " " (.sym tok))
          (recur (.next_token lex)))))))

(defn parse [file-name]
  (with-open [fis (FileInputStream. file-name)]
    (let [lex (Yylex. fis)
          syn (DBParser. lex)
          parse-tree (.parse syn)]
      (transform (.value parse-tree)))))