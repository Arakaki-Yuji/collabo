(ns collabo.styles.pages.home)

(def home-page
  [:.home-page
   {:background-image "url('/images/top-image.jpg')"
    :background-size "cover"
    :height "35em"}
   [:.overlay {:height "100%" :background-color "rgba(0,0,0,.62)"}]
   [:.headline-container {:text-align "center" :margin "5% auto" :color "#fff"}
    [:.headline {:font-size "3rem" :margin "2rem"}]
    [:.sub-headline {:margin "1rem"}]
    ]
   ])
