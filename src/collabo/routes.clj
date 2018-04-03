(ns collabo.routes
  (:require [bidi.ring :refer (make-handler)]
            [ring.util.response :as res]
            [collabo.handlers.signup :as signup]
            [collabo.handlers.login :as login]
            [collabo.views.index :as vi]))

(defn index-handler [req]
  (-> (res/response vi/index-page)
      (res/header "Content-Type" "text/html")))

(def routes
  (make-handler ["/" {:get
                      {"login" login/get-login
                       "signup" signup/get-signup
                       "" login/get-login}
                      :post
                      {"signup" signup/post-signup
                       "login" login/post-login}}
                 ]
                ))
