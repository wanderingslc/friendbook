(ns friendbook.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [friendbook.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[friendbook started successfully using the development profile]=-"))
   :middleware wrap-dev})
