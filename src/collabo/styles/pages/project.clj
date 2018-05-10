(ns collabo.styles.pages.project)

(def new-page
  [:.project-new-page
   [:.headline {:text-align "center"}]
   [:.new-project-form
    [:.btn-container {:text-align "right"}]]
   ])

(def project-page
  [:.project-page
   [:.overview-show
    [:.description {:line-height "2rem"}
     [:textarea {:line-height "2rem"}]]
    ]

   [:.issues-show
    [:.issues.tile {:padding "10px"}]
    [:.issues.tile:hover {:background-color "#f5f5f5"}]]
   
   ])
