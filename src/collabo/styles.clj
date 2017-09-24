(ns collabo.styles
  (:require [garden.core :refer [css]]
            [com.stuartsierra.component :as component]
            [collabo.styles.base :refer [all-styles]]))

(def build-css-path "resources/public/css/build.css")

(def app-styles [[:body {:background-color "#fff"}]])

(defrecord CssBuilder [output-to styles]
  component/Lifecycle
  (start [component]
    (println ";; Building CSS")
    (css {:output-to output-to} styles))
  (stop [component]
    (println ";; Stoping CSS")))

(defn new-cssbuilder []
  (map->CssBuilder {:output-to build-css-path :styles all-styles}))
