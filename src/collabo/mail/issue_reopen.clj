(ns collabo.mail.issue-reopen
  ;; responsible to sending mail and mail body
  (:require [collabo.mail :refer [send-mail]]
            [collabo.repositories.user :as repo-user]
            [collabo.repositories.issue :as repo-issue]
            [cljstache.core :refer [render-resource]]
            [collabo.views.utilities.link :refer [issue-link]]
            [collabo.config.base :refer [app-base-url]]))

(defn issue-reopened-mail [issue reopened-user]
  (let [to-users (repo-user/get-users-by-ids (repo-issue/find-related-userids (:id issue)))
        email-list (map #(:email %) to-users)]
    (send-mail {:to email-list
                :subject (str "Reopen Issue #" (:id issue) " 「" (:title issue) "」")
                :body  (render-resource "collabo/mail/issue_reopen.mustache"
                                        {:issue-number (:id issue)
                                         :issue-title (:title issue)
                                         :reopen-user-name (:account_name reopened-user)
                                         :issue-link (str app-base-url
                                                          (issue-link issue))})})))
