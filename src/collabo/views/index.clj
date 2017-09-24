(ns collabo.views.index
  (:require [hiccup.core :as h]
            [hiccup.page :as p]))

(defn layout [content]
  (p/html5
   [:head
    [:title "Collabo"]
    [:link {:rel "stylesheet" :href "/css/spectre.css"}]
    [:link {:rel "stylesheet" :href "/css/spectre-icons.min.css"}]
    [:link {:rel "stylesheet" :href "/css/build.css"}]]
   [:body content]))

(def index-page
  (layout [:h1 "Hello World"]))

(def login-page
  (layout [:div {:class "page-login layout-main"}
           [:h1 "Login"]
           [:form {:class ""}
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-email"} "Email"]]
            [:div {:class "form-group"}
             [:input {:class "form-input" :type "text" :id "user-email" :placeholder "Email"}]]
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-password"} "Password"]]
            [:div {:class "form-group"}
             [:input {:class "form-input" :type "password" :id "user-password"}]]
            [:div {:class "form-group"}
             [:button {:class "btn btn-primary" :type "submit"} "Submit"]]
            ]]))
