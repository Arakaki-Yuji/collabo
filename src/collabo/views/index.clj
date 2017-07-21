(ns collabo.views.index
  (:require [hiccup.core :as h]
            [hiccup.page :as p]))

(defn layout [content]
  (p/html5
   [:head
    [:title "Collabo"]
    [:link {:rel "stylesheet" :href "/css/spectre.css"}]]
   [:body content]))

(def index-page
  (layout [:h1 "Hello World"]))
