(ns collabo.views.components.project.overview
  (:require [clojure.string :as string]
            [collabo.views.utilities.link :as vu-link]))

(defn project-link
  ([{:keys [id] :as project} {:keys [tab] :as queries}]
   (let [tab-query (if tab (str "tab=" (name tab)))]
   (str "/projects/"
        id
        (vu-link/make-query-string-from-map queries)
        )))
  )


(defn show [req project]
  [:div
   [:div {:class "action-area columns"}
    [:div {:class "column col-4 col-ml-auto mt-2"}
     [:a {:class "btn btn-primary btn-lg"
          :href (project-link project {"tab" "overview" "action" "edit"})}
      [:i {:class "icon icon-edit mr-2"}] "Edit"]
     ]]
   [:div {:class "overview-show columns"}
    [:div {:class "description column col-8 col-mx-auto"}
     (string/replace (str (:description project)) #"\n" "<br>")]]
   ]
  )
