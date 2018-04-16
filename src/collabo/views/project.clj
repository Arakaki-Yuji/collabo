(ns collabo.views.project
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]))


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
