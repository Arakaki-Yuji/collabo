(ns collabo.routes
  (:require [bidi.ring :refer (make-handler)]
            [ring.util.response :as res]
            [collabo.handlers.signup :as signup]
            [collabo.handlers.login :as login]
            [collabo.handlers.home :as home]
            [collabo.handlers.project :as project]
            [collabo.views.index :as vi]))

(defn index-handler [req]
  (-> (res/response vi/index-page)
      (res/header "Content-Type" "text/html")))

(def routes
  (make-handler ["/" {:get
                      {"login" login/get-login
                       "signup" signup/get-signup
                       "projects" {"/new" project/get-new}
                       "" home/get-home}
                      :post
                      {"signup" signup/post-signup
                       "login" login/post-login
                       "projects" {"/new" project/post-new}
                       }}
                 ]
                ))
