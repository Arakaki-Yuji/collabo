(ns collabo.handlers.project
  (:require [collabo.handlers.base :refer [html]]
            [collabo.views.project :refer [new-page detail-page]]
            [collabo.models.user :refer [map-to-users]]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.project :as pj-repo]
            [collabo.repositories.issue :as issue-repo]
            [collabo.validates.core :as v]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [redirect]]
            [collabo.handlers.utilities.project :refer [current-user-is-owner]]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]))


(defn get-new [{:keys [session] :as req}]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [current-user (first (user-repo/find-one-by-account_name (name (:identity session))))]
      (html (new-page req current-user)))))

(defn get-detail [{:keys [route-params session] :as req}]
  (do
    (log/info req)
    (let [id (:id route-params)
          project (first
                   (do
                     (pj-repo/find-by-id (read-string id))))
          issues (issue-repo/find-open-issues-in-project (:id project))
          current-user (if (authenticated? session)
                         (first (user-repo/find-one-by-account_name (name (:identity session))))
                         nil)]
      (do
        (log/info current-user)
        (html (detail-page req project issues current-user)))
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

(defn update-description [{:keys [route-params form-params session] :as req}]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [project-id (:id route-params)
          project (first (pj-repo/find-by-id (read-string project-id)))
          description (get form-params  "project-description")]
      (if-not (current-user-is-owner session project)
        ;; TODO: it is authentication error, so fix later.
        (throw-unauthorized)
        (do
          (log/info form-params)
          (pj-repo/update-description-by-id project-id description)
          (redirect (str "/projects/" project-id "?tab=overview")))))))

(defn post-coverimage [{:keys [route-params multipart-params session] :as req}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [project-id (Integer/parseInt (:id route-params))
          project (first (pj-repo/find-by-id project-id))
          coverimage (get multipart-params "project-coverimage")
          save-filename (str project-id "-" (:filename coverimage))]
      (if-not (current-user-is-owner session project)
        ;; TODO: it is authentication error, so fix later.
        (throw-unauthorized)
        (if-not (v/validate-filetype (:filename coverimage))
          (-> (redirect (str "/projects/" project-id "?tab=setting&menu=overview-coverimage"))
              (assoc :flash {:error "Coverimage filetype must be png or jpeg."}))
          (do
            (log/info coverimage)
            (io/copy (:tempfile coverimage)
                     (io/file pj-repo/project-coverimage-save-path save-filename))
            (pj-repo/insert-or-update-coverimage project-id save-filename)
            (redirect (str "/projects/" project-id))))))))

(defn update-title [{:keys [route-params form-params session] :as req}]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (let [project-id (:id route-params)
          project (first (pj-repo/find-by-id (read-string project-id)))
          title (get form-params "project-title")]
      (if-not (current-user-is-owner session project)
        ;; TODO: it is authentication error, so fix later.
        (do
          (log/info project)
          (log/info session)
          (throw-unauthorized))
        (if (empty? title)
          (-> (redirect (str "/projects/" project-id "?tab=setting&menu=title"))
              (assoc :flash {:error "Project title must be present"}))
          (do
            (pj-repo/update-title-by-id project-id title)
            (redirect (str "/projects/" project-id "?tab=setting&menu=title"))))))))


(defn delete-project [{:keys [route-params form-params session] :as req}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [project-id (:id route-params)]
      (do
        (pj-repo/delete-project (Integer/parseInt project-id))
      (redirect (str "/users/" (name (:identity session))))))
  ))
