(ns powerblog.pages
  (:require [powerpack.markdown :as md]
            [datomic.api :as d])
  (:import (java.time LocalDateTime)
           (java.time.format DateTimeFormatter)))

(defn get-blog-posts [db]
  (->> (d/q '[:find [?e ...]
              :where
              [?e :blog-post/author]]
            db)
       (map #(d/entity db %))
       (sort-by :blog-post/published)
       reverse))

(defn ->ldt [inst]
  (when inst
    (LocalDateTime/ofInstant (.toInstant inst) (java.time.ZoneId/of "Europe/Oslo"))))

(defn ymd [^LocalDateTime ldt]
  (.format ldt (DateTimeFormatter/ofPattern "MMMM d yyy")))

(defn md [^LocalDateTime ldt]
  (.format ldt (DateTimeFormatter/ofPattern "MMMM d")))

(defn layout [{:keys [title]} & content]
  [:html
   [:head
    (when title [:title title])]
   [:body
    content]])

(def header
  [:header [:a {:href "/"} "@einarwh"] [:hr]])

(defn render-frontpage [context page]
  (layout {}
          (md/render-html (:page/body page))
          header
          [:h2 "Blog posts"]
          [:ul
           (for [blog-post (get-blog-posts (:app/db context))]
             [:li
              [:p {:class "blog-post-list-date"} (:blog-post/published blog-post)]
              [:a {:href (:page/uri blog-post)} (:page/title blog-post)]])]))

(defn render-article [context page]
  (layout {}
          header
          [:script "hljs.highlightAll();"]
          (md/render-html (:page/body page))))

(defn render-blog-post [context page]
  (render-article context page))

(defn render-page [context page]
  (case (:page/kind page)
    :page.kind/frontpage (render-frontpage context page)
    :page.kind/blog-post (render-blog-post context page)
    :page.kind/article (render-article context page)))
