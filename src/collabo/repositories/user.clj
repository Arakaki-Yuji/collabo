(ns collabo.repositories.user
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [buddy.hashers :as hashers]))

(defn create-user [email account_name password aboutme icon]
  (j/insert! db :users {:email email
                        :account_name account_name
                        :password (hashers/derive password)
                        :aboutme (if aboutme aboutme "")
                        :icon (if icon icon "")
                        :created_at (tl/local-now)
                        :updated_at (tl/local-now)}))


(defn find-by-email [email]
  (j/query db ["SELECT * FROM users WHERE email = ?" email]))


(defn find-one-by-account_name [name]
  (j/query db ["SELECT * FROM users WHERE account_name = ? LIMIT 1", name]))

(defn update-aboutme-by-account_name [name aboutme]
  (j/update! db :users {:aboutme aboutme} ["account_name = ?" name]))

(defn update-email-by-account_name [name email]
  (j/update! db :users {:email email} ["account_name = ?" name]))

(defn update-icon-by-account_name [name icon]
  (j/update! db :users {:icon icon} ["account_name = ?" name]))


(defn find-project-owner [project-id]
  (j/query db ["SELECT users.id,
                       users.account_name,
                       users.email,
                       users.password,
                       users.created_at,
                       users.updated_at
                FROM users
                JOIN project_owners ON users.id = project_owners.user_id
                WHERE project_owners.project_id = ?" project-id]))

(defn exist-by-email [email]
  (not (empty? (j/query db ["SELECT id FROM users WHERE email = ? LIMIT 1" email]))))

(defn exist-by-account-name [account-name]
  (not (empty? (j/query db ["SELECT id FROM users WHERE account_name = ? LIMIT 1" account-name]))))

(defn get-trending-users [limit]
  (j/query db ["SELECT id, account_name, aboutme, icon FROM users LIMIT ?" limit]))
