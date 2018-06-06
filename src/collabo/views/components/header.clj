(ns collabo.views.components.header
  (:require [clojure.tools.logging :as log]))

(defn header
  ([]
   (header nil))
  ([current-user]
  [:header {:class "navbar"}
   [:section {:class "navbar-section"}
    [:a {:href "/" :class "navbar-brand site-icon"} "Collabo"]]
   [:section {:class "navbar-center"} ""]
   (if-not current-user
     [:section {:class "navbar-section header-right"}
      [:a {:href "/signup" :class "navbar-brand"} "Signup"]
      [:span {:class "mx-2"} "/"]
      [:a {:href "/login" :class "navbar-brand"} "Login"]]

     [:section {:class "navbar-section header-right"}
      [:div {:class "dropdown"}
       [:div {:class "btn-group"}
        [:a {:href "#" :class "btn navbar-brand"}
         "MENU"]
        [:a {:href "#" :class "btn dropdown-toggle" :tabindex "0"}
         [:i {:class "icon icon-caret"}]
         ]
        [:ul {:class "menu"}
         [:li {:class "menu-item"}
          [:a {:href (str "/users/" (:account_name current-user))} "My Page"]]
         [:li {:class "menu-item"}
          [:a {:href "/logout"} "Log out"]]]]]])
   ]))
