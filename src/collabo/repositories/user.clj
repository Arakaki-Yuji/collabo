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
                        :aboutme aboutme
                        :icon icon
                        :created_at (tl/local-now)
                        :updated_at (tl/local-now)}))


(defn find-by-email [email]
  (j/query db ["SELECT * FROM users WHERE email = ?" email]))


(defn find-one-by-account_name [name]
  (j/query db ["SELECT * FROM users WHERE account_name = ? LIMIT 1", name]))

(defn update-aboutme-by-account_name [name aboutme]
  (j/update! db :users {:aboutme aboutme} ["account_name = ?" name]))
