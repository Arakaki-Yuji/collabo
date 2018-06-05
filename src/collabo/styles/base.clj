(ns collabo.styles.base
  (:require [collabo.styles.layouts.signup :as l-signupin]
            [collabo.styles.pages.project :as lpp]
            [collabo.styles.pages.not-found :refer [not-found-page]]
            [collabo.styles.pages.home :refer [home-page]]
            [collabo.styles.pages.user :refer [user-page]]))

(def overwrite-spectre
  [:.empty {:background-color "#fff"}])

(def all-styles
  [
   [:header {:height "56px"
             :border-bottom "1px solid #e7e9ed"}
    [:.site-icon {:margin-left "30px"}]
    [:.header-right {:margin-right "30px"}]]
   [overwrite-spectre]
   [l-signupin/styles]
   [lpp/new-page]
   [lpp/project-page]
   [not-found-page]
   [home-page]
   [user-page]
   ]
  )
