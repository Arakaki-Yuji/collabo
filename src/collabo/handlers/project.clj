(ns collabo.handlers.project
  (:require [collabo.handlers.base :refer [html]]
            [collabo.views.project :refer [new-page]]))


(defn get-new [req]
  (html (new-page)))
