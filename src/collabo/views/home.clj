(ns collabo.views.home
  (:require [hiccup.core :as h]
            [collabo.views.layout :refer [layout]]))

(defn home-page [user]
  (layout
   [:div {:class "home-page"}
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
      [:div {:class "empty"}
       [:div {:class "empty-icon"}
        [:i {:class "icon icon-3x icon-people"}]]
       [:p {:class "empty-title h5"} "You have no projects"]
       [:p {:class "empty-subtitle"} "Click the button to create new projects!"]
       [:div {:class "empty-action"}
        [:a  {:class "btn btn-primary" :href "/projects/new"} "Create New Projects"]]]
      ]
     ]
    ]
   )
  )
