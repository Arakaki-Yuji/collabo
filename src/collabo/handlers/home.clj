(ns collabo.handlers.home
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [collabo.views.home :as vh]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.project :as pj-repo]))

(defn get-home [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [current-user (m-user/find-by-identity (name (get-in req [:session :identity])))
          projects (pj-repo/get-trending-projects 4)]
      (html (vh/home-page current-user projects)))
    ))
