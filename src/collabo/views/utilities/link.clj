(ns collabo.views.utilities.link
  (:require [clojure.string :refer [join]]))

(defn make-query-string [queries]
  (if (< 0 (count queries))
    (str "?" (join "&" queries))))

(defn make-query-string-from-map [_map]
  (let [queries (map #(str (first %) "=" (second %)) _map)]
    (make-query-string queries)))

(defn project-link
  ([project]
   (project-link project {}))
  ([{:keys [id] :as project} {:keys [tab] :as queries}]
   (let [tab-query (if tab (str "tab=" (name tab)))]
   (str "/projects/"
        id
        (make-query-string-from-map queries)
        ))))

(defn issue-link [issue]
  (str "/projects/" (:project_id issue) "/issues/" (:id issue)))

