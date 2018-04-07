(ns collabo.views.home
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]))

(defn home-page []
  (layout [:h1 "Home Page"]))
