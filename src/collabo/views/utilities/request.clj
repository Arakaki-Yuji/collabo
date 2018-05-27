(ns collabo.views.utilities.request)

(defn get-req-url [req]
  (str (name (:scheme req))
       "://"
       (:server-name req)       (:uri req)
       (if (:query-string req)
         (str "?" (:query-string req)))
       ))

(defn get-baseurl [req]
  (str (name (:scheme req))
       "://"
       (:server-name req)))
