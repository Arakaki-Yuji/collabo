(ns collabo.handlers.project
  (:require [collabo.handlers.base :refer [html]]
            [collabo.views.project :refer [new-page]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]))


(defn get-new [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (html (new-page))))
