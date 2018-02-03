(ns collabo.repositories.user
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [db]]
            [clj-time.local :as tl]
            [clj-time.jdbc]))

(defn create-user [email account_name password]
  (j/insert! db :users {:email email
                        :account_name account_name
                        :password password
                        :created_at (tl/local-now)
                        :updated_at (tl/local-now)}))
