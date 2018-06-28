(ns collabo.views.components.project.setting)

(defn delete-project-comp [{:keys [route-params]}]
  [:div {:class "column col-8 col-mx-auto my-1"}
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

(defn edit-project-title [{:keys [route-params flash]}]
  [:div {:class "column col-8 col-mx-auto my-1"}
   [:div {:class "panel"}
    [:div {:class "panel-header"}
     [:h2 {:class "text-center"} "Edit Project Title"]]

    [:div {:class "panel-body"}
     [:form {:action (str "/projects/" (:id route-params) "/title")
             :method "POST"
             :id "form-project-title"}
      (if (get-in flash [:error])
        [:div {:class "toast toast-error"} (get-in flash [:error])])
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


(defn edit-overview-description [{:keys [route-params]} project]
  [:div {:class "column col-8 col-mx-auto my-1"}
   [:div {:class "panel"}
    [:div {:class "panel-header"}
     [:h2 {:class "text-center"} "Edit Vision"]
     ]

    [:div {:class "panel-body"}
     [:form {:method "POST"
             :action (str "/projects/" (:id project) "/description")
             :id "form-project-description"}
      [:div {:class "overview-show columns"}
       [:div {:class "description column col-12 col-mx-auto"}
        [:textarea {:class "form-input"
                    :rows "10"
                    :id "description"
                    :name "project-description"
                    } (:description project)]]]
      ]
     ]

    [:div {:class "panel-footer"}
     [:button {:class "btn btn-primary btn-block" :form "form-project-description"}
      [:i {:class "icon icon-edit mr-2"}] "Save"]
     ]
    ]]
  )

(defn edit-overview-coverimage [{:keys [route-params flash]} project]
[:div {:class "column col-8 col-mx-auto my-1"}
   [:div {:class "panel"}
    [:div {:class "panel-header"}
     [:h2 {:class "text-center"} "Edit Cover image"]
     ]

    [:div {:class "panel-body"}
     [:form {:method "POST"
             :action (str "/projects/" (:id project) "/coverimage")
             :enctype "multipart/form-data"
             :id "form-project-coverimage"}
      (if (get-in flash [:error])
        [:div {:class "toast toast-error"} (get-in flash [:error])])
      [:div {:class "form-group"}
       [:label {:class "form-label" :for "project-coverimage"} "Cover image"]
       [:input {:class "form-input" :type "file" :id "project-coverimage" :name "project-coverimage"}]
       ]
      ]
     ]

    [:div {:class "panel-footer"}
     [:button {:class "btn btn-primary btn-block" :form "form-project-coverimage"}
      [:i {:class "icon icon-edit mr-2"}] "UPDATE"]
     ]
    ]]
  )

(defn show [{:keys [route-params query-params] :as req} project]
  [:div {:class "setting-show columns"}
   [:div {:class "column col-4"}
    [:ul {:class "menu"}
     [:li {:class "divider" :data-content "SETTING MENU"}]
     
     [:li {:class "menu-item"}
      [:a {:href (str "/projects/" (:id route-params) "?tab=setting&menu=overview-description")} "Edit Vision"]
      ]

     [:li {:class "menu-item"}
      [:a {:href (str "/projects/" (:id route-params) "?tab=setting&menu=title")} "Edit Project Title"]
      ]
     
     [:li {:class "menu-item"}
      [:a {:href (str "/projects/" (:id route-params) "?tab=setting&menu=delete")} "Delete Project"]
      ]
     ]
    ]

   (case (get query-params "menu")
     "delete" (delete-project-comp req)
     "title" (edit-project-title req)
     "overview-description" (edit-overview-description req project)
     "overview-coverimage" (edit-overview-coverimage req project)
     (delete-project-comp req))
   ]
  )
