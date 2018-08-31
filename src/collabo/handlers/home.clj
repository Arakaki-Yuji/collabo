(ns collabo.handlers.home
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [collabo.views.home :as vh]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.project :as pj-repo]))

(defn get-home [req]
  (html (vh/home-page req)))
