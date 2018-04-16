(ns collabo.views.not-found
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]))


(defn not-found-page []
  (layout
   [:div {:class "columns not-found-page"}
    [:h1 {:class "headline"} "404 Not found"]
    ]))
