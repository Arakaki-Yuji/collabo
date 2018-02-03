(ns collabo.config.db
  (:require [environ.core :refer [env]]))

(def db {:dbtype "postgresql"
         :dbname (env :db-name)
         :host (env :db-host)
         :user (env :db-user)
         :password (env :db-password)
         :ssl false})
