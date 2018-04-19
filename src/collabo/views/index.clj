(ns collabo.views.index
  (:require [hiccup.core :as h]
            [hiccup.page :as p]
            [collabo.views.layout :refer [layout]]))

(def index-page
  (layout [:h1 "Hello World"]))

(def login-page
  (layout [:div {:class "page-login layout-signup-in container"}
           [:h1 "Login"]
           [:form {:class "" :action "/login" :method "POST"}
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-email"} "Email"]]
            [:div {:class "form-group"}
             [:input {:class "form-input" :type "text" :id "user-email" :name "user-email" :placeholder "Email"}]]
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-password"} "Password"]]
            [:div {:class "form-group"}
             [:input {:class "form-input" :type "password" :id "user-password" :name "user-password"}]]
            [:div {:class "form-group"}
             [:button {:class "btn btn-primary" :type "submit"} "Submit"]]
            ]
           [:div {:class "mt-10"}
            [:a {:href "/signup" :class "mt-10"} "Creating Accounts is here"]]
           ]))

(def signup-page
  (layout [:div {:class "page-signup layout-signup-in"}
           [:h1 "Signup"]
           [:form {:class "" :action "/signup" :method "POST"}
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "account-name"} "Account Name"]
             [:input {:class "form-input" :type "text" :id "account-name" :placeholder "AccountName" :name "account_name"}]]
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-email"} "Email"]
             [:input {:class "form-input" :type "email" :id "user-email" :name "email" :placeholder "Email"}]]
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-password"} "Password"]
              [:input {:class "form-input" :type "password" :id "user-password" :name "password"}]]
            [:div {:class "form-group"}
             [:button {:class "btn btn-primary" :type "submit"} "Submit"]]
            ]
           [:div {:class "mt-10"}
            [:a {:href "/login" :class "mt-10 btn-link"} "Login is here."]]]))
