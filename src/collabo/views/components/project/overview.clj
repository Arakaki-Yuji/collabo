(ns collabo.views.components.project.overview
  (:require [clojure.string :as string]
            [collabo.views.utilities.link :as vu-link]
            [collabo.views.utilities.html :refer [nl2br]]
            [collabo.repositories.project :refer [get-coverimage-webpath]]))

(defn show [req project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:div {:class "overview-show columns"}
     [:div {:class "cover-image column col-12 col-mx-auto"}
      [:img {:src (get-coverimage-webpath project)}]
      ]
     [:div {:class "description column col-12 col-mx-auto"}
      (nl2br (str (:description project)))
     ]
     ]
    ]
   ]
  )

