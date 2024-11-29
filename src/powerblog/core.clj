(ns powerblog.core
  (:require [powerblog.ingest :as ingest]
            [powerblog.pages :as pages]))

(ns powerblog.core
  (:require [powerblog.ingest :as ingest]
            [powerblog.pages :as pages]
            [powerpack.highlight :as highlight]))

(def config
  (-> {:site/title "@einarwh"
       :powerpack/render-page #'pages/render-page
       :powerpack/create-ingest-tx #'ingest/create-tx

       :optimus/bundles {"app.css"
                         {:public-dir "public"
                          :paths ["/css/styles.css"]}}

       :optimus/assets [{:public-dir "public"
                         :paths [#".*\.jpg" #".*\.png"]}]

       :imagine/config {:prefix "image-assets"
                        :resource-path "public"
                        :disk-cache? true
                        :transformations
                        {:preview-small
                         {:transformations [[:fit {:width 184 :height 184}]
                                            [:crop {:preset :square}]]
                          :retina-optimized? true
                          :retina-quality 0.4
                          :width 184}}}}
      highlight/install))


