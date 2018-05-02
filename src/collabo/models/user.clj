(ns collabo.models.user
  (:require [collabo.repositories.user :as ru]
            [buddy.hashers :as hashers]))

(defrecord User
    [id
     email
     account_name
     password
     aboutme
     icon
     created_at
     updated_at])


(defn make-user [{:keys [id email account_name password aboutme icon created_at updated_at]}]
  (->User id email account_name password aboutme icon created_at updated_at))

(defn create! [{:keys [email account_name password aboutme icon]}]
  (let [result (ru/create-user email account_name password aboutme icon)]
    (make-user (first result))))

(defn make-password [raw-password]
  ;; password hashing
  (hashers/derive raw-password))

(defn password-check [raw hashed-pass]
  (hashers/check raw hashed-pass))

(defn new-user [email account-name raw-password aboutme icon]
  ;; return new User record. password is hashed
  (->User nil email account-name (make-password raw-password) aboutme icon nil nil))

(defn find-by-email [email]
  (let [result (ru/find-by-email email)]
    (map make-user result))
  )

(defn map-to-users [users-map]
  (map make-user users-map))

(defn find-by-identity [identity]
  (let [users (ru/find-one-by-account_name identity)]
    (if-not (= 0 (count users))
      (first (map-to-users users))
      nil)))
