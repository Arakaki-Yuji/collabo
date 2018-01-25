(ns collabo.repositories.user
  (:require [clojure.java.jdbc :as j]
            [collabo.config.db :refer [mysql-db]]
            [clj-time.core :as t]
            [clj-time.local :as tl]
            [clj-time.format :as tf]))

(def custom-formatter (tf/formatter "yyyy-MM-dd HH:mm:ss"))
(tf/unparse custom-formatter (tl/local-now))

(defn date-format [date]
  (tf/unparse custom-formatter date))

(defn create-user [email account_name password]
  (j/insert! mysql-db :users {:email email
                              :account_name account_name
                              :password password
                              :created_at (date-format (tl/local-now))
                              :updated_at (date-format (tl/local-now))}))
