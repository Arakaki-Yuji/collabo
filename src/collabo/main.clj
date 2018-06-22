(ns collabo.main
  (:require [collabo.core :as core]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [environ.core :refer [env]])
  (:gen-class))

(defn -main [& [:as args]]
 (component/start (core/main-system {:port (Integer/parseInt (env :port))})))
