(ns collabo.repositories.user
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]
            [buddy.hashers :as hashers]))

(defn create-user [email account_name password]
  (j/insert! db :users {:email email
                        :account_name account_name
                        :password (hashers/derive password)
                        :created_at (tl/local-now)
                        :updated_at (tl/local-now)}))
