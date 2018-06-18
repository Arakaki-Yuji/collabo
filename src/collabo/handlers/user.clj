(ns collabo.handlers.user
  (:require [collabo.views.user :as v-user]
            [collabo.handlers.base :refer [html]]
            [collabo.models.user :as m-user]
            [collabo.repositories.user :as repo-user]
            [collabo.repositories.project :as pj-repo]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [redirect]]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [clojure.string :refer [trim]]
            [collabo.validates.core :as v]
            [collabo.utilities.azure.blob :as blob]))

(defn get-user [{:keys [route-params query-params session] :as req}]
  (let [user (m-user/find-by-identity (:account_name route-params))
        current-user (if (authenticated? session)
                       (m-user/find-by-identity (name (:identity session)))
                       nil)
        user-projects (pj-repo/find-owned-projects (:id user))]
    (do
     (log/info (get query-params "tab"))
     (log/info (type (first (keys query-params))))
     (log/info req)
     (html (v-user/user-page req
                             current-user
                             user
                             user-projects
                             (get query-params "tab")
                             (get query-params "menu"))))))

(defn post-aboutme [{:keys [route-params form-params session]}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [identity (:identity session)
          aboutme (get-in form-params ["user-aboutme"])
          account_name (:account_name route-params)]
      (do
        (log/info aboutme)
        (if (= (name identity) account_name)
          (repo-user/update-aboutme-by-account_name account_name aboutme))
        (redirect (str "/users/" account_name "?tab=setting&menu=aboutme"))
        )
      )
    ))


(defn post-email [{:keys [route-params form-params session]}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [identity (:identity session)
          current-email (trim (get-in form-params ["user-current-email"] ""))
          user-new-email (trim (get-in form-params ["user-new-email"] ""))
          account_name (:account_name route-params)
          user (m-user/find-by-identity account_name)]
      (do
        (if (= (name identity) account_name)
          (if (or (empty? current-email)
                  (empty? user-new-email))
            (-> (redirect (str "/users/" account_name "?tab=setting&menu=email"))
                (assoc :flash {:error "Current Email and New Email must are present."}))
            (if (= current-email (:email user))
              (if-not (v/validate-email user-new-email)
                (-> (redirect (str "/users/" account_name "?tab=setting&menu=email"))
                    (assoc :flash {:error "New Email must be email format."}))
                (if (repo-user/exist-by-email user-new-email)
                  (-> (redirect (str "/users/" account_name "?tab=setting&menu=email"))
                      (assoc :flash {:error "New Email already registered."}))
                  (do
                    (repo-user/update-email-by-account_name account_name user-new-email)
                    (-> (redirect (str "/users/" account_name "?tab=setting&menu=email"))
                        (assoc :flash {:success "Success: Email is updated."})))))
              (-> (redirect (str "/users/" account_name "?tab=setting&menu=email"))
                  (assoc :flash {:error "Current Email is wrong."})))
            )
          (do
            (log/info "failed")
            (redirect (str "/users/" account_name "?tab=setting&menu=email"))))))))


(defn post-icon [{:keys [route-params params session]}]
  (if-not (authenticated? session)
    (throw-unauthorized)
    (let [account_name (:account_name route-params)
          user-icon (get params "user-icon")
          current-user (m-user/find-by-identity (name (:identity session)))
          save-filename (str account_name "-" (:filename user-icon))]
      (if-not (= (name (:identity session))
                 account_name)
        (redirect (str "/users/" account_name "?tab=setting&menu=icon"))
        (if-not (v/validate-filetype (:filename user-icon))
          (-> (redirect (str "/users/" account_name "?tab=setting&menu=icon"))
              (assoc :flash {:error "Icon image must be jpg or png"}))
          (let [blobname (blob/make-user-icon-blobname current-user
                                                       (blob/make-random-filename (:filename user-icon)))]
            (do
              (log/info current-user)
              (log/info user-icon)
              (blob/upload-image (:tempfile user-icon) blobname)
              (repo-user/update-icon-by-account_name account_name blobname)
              (-> (redirect (str "/users/" account_name "?tab=setting&menu=icon"))
                  (assoc :flash {:success "Success: Icon image is updated."}))
            )))))))
