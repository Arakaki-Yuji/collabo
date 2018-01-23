(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application.

  Call `(reset)` to reload modified code and (re)start the system.

  The system under development is `system`, referred from
  `com.stuartsierra.component.repl/system`.

  See also https://github.com/stuartsierra/component.repl"
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as string]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all clear]]
   [com.stuartsierra.component :as component]
   [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
   [alembic.still :refer [load-project]]
   [collabo.core :as core]
   [ragtime.jdbc :as jdbc]
   [ragtime.repl]
   [collabo.config.db :as db-config]))

;; Do not try to load source code from 'resources' directory
(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src" "test")

(defn dev-system
  "Constructs a system map suitable for interactive development."
  []
  (core/main-system {:port 3000})
  )

(def migration-config
  {:datastore  (jdbc/sql-database db-config/mysql-db)
   :migrations (jdbc/load-resources "migrations")})

;; database migration (ragtime.repl/migrate migration-config)
;; database rollback (ragtime.repl/rollback migration-config)

(set-init (fn [_] (dev-system)))
