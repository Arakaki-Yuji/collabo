(ns collabo.views.layout
  (:require [hiccup.page :as page]
            [collabo.views.components.header :refer [header]]
            [collabo.views.utilities.request :refer [get-req-url]]))

(def default-ogptag
  {:title "Collabo"
   :description "Collabo is Open Collaboration Community. Let's Join us"
   :url ""
   :image ""})

(defn layout
  ([content]
   (layout default-ogptag (header) content))

  ([ogptag content]
   (layout ogptag (header) content))

  ([ogptag header-content content]
  (page/html5
   [:head
    [:title "Collabo"]
    [:meta {:charset "utf-8"}]

    [:meta {:property "og:title" :content (:title ogptag)}]
    [:meta {:property "og:type" :content "website"}]
    [:meta {:property "og:description" :content (:description ogptag)}]
    [:meta {:property "og:url" :content (:url ogptag)}]
    [:meta {:property "og:site_name" :content "Collabo"}]
    [:meta {:property "og:image" :content (:image ogptag)}]

    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-exp.min.css"}]
    [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-icons.min.css"}]
    [:link {:rel "stylesheet" :href "/css/build.css"}]
    [:script {:src "/cljs/main.js"}]
    ]
   [:body
    header-content
    [:div {:class "content"}
     content
     ]
    ])))

(defn layout-headerless
  ([content]
   (layout-headerless default-ogptag content))

  ([ogptag content]
   (page/html5
    [:head
     [:title "Collabo"]
     [:meta {:charset "utf-8"}]
     
     [:meta {:property "og:title" :content (:title ogptag)}]
     [:meta {:property "og:type" :content "website"}]
     [:meta {:property "og:description" :content (:description ogptag)}]
     [:meta {:property "og:url" :content (:url ogptag)}]
     [:meta {:property "og:site_name" :content "Collabo"}]
     [:meta {:property "og:image" :content (:image ogptag)}]
     
     [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre.min.css"}]
     [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-exp.min.css"}]
     [:link {:rel "stylesheet" :href "https://unpkg.com/spectre.css/dist/spectre-icons.min.css"}]
     [:link {:rel "stylesheet" :href "/css/build.css"}]
     [:script {:src "/cljs/main.js"}]
     ]
    [:body
     [:div {:class "content"}
      content
      ]])))
