(ns collabo.views.components.project.setting)

(defn delete-project-comp [{:keys [route-params]}]
  [:div {:class "column col-8 col-mx-auto"}
   [:div {:class "panel"}
    [:div {:class "panel-header"}
     [:h2 {:class "text-center"} "Delete Project"]
     ]

    [:div {:class "panel-body"}
     [:form {:action (str "/projects/" (:id route-params) "/delete")
             :method "POST"
             :id "form-delete-project"}
      [:p "Once you delete a projects, there is no go back."]
      ]
     ]
    [:div {:class "panel-footer"}
     [:button {:class "btn btn-error btn-block" :form "form-delete-project" :type "submit"} "DELETE"]
     ]
    ]
   ]
  )

(defn edit-project-title [{:keys [route-params]}]
  [:div {:class "column col-8 col-mx-auto"}
   [:div {:class "panel"}
    [:div {:class "pnale-header"}
     [:h2 {:class "text-center"} "Edit Project Title"]]

    [:div {:class "panel-body"}
     [:form {:action (str "/projects/" (:id route-params) "/title")
             :method "POST"
             :id "form-project-title"}
      [:div {:class "form-group"}
       [:label {:class "form-label" :for "project-title"} "Project title"]
       [:input {:class "form-input" :type "text" :id "project-title" :name "project-title"}]
       ]
      ]
     ]

    [:div {:class "panel-footer"}
     [:button {:class "btn btn-primary btn-block" :type "submit" :form "form-project-title"} "UPDATE"]]
    ]
   ])

(defn show [{:keys [route-params query-params] :as req}]
  [:div {:class "setting-show columns"}
   [:div {:class "column col-4"}
    [:ul {:class "menu"}
     [:li {:class "divider" :data-content "SETTING MENU"}]
     [:li {:class "menu-item"}
      [:a {:href (str "/projects/" (:id route-params) "?tab=setting&menu=delete")} "Delete Project"]
      ]
     [:li {:class "menu-item"}
      [:a {:href (str "/projects/" (:id route-params) "?tab=setting&menu=title")} "Edit Project Title"]
      ]
     ]
    ]

   (case (get query-params "menu")
     "delete" (delete-project-comp req)
     "title" (edit-project-title req)
     (delete-project-comp req))
   ]
  )
