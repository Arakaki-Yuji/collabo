(ns collabo.views.home
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]))

(defn home-page []
  (layout
   [:div {:class "columns"}
    [:div {:class "column col-12"}
    [:div {:class "empty"}
     [:div {:class "empty-icon"}
      [:i {:class "icon icon-3x icon-people"}]]
     [:p {:class "empty-title h5"} "You have no projects"]
     [:p {:class "empty-subtitle"} "Click the button to create new projects!"]
     [:div {:class "empty-action"}
      [:button {:class "btn btn-primary"} "Create New Projects"]]]
    ]]
   ))
