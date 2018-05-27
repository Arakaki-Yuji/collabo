(ns collabo.views.home
  (:require [hiccup.core :as h]
            [collabo.views.utilities.string :refer [ellipsis]]
            [collabo.views.utilities.link :refer [project-link]]
            [collabo.views.layout :refer [layout-headerless]]
            [collabo.views.components.home.project-list :refer [project-list]]
            [collabo.repositories.project :refer [get-project-coverimage-url]]
            [collabo.models.user :refer [get-icon-public-path]]))

(defn trending-project [project]
  [:div {:class "project column col-3"}
   [:a {:href (project-link project)}
    [:div {:class "card"}
     [:div {:class "card-image"}
      [:img {:src (get-project-coverimage-url project) :class "image-responsive"}]]

     [:div {:class "card-header"}
      [:div {:class "card-title h5"} (:title project)]]

     [:div {:class "card-body"}
      (ellipsis (:description project) 100)
      ]
     ]
    ]
   ]
  )

(defn trending-user [user]
  [:div {:class "user column col-3"}
   [:a {:href (str "/users/" (:account_name user))}
    [:div {:class "card"}
     [:div {:class "card-image"}
      [:img {:src (get-icon-public-path user)
             :class "image-responsive"}]
      ]
     [:div {:class "card-header"}
      [:div {:class "card-title h5"} (:account_name user)]]

     [:div {:class "card-body"}
      (:aboutme user)
      ]
     ]
    ]
   ])


(defn home-page [projects users]
  (layout-headerless
   [:div {:class "home-page"}
    [:div {:class "overlay"}
     [:div {:class "columns"}
      [:div {:class "column col-8 col-mx-auto headline-container"}
       [:h1 {:class "headline"} "Comove"]
       [:p {:class "sub-headline"}  "Start projects with people all over the world"]
       ]
      ]
     [:div {:class "columns action-container"}
      [:div {:class "column col-2 col-ml-auto"}
       [:a {:href "/signup" :class "btn btn-primary btn-block"} "Signup"]]
      [:div {:class "column col-2 col-mr-auto"}
       [:a {:href "/login" :class "btn btn-primary btn-block"} "Login"]]
      ]
     ]

    [:div {:class "hot-projects"}
     [:h2 {:class "text-center headline"} "Trending Projects"]

     (apply conj
            [:div {:class "projects-container columns"}]
            (map trending-project projects))
     ]

    [:div {:class "hot-users"}
     [:h2 {:class "text-center headline"} "Trending Users"]
     (apply conj [:div {:class "users-container columns"}]
            (map trending-user users))
     ]
    ]
   )
  )
