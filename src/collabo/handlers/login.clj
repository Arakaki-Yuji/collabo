(ns collabo.handlers.login
  (:require [ring.util.response :as res]
            [collabo.views.index :as view-idx]
            [collabo.models.user :as user]
            [clojure.walk :refer [keywordize-keys]]))

(defn html [view]
  (-> (res/response view)
      (res/header "Content-Type" "text/html")))

(defn get-login [req]
  (html view-idx/login-page))

(defn post-login [req]
  (html view-idx/login-page))
