(ns collabo.models.user
  (:require [collabo.repositories.user :as ru]
            [buddy.hashers :as hashers]))

(defrecord User
    [id
     email
     account_name
     password
     created_at
     updated_at])


(defn make-user [{:keys [id email account_name password created_at updated_at]}]
  (->User id email account_name password created_at updated_at))

(defn create! [{:keys [email account_name password]}]
  (let [result (ru/create-user email account_name password)]
    (make-user (first result))))

(defn make-password [raw-password]
  ;; password hashing
  (hashers/derive raw-password))

(defn password-check [raw hashed-pass]
  (hashers/check raw hashed-pass))

(defn new-user [email account-name raw-password]
  ;; return new User record. password is hashed
  (->User nil email account-name (make-password raw-password) nil nil))

(defn find-by-email [email]
  (let [result (ru/find-by-email email)]
    (map make-user result))
  )

(defn map-to-users [users-map]
  (map make-user users-map))
