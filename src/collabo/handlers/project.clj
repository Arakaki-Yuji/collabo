(ns collabo.handlers.project
  (:require [collabo.handlers.base :refer [html]]
            [collabo.views.project :refer [new-page detail-page]]
            [collabo.models.user :refer [map-to-users]]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.project :as pj-repo]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [clojure.tools.logging :as log]))


(defn get-new [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (html (new-page))))

(defn get-detail [{:keys [route-params] :as req}]
  (do
    (log/info req)
    (log/info route-params)
    (let [id (:id route-params)
          project (first
                   (do
                     (pj-repo/find-by-id (read-string id))))]
      (do
        (log/info project)
        (log/info req)
        (html (detail-page req project)))
      )))

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
