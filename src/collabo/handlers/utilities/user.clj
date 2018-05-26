(ns collabo.handlers.utilities.user)

(defn is-mypage [{:keys [identity] :as session} user]
  (if-not identity
    false
    (if (= (name identity) (:account_name user))
      true
      false)))
