(ns collabo.repositories.issue
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [clj-time.format :as tf]))

(def table :issues)

(defn create-issue [{:keys [title description project_id user_id] :as issue}]
  (j/insert! db table {:title title
                    :description description
                    :project_id project_id
                    :user_id user_id
                    :created_at (tl/local-now)
                    :updated_at (tl/local-now)}))

(defn find-by-project_id [project-id]
  (j/query db ["SELECT * FROM issues WHERE project_id = ?" project-id]))

(defn find-by-project_id-with-user [project-id]
  (j/query db ["SELECT issues.id AS id, title, description, user_id, project_id, issues.created_at AS created_at, issues.updated_at AS updated_at, users.account_name AS account_name FROM issues JOIN users ON user_id = users.id WHERE project_id = ?" project-id]))
