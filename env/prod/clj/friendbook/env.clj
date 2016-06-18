(ns friendbook.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[friendbook started successfully]=-"))
   :middleware identity})
