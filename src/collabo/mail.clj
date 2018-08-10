(ns collabo.mail
  (:require [collabo.config.mail :refer [mail mail-from]]
            [postal.core :as postal]))

(def default {:from mail-from})

(defn send-mail [{:keys [from to subject body] :as opts}]
  (postal/send-message mail (merge default opts)))
