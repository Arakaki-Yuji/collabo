(ns collabo.views.utilities.string)

(defn take-head [string num]
  (if (< (count string) num)
    string
    (subs string 0 num)))

(defn ellipsis [string num]
  (if (< (count string) num)
    string
    (str (take-head string num) "...")))
