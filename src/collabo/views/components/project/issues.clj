(ns collabo.views.components.project.issues
  (:require [collabo.views.utilities.link :as vu-link]
            [clj-time.local :as tl]))

(defn issue-tile [{:keys [id title created_at account_name] :as issue} project-id]
  [:a {:href (str "/projects/" project-id "/issues/" id)}
   [:div {:class "issues tile c-hand"}
    [:div {:class "tile-content"}
     [:p {:class "tile-title"} title]
     [:p {:class "tile-subtitle text-gray"} (str "created at " (tl/format-local-time created_at :mysql) " by " account_name)]
     ]
    ]
   ])

(defn issue-list [issues project]
  (let [container [:div {:class "issues-list"}]
        tiles (map #(issue-tile % (:id project)) issues)]
        (apply conj container tiles)))

(defn show [{:keys [session] :as req} issues project]
  [:div {:class "issues-show columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:div {:class "action-area columns"}
     (if (:identity session)
       [:div {:class "column col-4 col-ml-auto my-2 text-right"}
        [:a {:class "btn btn-link btn-lg"
             :href (vu-link/project-link project {"tab" "issues" "action" "new"})}
         [:i {:class "icon icon-plus mr-2"}] "New Issue"]
        ])
     ]
    (issue-list issues project)
    ]])

(defn new [{:keys [session flash] :as req} project]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    [:div {:class "action-area columns"}
     [:div {:class "column col-4 col-ml-auto my-2 text-right"}
      [:button {:class "btn btn-primary btn-lg"
                :type "submit"
                :form "form-new-issue"}
       [:i {:class "icon icon-edit mr-2"}] "Save"]
      ]
     ]
    (if (get-in flash [:error])
      [:div {:class "toast toast-error my-2"} (get-in flash [:error])])
    [:div {:class "issues-form"}
     [:form {:action (str "/projects/" (:id project) "/issues/new")
             :method "POST"
             :id "form-new-issue"}
      [:div {:class "form-group"}
       [:label {:class "form-label" :for "issue-title"} "Title"]
       [:input {:class "form-input" :type "text" :id "input-issue-title" :name "issue-title"}]
       ]
      [:div {:class "form-group"}
       [:label {:class "form-label" :for "issue-comment"} "Comment"]
       [:textarea {:class "form-input" :id "input-issue-comment" :rows 10 :name "issue-comment"} ]
       ]
      ]
     ]
    ]])
