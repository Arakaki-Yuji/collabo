(ns collabo.webserver
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jty]))

(defrecord Webserver [port handler server]
  component/Lifecycle

  (start [component]
    (println ";; Starting webserver")
    (let [s (jty/run-jetty handler {:port port :join? false})]
      (assoc component :server s)))

  (stop [component]
    (println ";; Stopping werbserver")
    (if (not (nil? server))
      (do (.stop server)
          (assoc component :server nil))
      component))
  )

(defn new-webserver [port handler]
  (map->Webserver {:port port :handler handler}))

(defn sample-handler [res]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Goodbye"})
