(ns collabo.repositories.project
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]))

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
