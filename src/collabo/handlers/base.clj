(ns collabo.handlers.base
  (:require [ring.util.response :as res]))

(defn html [view]
  (-> (res/response view)
      (res/header "Content-Type" "text/html")))
