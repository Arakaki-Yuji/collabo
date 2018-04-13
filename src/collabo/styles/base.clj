(ns collabo.styles.base
  (:require [collabo.styles.layouts.signup :as l-signupin]))

(def all-styles
  [
   [:header.navbar {:margin "20px"}]
   [l-signupin/styles]
   ]
  )
