(ns collabo.config.mail
  (:require [environ.core :refer [env]]))

(def mail {:host (env :mail-host)
           :user (env :mail-user)
           :pass (env :mail-pass)
           :ssl true})

(def mail-from (env :mail-user))
