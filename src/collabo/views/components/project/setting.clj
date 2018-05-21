(ns collabo.views.components.project.setting)

(defn show [{:keys [route-params] :as req}]
  [:div {:class "setting-show columns"}
   [:div {:class "column col-4"}
    [:ul {:class "menu"}
     [:li {:class "divider" :data-content "SETTING MENU"}]
     [:li {:class "menu-item"}
      [:a {:href "#"} "Delete Project"]
      ]
     ]
    ]

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
   ]
  )
