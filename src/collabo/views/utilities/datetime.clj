(ns collabo.views.utilities.datetime
  (:require [clj-time.core :as tc]
            [clj-time.format :as tf]))

(defn datetime-format [datetime]
  (tf/unparse (tf/formatter-local "yyyy-MM-dd HH:mm:ss")
              (tc/to-time-zone datetime (tc/time-zone-for-id "Asia/Tokyo"))))

