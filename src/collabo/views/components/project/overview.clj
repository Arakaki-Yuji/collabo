(ns collabo.views.components.project.overview
  (:require [clojure.string :as string]))

(defn show [req project]
  [:div {:class "ovew-show columns"}
   [:div {:class "description column col-8 col-mx-auto"}
    (string/replace (str (:description project)) #"\n" "<br>")]]
  )
