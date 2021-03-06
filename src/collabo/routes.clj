(ns collabo.routes
  (:require [bidi.ring :refer (make-handler)]
            [ring.util.response :as res]
            [collabo.handlers.base :as base]
            [collabo.handlers.signup :as signup]
            [collabo.handlers.login :as login]
            [collabo.handlers.home :as home]
            [collabo.handlers.project :as project]
            [collabo.handlers.user :as user]
            [collabo.handlers.issue :as issue]
            [collabo.views.index :as vi]))

(defn index-handler [req]
  (-> (res/response vi/index-page)
      (res/header "Content-Type" "text/html")))

(def routes
  (make-handler ["/" {:get
                      {"login" login/get-login
                       "signup" signup/get-signup
                       "logout" login/logout
                       "projects" {"/new" project/get-new
                                   ["/" :id "/issues/" :issue-id] issue/get-detail
                                   ["/" :id] project/get-detail}
                       "users" {["/" :account_name] user/get-user}
                       "" home/get-home
                       true base/not-found}
                      :post
                      {"signup" signup/post-signup
                       "login" login/post-login
                       "projects" {"/new" project/post-new
                                   ["/" :id "/description"] project/update-description
                                   ["/" :id "/title"] project/update-title
                                   ["/" :id "/delete"] project/delete-project
                                   ["/" :id "/coverimage"] project/post-coverimage
                                   ["/" :project-id "/issues/" :issue-id "/close"] issue/close-issue
                                   ["/" :project-id "/issues/" :issue-id "/open"] issue/open-issue
                                   ["/" :project-id "/issues/" :issue-id "/comment"] issue/create-comment
                                   ["/" :project-id "/issues"] {"/new" issue/post-new}
                                   }
                       "users" {["/" :account_name "/aboutme"] user/post-aboutme
                                ["/" :account_name "/email"] user/post-email
                                ["/" :account_name "/icon"] user/post-icon}
                       true base/not-found
                       }
                      }
                 ]
                ))
