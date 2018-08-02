(ns collabo.styles.pages.home
  (:require [garden.stylesheet :refer [at-media]]
            [garden.units :as u]))

(def home-page
  [:.home-page
   {:background-image "url('/images/top-image.jpg')"
    :background-size "cover"
    :height "35em"}
   [:.overlay {:height "100%" :background-color "rgba(0,0,0,.62)"}]
   [:.headline-container {:text-align "center" :margin "5% auto" :color "#fff"}
    [:.headline {:margin "2rem"}]
    [:.sub-headline {:margin "1rem"}]
    (at-media {:screen true :min-width (u/px 960)}
              [:.headline {:font-size "3rem" :margin "2rem"}]
              [:.sub-headline {:margin "1rem" :font-size "1rem"}])
    
    ]

   [:.hot-projects
    :.headline {:margin "3rem 2rem 2rem 2rem"}
    [:.project {:margin "1rem 0"}
     [:.card-image {:margin "auto" :padding "2rem"}
      [:img {:border-radius "50%" :width "6rem" :height "6rem"}]]]]
   
   [:.hot-users
    :.headline {:margin "3rem 2rem 2rem 2rem"}
    
    [:.user {:margin "1rem 0"}
     [:.card-image {:margin "auto" :padding "2rem"}
      [:img {:border-radius "50%" :width "6rem" :height "6rem"}]]]
    ]
   ])
