(ns collabo.core
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jty]
            [collabo.webserver :as cw]
            [collabo.styles :as css]
            [collabo.routes :refer (routes)]
            [ring.util.response :refer [redirect]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.session.cookie :refer [cookie-store]]
            [ring.middleware.resource :refer (wrap-resource)]
            [ring.middleware.content-type :refer (wrap-content-type)]
            [ring.middleware.not-modified :refer (wrap-not-modified)]
            [ring.middleware.params :refer (wrap-params)]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.flash :refer [wrap-flash]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            ))

(defn unauthorized-handler
  [request metadata]
  (let [current-url (:uri request)]
    (redirect (format "/login?next=%s" current-url))))

;; Create an instance of auth backend.
(def auth-backend
  (session-backend {:unauthorized-handler unauthorized-handler}))

(def app
  (-> routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)
      (wrap-flash)
      (wrap-session {:store (cookie-store {:key "1234567890qwerty"})})
      (wrap-authorization auth-backend)
      (wrap-authentication auth-backend)
      wrap-params
      wrap-multipart-params))

(defn main-system [config-options]
  (let [{:keys [port]} config-options]
    (component/system-map
     :web (cw/new-webserver port app)
     :css (css/new-cssbuilder))))
