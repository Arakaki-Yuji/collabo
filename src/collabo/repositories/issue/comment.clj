(ns collabo.repositories.issue.comment
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [clj-time.format :as tf]
            [hugsql.core :as hugsql]))

(defn create-comment [comment issue-id user-id]
  (j/insert! db :issue_comments {:issue_id issue-id
                                 :user_id user-id
                                 :comment comment
                                 :created_at (tl/local-now)
                                 :updated_at (tl/local-now)}))

(defn find-by-issue-id [issue-id]
  (j/query db ["SELECT issue_comments.id AS id,
                       issue_comments.comment AS comment,
                       issue_comments.user_id AS user_id,
                       issue_comments.created_at AS created_at,
                       issue_comments.updated_at AS updated_at,
                       users.account_name AS account_name,
                       users.icon AS icon
                FROM issue_comments
                JOIN users ON issue_comments.user_id = users.id
                WHERE issue_id = ?
                ORDER BY id ASC" issue-id]))


(hugsql/def-db-fns "collabo/repositories/sql/issues/comments.sql")
