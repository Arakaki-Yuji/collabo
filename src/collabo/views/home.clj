(ns collabo.views.home
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout-headerless]]
            [collabo.views.components.home.project-list :refer [project-list]]))

(defn home-page [user projects]
  (layout-headerless
   [:div {:class "home-page"}
    [:div {:class "overlay"}
     [:div {:class "columns"}
      [:div {:class "column col-8 col-mx-auto headline-container"}
       [:h1 {:class "headline"} "Collabo"]
       [:p {:class "sub-headline"}  "Start projects with people all over the world"]
       ]
      ]
     [:div {:class "columns action-container"}
      [:div {:class "column col-2 col-ml-auto"}
       [:a {:href "/signup" :class "btn btn-primary btn-block"} "Signup"]]
      [:div {:class "column col-2 col-mr-auto"}
       [:a {:href "/login" :class "btn btn-primary btn-block"} "Login"]]
      ]
     ]
    ]
   )
  )
