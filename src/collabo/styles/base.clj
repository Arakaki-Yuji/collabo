(ns collabo.styles.base
  (:require [collabo.styles.layouts.signup :as l-signupin]
            [collabo.styles.pages.project :as lpp]
            [collabo.styles.pages.not-found :refer [not-found-page]]))

(def overwrite-spectre
  [:.empty {:background-color "#fff"}])


(def all-styles
  [
   [:header.navbar {:margin "20px"}]
   [overwrite-spectre]
   [l-signupin/styles]
   [lpp/new-page]
   [not-found-page]
   []
   ]
  )
