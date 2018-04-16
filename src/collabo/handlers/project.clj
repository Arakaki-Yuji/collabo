(ns collabo.handlers.project
  (:require [collabo.handlers.base :refer [html]]
            [collabo.views.project :refer [new-page]]
            [collabo.models.user :refer [map-to-users]]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.project :as pj-repo]
            [buddy.auth :refer [authenticated? throw-unauthorized]]))


(defn get-new [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (html (new-page))))


(defn post-new [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [title (get-in req [:form-params "project-title"])
          description (get-in req [:form-params "project-description"])
          session (:session req)
          current-user (first (map-to-users
                               (user-repo/find-one-by-account_name
                                (name (:identity session)))))
          new-project (pj-repo/create-project title description current-user)]
      (html (str new-project)))))
