(ns collabo.routes
  (:require [bidi.ring :refer (make-handler)]
            [ring.util.response :as res]
            [collabo.views.index :as vi]))

(defn index-handler [req]
  (-> (res/response vi/index-page)
      (res/header "Content-Type" "text/html")))

(defn login-handler [req]
  (-> (res/response vi/login-page)
      (res/header "Content-Type" "text/html")))

(defn signup-handler [req]
  (-> (res/response vi/signup-page)
      (res/header "Content-Type" "text/html")))

(def routes
  (make-handler ["/" {"login" login-handler
                      "signup" signup-handler
                      "" login-handler}]
                ))
