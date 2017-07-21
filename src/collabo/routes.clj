(ns collabo.routes
  (:require [bidi.ring :refer (make-handler)]
            [ring.util.response :as res]))

(defn index-handler [req]
  (res/response "Homepage"))

(def routes
  (make-handler ["/" index-handler]))
