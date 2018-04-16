(ns collabo.handlers.base
  (:require [ring.util.response :as res]
            [collabo.views.not-found :refer [not-found-page]]
            ))

(defn html [view]
  (-> (res/response view)
      (res/header "Content-Type" "text/html")))

(defn not-found [req]
  (html (not-found-page)))
