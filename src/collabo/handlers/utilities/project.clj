(ns collabo.handlers.utilities.project)

(defn current-user-is-owner [{:keys [identity] :as session} project]
  (if-not identity
    false
    (if (= (name identity) (:account_name project))
      true
      false)))
