(ns collabo.views.utilities.html
  (:require [clojure.string :as string]))

(defn nl2br [text]
  ;; new line code change to <br> tag
  (string/replace (str text) #"\n" "<br>"))
