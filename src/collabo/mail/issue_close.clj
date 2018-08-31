(ns collabo.mail.issue-close
  ;; responsible to sending mail and mail body
  (:require [collabo.mail :refer [send-mail]]
            [collabo.repositories.user :as repo-user]
            [collabo.repositories.issue :as repo-issue]
            [cljstache.core :refer [render-resource]]
            [collabo.views.utilities.link :refer [issue-link]]
            [collabo.config.base :refer [app-base-url]]))

(defn issue-closed-mail [issue closed-user]
  (let [to-users (repo-user/get-users-by-ids (repo-issue/find-related-userids (:id issue)))
        email-list (map #(:email %) to-users)]
    (send-mail {:to email-list
                :subject (str "Close Issue #" (:id issue) " 「" (:title issue) "」")
                :body  (render-resource "collabo/mail/issue_close.mustache"
                                        {:issue-number (:id issue)
                                         :issue-title (:title issue)
                                         :close-user-name (:account_name closed-user)
                                         :issue-link (str app-base-url
                                                          (issue-link issue))})})))
