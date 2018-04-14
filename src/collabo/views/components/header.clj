(ns collabo.views.components.header)

(defn header []
  [:header {:class "navbar"}
   [:section {:class "navbar-section"}
    [:a {:href "#" :class "navbar-brand mr-2"} ""]]
   [:section {:class "navbar-center"} ""]
   [:section {:class "navbar-section"}
    [:a {:href "#" :class "navbar-brand mr-2"} ""]]
   ])
