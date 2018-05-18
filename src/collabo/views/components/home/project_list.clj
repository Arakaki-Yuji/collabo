(ns collabo.views.components.home.project-list
  (:require [hiccup.core :as h]))

(defn project-zero []
  [:div {:class "empty"}
   [:div {:class "empty-icon"}
    [:i {:class "icon icon-3x icon-people"}]]
   [:p {:class "empty-title h5"} "You have no projects"]
   [:p {:class "empty-subtitle"} "Click the button to create new projects!"]
   [:div {:class "empty-action"}
    [:a  {:class "btn btn-primary" :href "/projects/new"} "Create New Projects"]]])

(defn project-tile [project]
  [:div {:class "tile"}
   ;; [:div {:class "tile-icon"}
   ;;  [:div {:class "example-tile-icon"}
   ;;   [:img {:class "avatar avatar-xl"  :src "http://via.placeholder.com/150x150"}]
   ;;   ]]
   [:div {:class "tile-content"}
    [:p {:class "tile-title"} (:title project)]
    [:p {:class "tile-subtitle text-gray"} (:description project)]
    ]
   [:div {:class "tile-action"}
    [:a {:class "btn btn-primary" :href (str "/projects/" (:id project))} "Detail"]
    ]
   ]
  )

(defn project-tiles [projects]
  (let [container [:div {:class "project-tiles-container"}]
        tiles (map project-tile projects)]
    (apply conj container (interleave tiles (repeat (count tiles) [:div {:class "divider"}])))))

(defn project-list [projects]
  (if (= (count projects) 0)
    (project-zero)
    (project-tiles projects)))
