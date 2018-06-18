(ns collabo.styles.pages.user)

(def user-page
  [:.user-page {:margin "30px 0"}
   [:.project-tiles-container {:margin "1em 0"}
    [:.project.tile {:padding "0.5rem"}]
    [:.project.tile:hover {:background-color "#f5f5f5"}]
    [:.icon {:height "5em" :width "auto"}]
    ]
   ])
