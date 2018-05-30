(ns collabo.utilities.file
  (:require [clojure.string :refer [split]]))


(defn get-file-extension [filename]
  (last (split filename #"\.")))
