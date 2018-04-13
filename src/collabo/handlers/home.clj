(ns collabo.handlers.home
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [collabo.views.home :as vh]
            [collabo.handlers.base :refer [html]]))

(defn get-home [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (html (vh/home-page))))
