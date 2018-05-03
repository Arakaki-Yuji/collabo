(ns collabo.views.user
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]
            [collabo.views.components.home.project-list :refer [project-list]]
            [clojure.string :refer [join]]))

(defn make-query-string [queries]
  (if (< 0 (count queries))
    (str "?" (join "&" queries))))

(defn make-link
  ([user]
   (make-link user nil nil))
  ([user tab]
   (make-link user tab nil))
  ([user tab menu]
   (let [tab-query (if tab (str "tab=" (name tab)))
         menu-query (if menu (str "menu=" (name menu)))]
     (str "/users/"
          (:account_name user)
          (make-query-string (filter identity [tab-query menu-query]))
          ))))

(defn projects-component [projects]
  [:div {:class "columns"}
   [:div {:class "column col-8 col-mx-auto"}
    (project-list projects)
    ]])

(defn edit-email-cmp [req user]
  [:div {:class "panel"}
   [:div {:class "panel-header"}
    [:h2 {:class "text-center"} "Edit Email"]]
   [:div {:class "panel-body"}
    (when-let [{:keys [success]} (:flash req)]
      (if success
        [:div {:class "toast toast-success"} (str success)]))
    (when-let [{:keys [error]} (:flash req)]
      (if error
        [:div {:class "toast toast-error"} (str error)]))
    [:form {:action (str "/users/" (:account_name user) "/email")
            :method "POST"
            :id "form-email"}
     [:div {:class "form-group"}
      [:label {:class "form-label" :for "user-current-email"} "Current Email"]
      [:input {:class "form-input" :type "email" :id "user-current-email" :name "user-current-email"}]
      ]
     [:div {:class "form-group"}
      [:label {:class "form-label" :for "user-new-email"} "New Email"]
      [:input {:class "form-input" :type "email" :id "user-new-email" :name "user-new-email"}]
      ]
     ]
    ]
   [:div {:class "panel-footer"}
    [:button {:class "btn btn-primary btn-block" :type "submit" :form "form-email"} "UPDATE"]
    ]
   ])

(defn edit-aboutme-cmp [user]
  [:div {:class "panel"}
   [:div {:class "panel-header"}
    [:h2 {:class "text-center"} "Edit Aboutme"]]
   [:div {:class "panel-body"}
    [:form {:action (str "/users/" (:account_name user) "/aboutme")
            :method "POST"
            :id "form-aboutme"}
     [:div {:class "form-group"}
      [:label {:class "form-label" :for "user-aboutme"} "About me"]
      [:textarea {:class "form-input" :id "user-aboutme" :name "user-aboutme" :rows 5} (:aboutme user)]
      ]
     ]
    ]
   [:div {:class "panel-footer"}
    [:button {:class "btn btn-primary btn-block" :form "form-aboutme" :type "submit"} "UPDATE"]]
   ]
  )

(defn edit-icon-cmp []
  [:div {:class "panel"}
   [:div {:class "panel-header"}
    [:h2 {:class "text-center"} "Edit Icon image"]]
   [:div {:class "panel-body"}
    [:form
     [:div {:class "form-group"}
      [:label {:class "form-label" :for "user-icon"} "Current Icon"]
      [:input {:class "form-input" :type "file" :id "user-icon" :name "user-icon"}]
      ]
     ]
    ]
   [:div {:class "panel-footer"}
    [:button {:class "btn btn-primary btn-block"} "UPDATE"]
    ]
   ])

(defn setting-component [menu body-comp user]
  [:div {:class "columns"}
   [:div {:class "column col-4"}
    [:ul {:class "menu"}
     [:li {:class "divider" :data-content "SETTING MENU"}]
     [:li {:class "menu-item"}
      [:a {:href (make-link user :setting :email) :class (if (= menu :email) "active")} "Edit Email"]
      ]
     [:li {:class "menu-item"}
      [:a {:href (make-link user :setting :aboutme) :class (if (= menu :aboutme) "active")} "Edit About me"]
      ]
     [:li {:class "menu-item"}
      [:a {:href (make-link user :setting :icon)
           :class (if (= menu :icon) "active")} "Edit Profile icon"]
      ]
     ]
    ]
   [:div {:class "column col-8"}
      body-comp
    ]
   ]
  )

(defn user-page [req user projects tab menu]
  (layout
   [:div {:class "user-page"}
    [:div {:class "columns"}
     [:div {:class "column col-8 col-mx-auto"}
      [:div {:class "columns user-info"}
       [:div {:class "icon-wrapper column col-2"}
        [:figure {:class "avatar avatar-xl" :data-initial "YJ"}
         [:img {:src "/images/profile-icon.jpg"}]
         ]]
       [:div {:class "column col-10"}
        [:h2 {:class "text-bold"} (:account_name user)]
        [:p (:aboutme user)]
        ]
       ]
      ]
     ]
    [:div {:class "divider"}]
    [:div {:class "columns"}
     [:ul {:class "tab tab-block colum col-12 col-mx-auto"}
      [:li {:class (str "tab-item" (if (= (keyword tab) :projects) " active"))}
       [:a {:href (make-link user :projects)} "Projects"]
       ]
      [:li {:class (str "tab-item" (if (= (keyword tab) :setting) " active"))}
       [:a {:href (make-link user :setting)} "Setting"]
       ]
      ]
     ]
    (case (keyword tab)
      :projects (projects-component projects)
      :setting (case (keyword menu)
                 :aboutme (setting-component (keyword menu) (edit-aboutme-cmp user) user)
                 :email (setting-component (keyword menu) (edit-email-cmp req user) user)
                 :icon (setting-component (keyword menu) (edit-icon-cmp) user)
                 (setting-component (keyword menu) (edit-email-cmp req user) user))
      (projects-component projects)
      )
    ]
   )
  )
