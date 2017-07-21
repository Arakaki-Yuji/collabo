(ns collabo.core
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jty]
            [collabo.webserver :as cw]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn sample-handler [res]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Reloaded"})

(defn main-system [config-options]
  (let [{:keys [port]} config-options]
    (component/system-map
     :web (cw/new-webserver port sample-handler))))
