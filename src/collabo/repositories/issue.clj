(ns collabo.repositories.issue
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [clj-time.format :as tf]
            [collabo.repositories.user :refer [find-project-owner]]))

(def table :issues)

(defn create-issue [{:keys [title project_id user_id] :as issue}]
  (j/insert! db table {:title title
                       :project_id project_id
                       :user_id user_id
                       :created_at (tl/local-now)
                       :updated_at (tl/local-now)}))

(defn find-by-project_id [project-id]
  (j/query db ["SELECT * FROM issues WHERE project_id = ?" project-id]))

(defn find-by-project_id-with-user [project-id]
  (j/query db ["SELECT issues.id AS id, title, user_id, project_id, issues.created_at AS created_at, issues.updated_at AS updated_at, users.account_name AS account_name FROM issues JOIN users ON user_id = users.id WHERE project_id = ?" project-id]))

(defn find-by-id [id]
  (j/query db ["SELECT issues.id AS id,
                       issues.title,
                       issues.user_id,
                       issues.project_id,
                       issues.created_at AS created_at,
                       issues.updated_at AS updated_at,
                       closed_issues.closed_at AS closed_at,
                       users.account_name AS account_name,
                       issue_owner_user.account_name AS issue_owner_name
                FROM issues
                JOIN users ON user_id = users.id
                LEFT JOIN closed_issues ON issues.id = closed_issues.issue_id
                JOIN project_owners ON project_owners.project_id = issues.project_id
                JOIN users AS issue_owner_user ON issue_owner_user.id = issues.user_id
                WHERE issues.id = ?" id]))

(defn find-closed-issue [issue-id]
  (j/query db ["SELECT * FROM closed_issues WHERE issue_id = ?" issue-id]))

(defn close-issue! [id]
  (if (empty? (find-closed-issue id))
    (j/insert! db :closed_issues {:issue_id id
                                  :closed_at (tl/local-now)})))

(defn reopen-issue! [id]
  (if (empty? (find-closed-issue id))
    nil
    (j/delete! db :closed_issues ["issue_id = ?" id])))

(defn find-closed-issues-in-project [project-id]
  (j/query db ["SELECT issues.id AS id,
                       title,
                       user_id,
                       project_id,
                       issues.created_at AS created_at,
                       issues.updated_at AS updated_at,
                       users.account_name AS account_name
                FROM issues JOIN closed_issues
                            ON issues.id = closed_issues.issue_id
                            JOIN users
                            ON issues.user_id = users.id
                WHERE issues.project_id = ?" project-id]))

(defn find-open-issues-in-project [project-id]
  (j/query db ["SELECT issues.id AS id,
                       title,
                       user_id, project_id,
                       issues.created_at AS created_at,
                       issues.updated_at AS updated_at,
                       users.account_name AS account_name
                FROM issues LEFT JOIN closed_issues
                            ON issues.id = closed_issues.issue_id
                            JOIN users
                            ON issues.user_id = users.id
                WHERE closed_issues.issue_id IS NULL
                  AND issues.project_id = ?"
               project-id]))


(defn closable-user? [issue user-id]
  ;; issue closable only project-owner and issue owner
  (let [project-owner (first (find-project-owner (:project_id issue)))]
    (if (or (= (:id project-owner) user-id)
            (= (:user_id issue) user-id))
      true
      false)))
