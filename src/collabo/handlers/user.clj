(ns collabo.handlers.user
  (:require [collabo.views.user :as v-user]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.project :as pj-repo]
            [clojure.tools.logging :as log]))

(defn get-user [{:keys [route-params query-params] :as req}]
  (let [user (m-user/find-by-identity (:account_name route-params))
        user-projects (pj-repo/find-owned-projects (:id user))]
    (do
     (log/info (get query-params "tab"))
     (log/info (type (first (keys query-params))))
     (html (v-user/user-page user
                             user-projects
                             (get query-params "tab")
                             (get query-params "menu"))))))
