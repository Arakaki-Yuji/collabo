(ns collabo.handlers.utilities.issue)

;; TODO: closeale-user method is duplicated. it is also defined in collabo.repositories.issue
(defn is-closeable-user [{:keys [identity] :as session} issue]
  (if-not identity
    false
    (if (or (= (name identity) (:account_name issue))
            (= (name identity) (:issue_owner_name issue)))
      true
      false)))
  
  
