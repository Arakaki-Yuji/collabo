(defproject collabo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [ring "1.6.2"]
                 [com.stuartsierra/component "0.3.2"]
                 [org.clojure/tools.logging "0.4.0"]
                 [bidi "2.1.2"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [org.postgresql/postgresql "42.2.1"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [garden "1.3.2"]
                 [ragtime "0.6.0"]
                 [clj-time "0.14.2"]
                 [ring-logger "0.7.7"]
                 [buddy "2.0.0"]
                 [com.stuartsierra/log.dev "0.2.0"]
                 ]
  :plugins [[lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.15"]]
  :source-paths ["src" "src-cljs"]
  :clean-targets ^{:protect false} [:target-path "out" "resources/public/cljs"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src-cljs"]
                        :figwheel true
                        :compiler {:main "collabo.core"
                                   :asset-path "cljs/out"
                                   :output-to  "resources/public/cljs/main.js"
                                   :output-dir "resources/public/cljs/out"
                                   }}]}
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.8"]]
                   :source-paths ["dev"]}})
