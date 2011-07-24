(defproject Database "0.0.1-SNAPSHOT"
  :description "TODO: add summary of your project"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [net.sf.squirrel-sql.thirdparty-non-maven/java-cup "0.11a"]]
  :dev-dependencies [[net.sourceforge.maven-jlex/maven-jlex-plugin "1.0"]
                     [net.sf.squirrel-sql.thirdparty-non-maven/java-cup "0.11a"]]
  :aot [database.core]
  :main database.core)
