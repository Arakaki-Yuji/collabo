(ns collabo.handlers.utilities.project)

(defn current-user-is-owner [{:keys [identity] :as session} project]
  (if (= identity (:account_name project))
    true
    false))
