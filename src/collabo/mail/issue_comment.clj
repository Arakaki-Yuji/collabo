(ns collabo.mail.issue-comment
  ;; responsible to sending mail and mail body
  (:require [collabo.mail :refer [send-mail]]
            [collabo.repositories.user :as repo-user]
            [collabo.repositories.issue :as repo-issue]
            [cljstache.core :refer [render-resource]]
            [collabo.views.utilities.link :refer [issue-link]]
            [collabo.config.base :refer [app-base-url]]))

(defn issue-commented-mail [issue comment]
  (let [to-users (repo-user/get-users-by-ids (repo-issue/find-related-userids (:id issue)))
        email-list (map #(:email %) to-users)
        commented-user (repo-user/get-user-by-id (:user_id issue))]
    (send-mail {:to email-list
                :subject (str "Issue #" (:id issue) " 「" (:title issue) "」 にコメントが投稿されました")
                :body  (render-resource "collabo/mail/issue_comment.mustache"
                                        {:issue-number (:id issue)
                                         :issue-title (:title issue)
                                         :comment-user-name (:account_name commented-user)
                                         :comment (:comment comment)
                                         :issue-link (str app-base-url
                                                          (issue-link issue))})})))
