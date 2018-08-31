(ns collabo.handlers.issue
  (:require [collabo.handlers.base :refer [html]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [redirect]]
            [collabo.models.user :refer [map-to-users]]
            [collabo.repositories.project :as pj-repo]
            [collabo.repositories.user :as user-repo]
            [collabo.repositories.issue :as issue-repo]
            [collabo.repositories.issue.comment :as comment-repo]
            [collabo.views.project :as v-pj]
            [collabo.views.not-found :refer [not-found-page]]
            [clojure.tools.logging :as log]
            [collabo.mail.issue-comment :refer [issue-commented-mail]]
            [collabo.mail.issue-create :refer [issue-created-mail]]
            [collabo.mail.issue-close :refer [issue-closed-mail]]))


(defn post-new [{:keys [route-params form-params session] :as req}]
  (do
    (log/info route-params)
    (log/info form-params))
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [title (get form-params "issue-title")
          comment (get form-params "issue-comment")
          user (first (map-to-users
                               (user-repo/find-one-by-account_name
                                (name (:identity session)))))]
      (if (empty? title)
        (-> (redirect (str "/projects/" (get route-params :project-id) "?tab=issues&action=new"))
            (assoc :flash {:error "Title must be present."}))
        (do
          (let [created-issue (first (issue-repo/create-issue {:title title
                                                               :user_id (:id user)
                                                               :project_id (Integer/parseInt (:project-id route-params))}))
                created-comment (if-not (empty? comment)
                                  (first (comment-repo/create-comment comment (:id created-issue) (:id user))))]
            (issue-created-mail created-issue created-comment)
            (redirect (str "/projects/" (get route-params :project-id) "?tab=issues")))))
      )
    )
  )

(defn close-issue [{:keys [route-params form-params session] :as req}]
  (do
    (log/info route-params)
    (log/info form-params))
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [user (first (map-to-users (user-repo/find-one-by-account_name (name (:identity session)))))
          project-id (Integer/parseInt (:project-id route-params))
          issue-id (Integer/parseInt (:issue-id route-params))
          issue (first (issue-repo/find-by-id issue-id))]
      (if (issue-repo/closable-user? issue (:id user))
        (do
          (issue-repo/close-issue! issue-id)
          (issue-closed-mail issue user)
          (redirect (str "/projects/" project-id "/issues/" issue-id)))))))

(defn open-issue [{:keys [route-params form-params session] :as req}]
  (do
    (log/info route-params)
    (log/info form-params))
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [user (first (map-to-users (user-repo/find-one-by-account_name (name (:identity session)))))
          project-id (Integer/parseInt (:project-id route-params))
          issue-id (Integer/parseInt (:issue-id route-params))
          issue (first (issue-repo/find-by-id issue-id))]
      (if (issue-repo/closable-user? issue (:id user))
        (do
          (issue-repo/reopen-issue! issue-id)
          (redirect (str "/projects/" project-id "/issues/" issue-id)))))))

(defn create-comment [{:keys [route-params form-params session] :as req}]
  (do
    (log/info route-params)
    (log/info form-params))
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [user (first (map-to-users (user-repo/find-one-by-account_name (name (:identity session)))))
          project-id (Integer/parseInt (:project-id route-params))
          issue-id (Integer/parseInt (:issue-id route-params))
          issue (first (issue-repo/find-by-id issue-id))]
      (if issue
        (if (empty? (get form-params "comment"))
          (-> (redirect (str "/projects/" project-id "/issues/" issue-id))
              (assoc :flash {:error "Comment must be present."}))
          (do
            (let [created-comment (first (comment-repo/create-comment (get form-params "comment")
                                                               issue-id
                                                               (:id user)))]
              (issue-commented-mail issue created-comment))
            (redirect (str "/projects/" project-id "/issues/" issue-id))))
        (html (not-found-page))))))



(defn get-detail [{:keys [route-params session] :as req}]
  (let [project-id (Integer/parseInt (get route-params :id))
        issue-id (Integer/parseInt (get route-params :issue-id))
        issue (first (issue-repo/find-by-id issue-id))
        project (first (pj-repo/find-by-id project-id))
        comments (comment-repo/find-by-issue-id issue-id)
        current-user (if (authenticated? session)
                       (first (map-to-users (user-repo/find-one-by-account_name (name (:identity session)))))
                       nil)
        is-closeable-flg (issue-repo/closable-user? issue (:id current-user))]
    (if (and issue project (= project-id (:project_id issue)))
      (html (v-pj/issue-detail-page req project issue comments is-closeable-flg current-user))
      (html (not-found-page))
      )))
