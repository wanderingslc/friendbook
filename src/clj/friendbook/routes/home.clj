(ns friendbook.routes.home
  (:require
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [friendbook.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [friendbook.db.core :as db]))

(defn home-page []
  (layout/render
    "home.html" {:messages (db/get-messages)}))

(defn about-page []
  (layout/render "about.html"))

(defn save-message! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-message!
        (assoc params :timestamp (java.util.Date.)))
      (response/found "/"))))

(defn validate-message [params]
  (first
    (b/validate
      params
      :name v/required
      :message [v/required [v/min-count 10]])))

(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/message" request (save-message! request))
  (GET "/about" [] (about-page))
           )

