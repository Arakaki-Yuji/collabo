(ns collabo.handlers.home
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [clojure.tools.logging :as log]))

(defn get-home [req]
  (if-not (authenticated? (:session req))
    (throw-unauthorized)
    (-> (res/response "<h1>Login Successed</h1>")
        (res/header "Content-Type" "text/html"))))
