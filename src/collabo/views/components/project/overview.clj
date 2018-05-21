(ns collabo.views.components.project.overview
  (:require [clojure.string :as string]
            [collabo.views.utilities.link :as vu-link]))

(defn show [req project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:div {:class "action-area columns"}
     [:div {:class "column col-4 col-ml-auto my-2 text-right"}
      [:a {:class "btn btn-link btn-lg"
           :href (vu-link/project-link project {"tab" "overview" "action" "edit"})}
       [:i {:class "icon icon-edit mr-2"}] "Edit"]
      ]]
    [:div {:class "overview-show columns"}
     [:div {:class "description column col-12 col-mx-auto"}
      (string/replace (str (:description project)) #"\n" "<br>")]]
    ]]
  )

(defn edit [req project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:form {:method "POST"
            :action (str "/projects/" (:id project) "/description")
            :id "form-project-description"}
    [:div {:class "action-area columns"}
     [:div {:class "column col-4 col-ml-auto my-2 text-right"}
      [:button {:class "btn btn-primary btn-lg" :form "form-project-description"}
       [:i {:class "icon icon-edit mr-2"}] "Save"]
      ]]
    [:div {:class "overview-show columns"}
     [:div {:class "description column col-12 col-mx-auto"}
      [:textarea {:class "form-input"
                  :rows "10"
                  :id "description"
                  :name "project-description"
                  } (:description project)]]]
    ]]])
