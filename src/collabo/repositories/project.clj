(ns collabo.repositories.project
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [collabo.repositories.issue.comment :as issue-comment-repo]))

(def project-table :projects)
(def project-owner-table :project_owners)

(defn create-project [title description user]
  (j/with-db-transaction [t-con db]
    (let [pj (first (j/insert! t-con project-table {:title title
                                                    :description description
                                                    :created_at (tl/local-now)
                                                    :updated_at (tl/local-now)}))
          po (first (j/insert! t-con project-owner-table {:user_id (:id user)
                                                          :project_id (:id pj)
                                                          :created_at (tl/local-now)
                                                          :updated_at (tl/local-now)}))]
      {:project pj :project-owner po})
    )
  )


(defn find-by-id [id]
  (j/query db ["SELECT projects.id,
                       projects.title,
                       projects.description,
                       projects.created_at,
                       projects.updated_at,
                       users.id AS user_id,
                       users.account_name AS account_name
                FROM projects
                JOIN project_owners ON projects.id = project_owners.project_id
                JOIN users ON project_owners.user_id = users.id
                WHERE projects.id = ?" id]))


(defn find-owned-projects [user_id]
  (j/query db ["SELECT p.id, p.title, p.description, p.created_at, p.updated_at FROM projects AS p JOIN project_owners AS po ON p.id = po.project_id WHERE po.user_id = ?" user_id]))

(defn update-description-by-id [id description]
  (j/update! db :projects {:description description} ["id = ?" (read-string id)]))

(defn update-title-by-id [id title]
  (j/update! db :projects {:title title} ["id = ?" (read-string id)]))


(defn delete-project [project-id]
  (j/with-db-transaction [t-con db]
    (do
      (issue-comment-repo/delete-issue-comments-by-project-id t-con {:project_id project-id})
      (j/delete! t-con :issues ["project_id = ?" project-id])
      (j/delete! t-con :project_owners ["project_id = ?" project-id])
      (j/delete! t-con :projects ["id = ?", project-id])
      ))
  )
