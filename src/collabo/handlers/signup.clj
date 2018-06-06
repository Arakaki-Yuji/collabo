(ns collabo.handlers.signup
  (:require [ring.util.response :as res]
            [collabo.views.index :as view-idx]
            [collabo.models.user :as user]
            [collabo.repositories.user :as repo-user]
            [clojure.walk :refer [keywordize-keys]]
            [collabo.handlers.base :refer [html]]
            [collabo.validates.core :refer [validate-email]]
            [buddy.auth :refer [authenticated?]]
            [clojure.tools.logging :as log]))

(defn get-signup [{:keys [session] :as req}]
  (if (authenticated? session)
    (res/redirect (str "/users/" (name (:identity session))))
    (html (view-idx/signup-page req))))

(defn post-signup [{:keys [params]}]
  (let [user-params (keywordize-keys params)]
    (do
      (log/info user-params)
      (if (empty? (:account_name user-params))
        (-> (res/redirect "/signup")
            (assoc :flash {:error "Account Name must be present."}))
        (if (empty? (:email user-params))
          (-> (res/redirect "/signup")
              (assoc :flash {:error "Email must be present."}))
          (if-not (validate-email (:email user-params))
            (-> (res/redirect "/signup")
                (assoc :flash {:error "Email must be email format."}))
            (if (empty? (:password user-params))
              (-> (res/redirect "/signup")
                  (assoc :flash {:error "Password must be present."}))
              (if (repo-user/exist-by-email (:email user-params))
                (-> (res/redirect "/signup")
                    (assoc :flash {:error "Email is already registered."}))
                (if (repo-user/exist-by-account-name (:account_name user-params))
                  (-> (res/redirect "/signup")
                    (assoc :flash {:error "Account Name is already registered."}))
                  (do
                    (user/create! user-params)
                    (res/redirect "/login"))
                  )))))))))
              
