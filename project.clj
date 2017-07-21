(defproject collabo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.2"]
                 [com.stuartsierra/component "0.3.2"]
                 [org.clojure/tools.logging "0.4.0"]
                 [bidi "2.1.2"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [mysql/mysql-connector-java "6.0.6"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [garden "1.3.2"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]]
                   :source-paths ["dev"]}})
