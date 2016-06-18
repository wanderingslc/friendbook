(ns user
  (:require [mount.core :as mount]
            friendbook.core))

(defn start []
  (mount/start-without #'friendbook.core/repl-server))

(defn stop []
  (mount/stop-except #'friendbook.core/repl-server))

(defn restart []
  (stop)
  (start))


