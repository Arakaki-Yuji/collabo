(ns collabo.handlers.signup
  (:require [ring.util.response :as res]
            [collabo.views.index :as view-idx]
            [collabo.models.user :as user]
            [clojure.walk :refer [keywordize-keys]]
            [collabo.handlers.base :refer [html]]))

(defn get-signup [req]
  (html view-idx/signup-page))

(defn post-signup [{:keys [params]}]
  (let [created-user (user/create! (keywordize-keys params))]
    (html (str created-user))))
