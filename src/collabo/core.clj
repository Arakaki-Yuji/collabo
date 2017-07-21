(ns collabo.core
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jty]
            [collabo.webserver :as cw]
            [collabo.routes :refer (routes)]
            [ring.middleware.resource :refer (wrap-resource)]
            [ring.middleware.content-type :refer (wrap-content-type)]
            [ring.middleware.not-modified :refer (wrap-not-modified)]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def app
  (-> routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))

(defn main-system [config-options]
  (let [{:keys [port]} config-options]
    (component/system-map
     :web (cw/new-webserver port app))))
