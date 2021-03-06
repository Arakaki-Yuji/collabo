(ns collabo.views.project
  (:require [hiccup.core :as h]
            [hiccup.util :as h-util]
            [collabo.views.layout :refer [layout default-ogptag]]
            [collabo.handlers.utilities.project :refer [current-user-is-owner]]
            [collabo.handlers.utilities.issue :refer [is-closeable-user]]
            [collabo.models.user :refer [get-icon-public-path]]
            [collabo.views.components.project.overview :as vc-overview]
            [collabo.views.components.project.issues :as vc-issues]
            [collabo.views.components.project.setting :as vc-setting]
            [collabo.repositories.project :refer [get-project-coverimage-url]]
            [collabo.repositories.user :refer [find-one-by-account_name]]
            [collabo.views.utilities.html :refer [nl2br]]
            [collabo.views.utilities.request :refer [get-req-url get-baseurl]]
            [collabo.views.utilities.datetime :refer [datetime-format]]
            [buddy.auth :refer [authenticated?]]
            [collabo.views.components.header :refer [header]]))

(defn new-page [{:keys [flash] :as req} current-user]
  (layout
   nil
   (header current-user)
   [:div {:class "columns project-new-page"}
    [:div {:class "column col-6 col-lg-10 col-mx-auto"}
     [:h1 {:class "headline"}"New Project"]
     [:form {:class "new-project-form form-horizontal" :method "POST" :action "/projects/new"}
      (if (:error flash)
        [:div {:class "toast toast-error"} (:error flash)])
      [:div {:class "form-group col-12"}
       [:label {:class "form-label" :for "title"} "Title"]
       [:input {:class "form-input" :type "text" :id "title" :name "project-title" :placeholder "Write your project title"}]
       ]
      [:div {:class "form-group col-12"}
       [:label {:class "form-label" :for "Description"} "Vision"]
       [:textarea {:class "form-input"
                   :rows "10"
                   :id "description"
                   :name "project-description"
                   :placeholder "Write your project vision"}]
       ]
      [:div {:class "btn-container"}
       [:button {:class "btn btn-primary"} "Save"]
       ]
      ]
     ]
    ]
   ))

(defn active-tab [query-params]
  (let [tab (get query-params "tab")
        default "overview"]
    (if tab
      tab
      default)))

(defn menu-tabs [query-params session project]
  [:div {:class "columns"}
   [:ul {:class "tab tab-block colum col-12 col-mx-auto"}
    [:li {:class (str "tab-item" (if (= "overview" (active-tab query-params)) " active"))}
     [:a {:href (str "/projects/" (:id project) "?tab=overview")} "Vision"]
     ]
    [:li {:class (str "tab-item" (if (= "issues" (active-tab query-params)) " active"))}
     [:a {:href (str "/projects/" (:id project) "?tab=issues")} "Issues"]
     ]
    (if (current-user-is-owner session project)
      [:li {:class (str "tab-item" (if (= "setting" (active-tab query-params)) " active"))}
       [:a {:href (str "/projects/" (:id project) "?tab=setting&menu=overview-description")} "Setting"]
       ])
    ]
   ]
  )

(defn detail-page [{:keys [query-params session] :as req} project issues current-user]
  (layout {:title (:title project)
           :description (h-util/escape-html (:description project))
           :url (get-req-url req)
           :image (str (get-baseurl req) "/" (get-project-coverimage-url project))}
          (header current-user)
          [:div {:class "project-page"}
           [:div {:class "columns"}
            [:div {:class "column col-8 col-lg-10 col-mx-auto"}
             [:div {:class "columns project-info"}
              [:div {:class "column col-12"}
               [:h2 {:class "text-bold"}
                [:a {:href (str "/users/" (:account_name project))} (str (:account_name project))] "/" (h-util/escape-html (:title project))]
               ]]]]
           [:div {:class "divider"}]

           (menu-tabs query-params session project)

           (case (get query-params "tab")
             "overview" (case (get query-params "action")
                          (vc-overview/show req project))
             "issues" (case (get query-params "action")
                        "new" (vc-issues/new req project)
                        (vc-issues/show req issues project))
             "setting" (vc-setting/show req project)
             (vc-overview/show req project))
           ]))

(defn comment-component [comment]
  [:div {:class "comment tile"}
   [:div {:class "tile-icon"}
    [:div {:class "comment-user-icon avatar avatar-lg"}
     [:img {:src (get-icon-public-path {:icon (:icon comment)})}]]]
   [:div {:class "tile-content"}
    [:p {:class "tile-title"} (nl2br (h-util/escape-html (:comment comment)))]
    [:p {:class "tile-subtitle text-gray"} (str (:account_name comment)
                                                " created at "
                                                (datetime-format (:created_at comment)))]]
   ])

(defn comments-component [comments]
  (let [comments-cmp (map comment-component comments)
        container [:div {:class "comments"}]]
    (apply conj container comments-cmp)))

(defn issue-detail-page [{:keys [flash query-params session] :as req} project issue comments is-closeable-flg current-user]
  (layout
   {:title (str (:title project) "/" (:title issue))
    :description (:title issue)
    :url (get-req-url req)
    :image (str (get-baseurl req) "/" (get-icon-public-path {:icon (:issue_owner_icon issue)}))}
   (header current-user)
   [:div {:class "project-page"}
    [:div {:class "columns"}
     [:div {:class "column col-8 col-mx-auto"}
      [:div {:class "columns project-info"}
       [:div {:class "column col-12"}
        [:h2 {:class "text-bold"}
         [:a {:href (str "/users/" (:account_name project))} (str (:account_name project))] "/" (h-util/escape-html (:title project))]
        ]]]]
    [:div {:class "divider"}]

    (menu-tabs {"tab" "issues"} session project)

    [:div {:class "issue-detail columns my-2"}
     [:div {:class "column col-8 col-lg-10 col-mx-auto"}
      [:div {:class "title-area my-2"}
       [:h2 {:class "text-left"} (str (:title issue))]

       [:p {:class "text-gray"}
        (if (:closed_at issue )
          [:span {:class "label label-error px-2 mr-2"} "Closed"])
        (str (:account_name issue)
             " created at "
             (datetime-format (:created_at issue)))]]
      [:div {:class "divider"}]
      (comments-component comments)
      [:div {:class "divider"}]

      [:div {:class "comment-form-wrapper"}
       (if (authenticated? (:session req))
         [:form {:id "form-comment" :method "POST" :action (str "/projects/"
                                                                (:id project)
                                                                "/issues/"
                                                                (:id issue)
                                                                "/comment")}
          (if (get-in flash [:error])
            [:div {:class "toast toast-error"} (get-in flash [:error])])
          [:div {:class "form-group"}
           [:label {:class "form-label" :for "comment"} "Comment"]
           [:textarea {:class "form-input" :id "comment" :name "comment" :rows 3}]
           ]
          [:div {:class "comment-action-area text-right"}
           (if is-closeable-flg
             (if (:closed_at issue)
               [:button {:form "form-comment"
                         :formaction (str "/projects/" (:id project) "/issues/" (:id issue) "/open")
                         :formmethod "POST"
                         :class "btn btn-success mx-2" :value "close"} "Open issue"]
               [:button {:form "form-comment"
                         :formaction (str "/projects/" (:id project) "/issues/" (:id issue) "/close")
                         :formmethod "POST"
                         :class "btn mx-2" :value "close"} "Close issue"]))
           [:button {:form "form-comment" :class "btn btn-primary" :value "comment"} "Comment"]
           ]
          ]
         [:div {:class "toast toast-warning"} [:a {:href "/signup"} "Please Sign up"] " to join this conversation on Collabo"]
       )
      ]]]
     ]))
