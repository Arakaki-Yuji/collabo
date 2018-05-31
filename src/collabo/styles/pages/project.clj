(ns collabo.styles.pages.project)

(def new-page
  [:.project-new-page
   [:.headline {:text-align "center"}]
   [:.new-project-form
    [:.btn-container {:text-align "right"}]]
   ])

(def project-page
  [:.project-page {:margin "30px 0"}
   [:.overview-show
    [:.cover-image {:height "20rem" :margin "1rem 0"}
     [:img {:width "100%" :height "100%" :object-fit "cover"}]
     ]
    [:.description {:line-height "2rem"}
     [:textarea {:line-height "2rem"}]]
    ]

   [:.issues-show
    [:.issues.tile {:padding "10px"}]
    [:.issues.tile:hover {:background-color "#f5f5f5"}]]

   [:.issue-detail
    [:.comments {:margin "1rem 0"}
     [:.comment {:padding "1rem 0"}]
     [:.comment:hover {:background-color "#f5f5f5"}]]]
   ])
