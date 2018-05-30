(ns collabo.handlers.login
  (:require [ring.util.response :as res]
            [collabo.views.index :as view-idx]
            [collabo.models.user :as user]
            [clojure.walk :refer [keywordize-keys]]
            [buddy.hashers :as hashers]
            [collabo.handlers.base :refer [html]]))

(defn get-login [req]
  (html (view-idx/login-page req)))

(defn post-login [req]
  (let [email (get-in req [:form-params "user-email"])
        password (get-in req [:form-params "user-password"])
        session (:session req)
        user (first (user/find-by-email email))]
    (if (and user
             (user/password-check password
                                  (:password user)))
      (let [updated-session (assoc session
                                   :identity
                                   (keyword (:account_name user)))]
        (-> (res/redirect (str "/users/" (:account_name user)))
            (assoc :session updated-session)))
      (-> (res/redirect "/login")
          (assoc :flash {:error "Email or Password is incorrenct."}))
          )))
