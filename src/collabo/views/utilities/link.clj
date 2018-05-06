(ns collabo.views.utilities.link
  (:require [clojure.string :refer [join]])
  )

(defn make-query-string [queries]
  (if (< 0 (count queries))
    (str "?" (join "&" queries))))

(defn make-query-string-from-map [_map]
  (let [queries (map #(str (first %) "=" (second %)) _map)]
    (make-query-string queries)))
