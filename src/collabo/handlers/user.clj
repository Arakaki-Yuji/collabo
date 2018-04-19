(ns collabo.handlers.user
  (:require [collabo.views.user :as v-user]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.project :as pj-repo]))

(defn get-user [{:keys [route-params]}]
  (let [user (m-user/find-by-identity (:account_name route-params))
        user-projects (pj-repo/find-owned-projects (:id user))]
    (html (v-user/user-page user user-projects))))
