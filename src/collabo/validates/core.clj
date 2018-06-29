(ns collabo.validates.core
  (:require [collabo.utilities.file :refer [get-file-extension]]
            [clojure.string :refer [lower-case]]))

(defn validate-filetype
  ([filename]
   (validate-filetype filename ["png", "jpeg", "jpg"]))
  ([filename, extentions]
   (let [ex (get-file-extension filename)]
     (if-not ex
       false
       (let [_ex (lower-case ex)]
         (some #{_ex} extentions))))))

(def email-format #"^(.+)@(.+)$")

(def alphanumeric-and-simbols #"^[a-zA-Z0-9-_.]+$")

(defn validate-alphanumeric-and-simbols [val]
  (if (re-find alphanumeric-and-simbols val)
    true
    false))

(defn validate-email [email]
  (if (re-find email-format email)
    true
    false))
