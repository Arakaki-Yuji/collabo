(ns collabo.config.base
  (:require [environ.core :refer [env]]))

(def app-base-url (str (env :app-scheme)
                       "://"
                       (env :app-host)))
