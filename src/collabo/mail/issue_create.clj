(ns collabo.mail.issue-create
  (:require [collabo.mail :refer [send-mail]]
            [collabo.repositories.user :as repo-user]
            [collabo.repositories.issue :as repo-issue]
            [cljstache.core :refer [render-resource]]
            [collabo.views.utilities.link :refer [issue-link]]
            [collabo.config.base :refer [app-base-url]]
            [clojure.tools.logging :as log]))

(defn issue-created-mail [issue comment]
  (let [to-users (repo-user/get-users-by-ids (repo-issue/find-related-userids (:id issue)))
        email-list (map #(:email %) to-users)
        issue-user (repo-user/get-user-by-id (:user_id issue))]
    (do
      (log/info issue-user)
      (send-mail {:to email-list
                  :subject (str "Created Issue #" (:id issue) " 「" (:title issue) "」.")
                  :body (render-resource "collabo/mail/issue_create.mustache"
                                         {:issue-number (:id issue)
                                          :issue-title (:title issue)
                                          :issue-user-name (:account_name issue-user)
                                          :comment (:comment comment)
                                          :issue-link (str app-base-url (issue-link issue))})}))))
