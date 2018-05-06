(ns collabo.views.components.project.issues)

(defn show [req issues]
  [:div {:class "issues-show columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:h2 {:class "issue"} "Issues"]
    ]])
