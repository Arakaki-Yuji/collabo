(ns collabo.handlers.home
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [collabo.views.home :as vh]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.project :as pj-repo]))

(defn get-home [req]
  (let [projects (pj-repo/get-trending-projects 4)
        users (user-repo/get-trending-users 4)]
    (html (vh/home-page req projects users)))
  )
