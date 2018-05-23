(ns collabo.handlers.project
  (:require [collabo.handlers.base :refer [html]]
            [collabo.views.project :refer [new-page detail-page]]
            [collabo.models.user :refer [map-to-users]]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.project :as pj-repo]
            [collabo.repositories.issue :as issue-repo]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [redirect]]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]))


(defn get-new [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (html (new-page))))

(defn get-detail [{:keys [route-params] :as req}]
  (do
    (log/info req)
    (log/info route-params)
    (log/info (:query-params req))
    (let [id (:id route-params)
          project (first
                   (do
                     (pj-repo/find-by-id (read-string id))))
          issues (issue-repo/find-open-issues-in-project (:id project))]
      (do
        (log/info project)
        (log/info req)
        (log/info issues)
        (html (detail-page req project issues)))
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
      (redirect (str "/projects/" (:id (:project new-project)))))))

(defn update-description [{:keys [route-params form-params] :as req}]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [project-id (:id route-params)
          description (get form-params  "project-description")]
      (do
        (log/info form-params)
        (pj-repo/update-description-by-id project-id description)
        (redirect (str "/projects/" project-id "?tab=overview"))))))

(defn post-coverimage [{:keys [route-params multipart-params session] :as req}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [project-id (Integer/parseInt (:id route-params))
          coverimage (get multipart-params "project-coverimage")
          save-filename (str project-id "-" (:filename coverimage))]
      (do
        (log/info req)
        (io/copy (:tempfile coverimage)
                 (io/file pj-repo/project-coverimage-save-path save-filename))
        (pj-repo/insert-or-update-coverimage project-id save-filename)
        (redirect (str "/projects/" project-id))))))

(defn update-title [{:keys [route-params form-params] :as req}]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [project-id (:id route-params)
          title (get form-params "project-title")]
      (do
        (pj-repo/update-title-by-id project-id title)
        (redirect (str "/projects/" project-id "?tab=setting&menu=title"))))))


(defn delete-project [{:keys [route-params form-params session] :as req}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [project-id (:id route-params)]
      (do
        (pj-repo/delete-project (Integer/parseInt project-id))
      (redirect (str "/users/" (name (:identity session))))))
  ))
