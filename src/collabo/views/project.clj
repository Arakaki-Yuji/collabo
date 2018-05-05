(ns collabo.views.project
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]
            [collabo.views.components.project.overview :as vc-overview]))


(defn new-page []
  (layout
   [:div {:class "columns project-new-page"}
    [:div {:class "column col-6 col-mx-auto"}
     [:h1 {:class "headline"}"New Project"]
     [:form {:class "new-project-form form-horizontal" :method "POST" :action "/projects/new"}
      [:div {:class "form-group col-12"}
       [:label {:class "form-label" :for "title"} "Title"]
       [:input {:class "form-input" :type "text" :id "title" :name "project-title" :placeholder "Write your project title"}]
       ]
      [:div {:class "form-group col-12"}
       [:label {:class "form-label" :for "Description"} "Description"]
       [:textarea {:class "form-input"
                   :rows "10"
                   :id "description"
                   :name "project-description"
                   :placeholder "Write your project description"}]
       ]
      [:div {:class "btn-container"}
       [:button {:class "btn btn-primary"} "Save"]
       ]
      ]
     ]
    ]
   ))

(defn detail-page [req project]
  (layout
   [:div {:class "project-page"}
    [:div {:class "columns"}
     [:div {:class "column col-8 col-mx-auto"}
      [:div {:class "columns project-info"}
       [:div {:class "column col-12"}
        [:h2 {:class "text-bold"} (str "UG" "/" (:title project))]
        ]]]]
    [:div {:class "divider"}]
    [:div {:class "columns"}
     [:ul {:class "tab tab-block colum col-12 col-mx-auto"}
      [:li {:class "tab-item active"}
       [:a {:href "#"} "Overview"]
       ]
      [:li {:class "tab-item"}
       [:a {:href "#"} "Issues"]
       ]
      [:li {:class "tab-item"}
       [:a {:href "#"} "Setting"]
       ]
      ]
     ]
    (vc-overview/show req project)]))
