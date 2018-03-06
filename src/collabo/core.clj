(ns collabo.core
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jty]
            [collabo.webserver :as cw]
            [collabo.styles :as css]
            [collabo.routes :refer (routes)]
            [ring.middleware.resource :refer (wrap-resource)]
            [ring.middleware.content-type :refer (wrap-content-type)]
            [ring.middleware.not-modified :refer (wrap-not-modified)]
            [ring.middleware.params :refer (wrap-params)]
            [ring.logger :as logger]))

(def app
  (-> routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)
      (wrap-params)
      (logger/wrap-with-logger)))

(defn main-system [config-options]
  (let [{:keys [port]} config-options]
    (component/system-map
     :web (cw/new-webserver port app)
     :css (css/new-cssbuilder))))
