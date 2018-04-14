(ns collabo.styles.base
  (:require [collabo.styles.layouts.signup :as l-signupin]
            [collabo.styles.pages.project :as lpp]))

(def all-styles
  [
   [:header.navbar {:margin "20px"}]
   [l-signupin/styles]
   [lpp/new-page]
   ]
  )
