(ns collabo.views.layout
  (:require [hiccup.page :as page]
            [collabo.views.components.header :refer [header]]))

(def default-ogptag
  {:title "Comove"
   :description "Comove is Open Collaboration Community. Let's Join us"
   :url ""
   :image ""})

(defn layout
  ([content]
   (layout default-ogptag content))

  ([ogptag content]
  (page/html5
   [:head
    [:title "Comove"]
    [:meta {:charset "utf-8"}]

    [:meta {:property "og:title" :content (:title ogptag)}]
    [:meta {:property "og:type" :content "website"}]
    [:meta {:property "og:description" :content (:description ogptag)}]
    [:meta {:property "og:url" :content (:url ogptag)}]
    [:meta {:property "og:site_name" :content "Comove"}]
    [:meta {:property "og:image" :content (:image ogptag)}]

    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-exp.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-icons.min.css"}]
    [:link {:rel "stylesheet" :href "/css/build.css"}]
    [:script {:src "/cljs/main.js"}]
    ]
   [:body
    (header)
    content])))

(defn layout-headerless
  ([content]
   (layout-headerless default-ogptag content))

  ([ogptag content]
   (page/html5
    [:head
     [:title "Comove"]
     [:meta {:charset "utf-8"}]
     [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre.min.css"}]
     [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-exp.min.css"}]
     [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-icons.min.css"}]
     [:link {:rel "stylesheet" :href "/css/build.css"}]
     [:script {:src "/cljs/main.js"}]
     ]
    [:body
     content])))
