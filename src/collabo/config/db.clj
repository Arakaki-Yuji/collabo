(ns collabo.config.db
  (:require [environ.core :refer [env]]))

(def mysql-db {:dbtype "mysql"
               :dbname (env :mysql-dbname)
               :user (env :mysql-user)
               :password (env :mysql-password)
               :useSSL (env :mysql-usessl)})
