(ns collabo.views.project
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]
            [collabo.views.components.project.overview :as vc-overview]
            [collabo.views.components.project.issues :as vc-issues]
            [collabo.views.components.project.setting :as vc-setting]))


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

(defn detail-page [{:keys [query-params] :as req} project issues]
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
      [:li {:class (str "tab-item" (if (= "overview" (get query-params "tab")) " active"))}
       [:a {:href (str "/projects/" (:id project) "?tab=overview")} "Overview"]
       ]
      [:li {:class (str "tab-item" (if (= "issues" (get query-params "tab")) " active"))}
       [:a {:href (str "/projects/" (:id project) "?tab=issues")} "Issues"]
       ]
      [:li {:class (str "tab-item" (if (= "setting" (get query-params "tab")) " active"))}
       [:a {:href (str "/projects/" (:id project) "?tab=setting")} "Setting"]
       ]
      ]
     ]
    (case (get query-params "tab")
      "overview" (case (get query-params "action")
                   "edit" (vc-overview/edit req project)
                   (vc-overview/show req project))
      "issues" (case (get query-params "action")
                 "new" (vc-issues/new req project)
                 (vc-issues/show req issues project))
      "setting" (vc-setting/show req)
      (vc-overview/show req project))
    ]))
