(ns collabo.views.user
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]
            [collabo.views.components.home.project-list :refer [project-list]]))

(defn user-page [user projects]
  (layout
   [:div {:class "user-page"}
    [:div {:class "columns"}
     [:div {:class "column col-8 col-mx-auto"}
      [:div {:class "columns user-info"}
       [:div {:class "icon-wrapper column col-2"}
        [:figure {:class "avatar avatar-xl" :data-initial "YJ"}
         [:img {:src "/images/profile-icon.jpg"}]
         ]]
       [:div {:class "column col-10 "}
        [:h2 {:class "text-bold"} (:account_name user)]
        [:p "Software Enginner in Okinawa. Founder of Collabo. member of Javakueche. Husband, Father."]
        ]
       ]
      ]
     ]
    [:div {:class "divider"}]
    [:div {:class "columns"}
     [:ul {:class "tab tab-block colum col-12 col-mx-auto"}
      [:li {:class "tab-item"}
       [:a {:href "#"} "Timeline"]
       ]
      [:li {:class "tab-item active"}
       [:a {:href "#"} "Projects"]
       ]
      [:li {:class "tab-item"}
       [:a {:href "#"} "Profile"]
       ]
      [:li {:class "tab-item"}
       [:a {:href "#"} "Setting"]
       ]
      ]
     ]
    [:div {:class "columns"}
     [:div {:class "column col-8 col-mx-auto"}
      (project-list projects)
      ]
     ]
    ]
   )
  )
