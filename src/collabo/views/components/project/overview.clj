(ns collabo.views.components.project.overview
  (:require [clojure.string :as string]
            [hiccup.util :as h-util]
            [collabo.views.utilities.link :as vu-link]
            [collabo.views.utilities.html :refer [nl2br]]
            [collabo.repositories.project :refer [get-project-coverimage-url]]))

(defn show [req project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-lg-10 col-mx-auto"}
    [:div {:class "overview-show columns"}
     [:div {:class "description column col-12 col-mx-auto"}
      (nl2br (h-util/escape-html (str (:description project))))
     ]
     ]
    ]
   ]
  )

