(ns collabo.handlers.user
  (:require [collabo.views.user :as v-user]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.user :as repo-user]
            [collabo.repositories.project :as pj-repo]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [redirect]]
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

(defn post-aboutme [{:keys [route-params form-params session]}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [identity (:identity session)
          aboutme (get-in form-params ["user-aboutme"])
          account_name (:account_name route-params)]
      (do
        (log/info aboutme)
        (if (= (name identity) account_name)
          (repo-user/update-aboutme-by-account_name account_name aboutme))
        (redirect (str "/users/" account_name "?tab=setting&menu=aboutme"))
        )
      )
    ))
