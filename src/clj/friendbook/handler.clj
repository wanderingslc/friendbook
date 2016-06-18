(ns friendbook.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [friendbook.layout :refer [error-page]]
            [friendbook.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [friendbook.middleware :as middleware]))

(def app-routes
  (routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
