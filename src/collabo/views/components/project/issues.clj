(ns collabo.views.components.project.issues
  (:require [collabo.views.utilities.link :as vu-link]))

(defn show [req issues project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:div {:class "action-area columns"}
     [:div {:class "column col-4 col-ml-auto my-2 text-right"}
      [:a {:class "btn btn-link btn-lg"
           :href (vu-link/project-link project {"tab" "issues" "action" "new"})}
       [:i {:class "icon icon-plus mr-2"}] "New Issue"]
      ]
     ]
    [:div {:class "issues-list"}
     [:div {:class "issues tile"}
      [:div {:class "tile-content"}
       [:p {:class "tile-title"} "Issue Title"]
       [:p {:class "tile-subtitle text-gray"} "created at 2018-04-21 by UG"]
       ]
      ]
     [:div {:class "issues tile"}
      [:div {:class "tile-content"}
       [:p {:class "tile-title"} "Issue Title"]
       [:p {:class "tile-subtitle text-gray"} "created at 2018-04-21 by UG"]
       ]
      ]
     [:div {:class "issues tile"}
      [:div {:class "tile-content"}
       [:p {:class "tile-title"} "Issue Title"]
       [:p {:class "tile-subtitle text-gray"} "created at 2018-04-21 by UG"]
       ]
      ]
     ]
    ]])

(defn new [req project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:div {:class "action-area columns"}
     [:div {:class "column col-4 col-ml-auto my-2 text-right"}
      [:button {:class "btn btn-primary btn-lg"
                :type "submit"}
       [:i {:class "icon icon-edit mr-2"}] "Save"]
      ]
     ]
    [:div {:class "issues-form"}
     [:form {:action (str "/projects/" (:id project) "/issues/new") :method "POST"}
      [:div {:class "form-group"}
       [:label {:class "form-label" :for "issue-title"} "Title"]
       [:input {:class "form-input" :type "text" :id "input-issue-title"}]
       ]
      [:div {:class "form-group"}
       [:label {:class "form-label" :for "issue-description"} "Description"]
       [:textarea {:class "form-input" :id "input-issue-description" :rows 10}]
       ]
      ]
     ]
    
    ]])
