(ns collabo.handlers.issue
  (:require [collabo.handlers.base :refer [html]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [redirect]]
            [collabo.models.user :refer [map-to-users]]
            [collabo.repositories.project :as pj-repo]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.issue :as issue-repo]
            [clojure.tools.logging :as log]))


(defn post-new [{:keys [route-params form-params session] :as req}]
  (do
    (log/info route-params)
    (log/info form-params))
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [title (get form-params "issue-title")
          desc (get form-params "issue-description")
          user (first (map-to-users
                               (user-repo/find-one-by-account_name
                                (name (:identity session)))))]
      (do
        (issue-repo/create-issue {:title title
                                  :description desc
                                  :user_id (:id user)
                                  :project_id (Integer/parseInt (:project-id route-params))})
        (redirect (str "/projects/" (get route-params :project-id) "?tab=issues"))))
    )
  )
