(ns collabo.views.layout
  (:require [hiccup.page :as page]))

(defn layout [content]
  (page/html5
   [:head
    [:title "Collabo"]
    [:meta {:charset "utf-8"}]
    [:link {:rel "stylesheet" :href "/css/spectre.css"}]
    [:link {:rel "stylesheet" :href "/css/spectre-icons.min.css"}]
    [:link {:rel "stylesheet" :href "/css/build.css"}]]
   [:body content]))
