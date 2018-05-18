(ns collabo.views.layout
  (:require [hiccup.page :as page]
            [collabo.views.components.header :refer [header]]))

(defn layout [content]
  (page/html5
   [:head
    [:title "Collabo"]
    [:meta {:charset "utf-8"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-exp.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-icons.min.css"}]
    [:link {:rel "stylesheet" :href "/css/build.css"}]
    [:script {:src "/cljs/main.js"}]
    ]
   
   [:body
    (header)
    content]))

(defn layout-headerless [content]
  (page/html5
   [:head
    [:title "Collabo"]
    [:meta {:charset "utf-8"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-exp.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-icons.min.css"}]
    [:link {:rel "stylesheet" :href "/css/build.css"}]
    [:script {:src "/cljs/main.js"}]
    ]
   [:body
    content]))
