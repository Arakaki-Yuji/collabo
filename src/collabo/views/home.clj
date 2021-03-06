(ns collabo.views.home
  (:require [hiccup.core :as h]
            [hiccup.util :as h-util]
            [collabo.views.utilities.string :refer [ellipsis]]
            [collabo.views.utilities.link :refer [project-link]]
            [collabo.views.layout :refer [layout-headerless]]
            [collabo.views.components.home.project-list :refer [project-list]]
            [collabo.repositories.project :refer [get-project-coverimage-url]]
            [collabo.models.user :refer [get-icon-public-path]]
            [buddy.auth :refer [authenticated?]]))

(defn trending-project [project]
  [:div {:class "project column col-3 col-lg-12"}
   [:a {:href (project-link project)}
    [:div {:class "card"}
     [:div {:class "card-image"}
      [:img {:src (get-icon-public-path project)
             :class "image-responsive"}]]

     [:div {:class "card-header"}
      [:div {:class "card-title h5"} (h-util/escape-html (:title project))]]

     [:div {:class "card-body"}
      (ellipsis (h-util/escape-html (:description project)) 100)
      ]
     ]
    ]
   ]
  )

(defn trending-user [user]
  [:div {:class "user column col-3 col-lg-12"}
   [:a {:href (str "/users/" (:account_name user))}
    [:div {:class "card"}
     [:div {:class "card-image"}
      [:img {:src (get-icon-public-path user)
             :class "image-responsive"}]
      ]
     [:div {:class "card-header"}
      [:div {:class "card-title h5"} (:account_name user)]]

     [:div {:class "card-body"}
      (h-util/escape-html (:aboutme user))
      ]
     ]
    ]
   ])


(defn home-page [{:keys [session] :as req}]
  (layout-headerless
   [:div {:class "home-page"}
    [:div {:class "overlay"}
     [:div {:class "columns"}
      [:div {:class "column col-8 col-lg-10 col-mx-auto headline-container"}
       [:h1 {:class "headline"} "Collabo"]
       [:p {:class "sub-headline"}  "Start projects with people all over the world"]
       ]
      ]

     (if-not (authenticated? session)
       [:div {:class "columns action-container"}
        [:div {:class "column col-lg-4 col-2 col-ml-auto"}
         [:a {:href "/signup" :class "btn btn-primary btn-block"} "Signup"]]
        [:div {:class "column col-lg-4 col-2 col-mr-auto"}
         [:a {:href "/login" :class "btn btn-primary btn-block"} "Login"]]
        ]
       [:div {:class "columns action-container"}
        [:div {:class "column col-lg-4 col-2 col-mx-auto"}
         [:a {:href (str "/users/" (name (:identity session))) :class "btn btn-primary btn-block"} "Go to MyPage"]]
        ])
     ]
    [:div {:class "columns about-vision"}
     [:div {:class "column col-6 col-lg-10 col-mx-auto" }
      [:h3 {:class "text-headline"} "Create your projects and share vision"]
      [:p {:class "text"} "You can create your projects and share the vision. By sharing vision,  people who have empathy the vision can join your projects."]
      ]
     [:div {:class "column col-6 col-lg-10 col-mx-auto"}
      [:img {:src "/images/sample-vision.png"}]]
     ]

    [:div {:class "columns about-issue"}
     [:div {:class "column col-6 col-lg-10 col-mx-auto" }
      [:h3 {:class "text-headline"} "Track issues for your projects"]
      [:p {:class "text"} "You can create issues for your projects and track it. You can communicate about issues by comment with your project members."]
      ]
     [:div {:class "column col-6 col-lg-10 col-mx-auto"}
      [:img {:src "/images/sample-issue.png"}]
      ]
     ]]))
