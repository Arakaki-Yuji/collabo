(ns collabo.handlers.login
  (:require [ring.util.response :as res]
            [collabo.views.index :as view-idx]
            [collabo.models.user :as user]
            [clojure.walk :refer [keywordize-keys]]
            [buddy.hashers :as hashers]
            [buddy.auth :refer [authenticated?]]
            [collabo.handlers.base :refer [html]]))

(defn get-login [{:keys [session] :as req}]
  (if (authenticated? session)
    (res/redirect (str "/users/" (name (:identity session))))
    (html (view-idx/login-page req))))

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

(defn logout [{:keys [session] :as req}]
  (-> (res/redirect "/")
      (assoc :session {})))
