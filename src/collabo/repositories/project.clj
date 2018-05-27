(ns collabo.repositories.project
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [collabo.repositories.issue.comment :as issue-comment-repo]))

(def project-table :projects)
(def project-owner-table :project_owners)
(def project-coverimage-public-page "/uploads/projectcoverimages/")
(def project-coverimage-save-path (str "resources/public" project-coverimage-public-page))

(defn get-project-coverimage-url [project]
  (if (= (count (:coverimage project)) 0)
    "http://via.placeholder.com/950x650"
    (str project-coverimage-public-page (:coverimage project))))

(defn get-coverimage-webpath [{:keys [coverimage]}]
  (str project-coverimage-public-page coverimage))

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
                       users.account_name AS account_name,
                       project_coverimages.filename AS coverimage
                FROM projects
                JOIN project_owners ON projects.id = project_owners.project_id
                JOIN users ON project_owners.user_id = users.id
                LEFT JOIN project_coverimages ON projects.id = project_coverimages.project_id
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


(defn insert-or-update-coverimage [project-id filename]
  (j/with-db-transaction [t-con db]
    (let [coverimage (j/query db ["SELECT project_id FROM project_coverimages WHERE project_id = ?" project-id])]
      (if (= (count coverimage) 0)
        (j/insert! t-con :project_coverimages {:project_id project-id
                                               :filename filename
                                               :created_at (tl/local-now)
                                               :updated_at (tl/local-now)})
        (do
          (j/update! t-con
                     :project_coverimages
                     {:filename filename :updated_at (tl/local-now)}
                     ["project_id = ?" project-id])
          (j/query t-con ["SELECT * FROM project_coverimages WHERE project_id = ?" project-id]))))))

(defn get-trending-projects [limit]
  (j/query db ["SELECT projects.id,
                       projects.title,
                       projects.description,
                       projects.created_at,
                       projects.updated_at,
                       users.id AS user_id,
                       users.account_name AS account_name,
                       project_coverimages.filename AS coverimage
                FROM projects
                JOIN project_owners ON projects.id = project_owners.project_id
                JOIN users ON project_owners.user_id = users.id
                LEFT JOIN project_coverimages ON projects.id = project_coverimages.project_id
                ORDER BY projects.id DESC LIMIT ?" limit]))
