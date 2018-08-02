(ns collabo.views.index
  (:require [hiccup.core :as h]
            [hiccup.page :as p]
            [collabo.views.layout :refer [layout default-ogptag]]
            [collabo.views.components.header :refer [header]]))

(def index-page
  (layout [:h1 "Hello World"]))

(defn login-page [{:keys [flash] :as req}]
  (layout
   {:title "Collabo | Login"
    :description "Collabo is Open Collaboration Community. Let's Join us"
    :url nil
    :image nil}
   [:div {:class "page-login layout-signup-in container"}
    [:h1 "Login"]
    [:form {:class "" :action "/login" :method "POST"}
     (if (get-in flash [:error])
       [:div {:class "toast toast-error"} (get-in flash [:error])])
     (if (get-in flash [:message])
       [:div {:class "toast toast-success"} (get-in flash [:message])])
     [:div {:class "form-group"}
      [:label {:class "form-label" :for "user-email"} "Email"]]
     [:div {:class "form-group"}
      [:input {:class "form-input" :type "text" :id "user-email" :name "user-email" :placeholder "Email"}]]
     [:div {:class "form-group"}
      [:label {:class "form-label" :for "user-password"} "Password"]]
     [:div {:class "form-group"}
      [:input {:class "form-input" :type "password" :id "user-password" :name "user-password"}]]
     [:div {:class "form-group text-right"}
      [:button {:class "btn btn-primary" :type "submit"} "Submit"]]
     ]
    [:div {:class "mt-2 text-right"}
     [:a {:href "/signup" :class "mt-10"} "Creating Accounts is here"]]
    ]))

(defn signup-page [{:keys [flash] :as req}]
  (layout [:div {:class "page-signup layout-signup-in container"}
           [:h1 "Signup"]
           [:form {:class "" :action "/signup" :method "POST"}
            (if (get-in flash [:error])
              [:div {:class "toast toast-error"} (get-in flash [:error])])
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "account-name"} "Account Name"]
             [:input {:class "form-input" :type "text" :id "account-name" :placeholder "AccountName" :name "account_name"}]]
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-email"} "Email"]
             [:input {:class "form-input" :type "email" :id "user-email" :name "email" :placeholder "Email"}]]
            [:div {:class "form-group"}
             [:label {:class "form-label" :for "user-password"} "Password"]
              [:input {:class "form-input" :type "password" :id "user-password" :name "password"}]]
            [:div {:class "form-group text-right mt-2"}
             [:button {:class "btn btn-primary" :type "submit"} "Submit"]]
            ]
           [:div {:class "mt-2 text-right"}
            [:a {:href "/login" :class "mt-2 btn-link"} "Login is here."]]]))
