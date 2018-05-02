(ns collabo.routes
  (:require [bidi.ring :refer (make-handler)]
            [ring.util.response :as res]
            [collabo.handlers.base :as base]
            [collabo.handlers.signup :as signup]
            [collabo.handlers.login :as login]
            [collabo.handlers.home :as home]
            [collabo.handlers.project :as project]
            [collabo.handlers.user :as user]
            [collabo.views.index :as vi]))

(defn index-handler [req]
  (-> (res/response vi/index-page)
      (res/header "Content-Type" "text/html")))

(def routes
  (make-handler ["/" {:get
                      {"login" login/get-login
                       "signup" signup/get-signup
                       "projects" {"/new" project/get-new}
                       "users" {["/" :account_name] user/get-user}
                       "" home/get-home
                       true base/not-found}
                      :post
                      {"signup" signup/post-signup
                       "login" login/post-login
                       "projects" {"/new" project/post-new}
                       "users" {["/" :account_name "/aboutme"] user/post-aboutme}
                       true base/not-found
                       }}
                 ]
                ))
