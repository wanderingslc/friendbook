(ns friendbook.test.db.core
  (:require [friendbook.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [friendbook.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'friendbook.config/env
      #'friendbook.db.core/*db*)
    (migrations/migrate ["migrate"] (env :database-url))
    (f)))

(deftest test-messages
  (jdbc/with-db-transaction [t-conn *db*]
                            (jdbc/db-set-rollback-only! t-conn)
                            (let [timestamp (java.util.Date.)]
                              (is (= 1 (db/save-message!
                                         t-conn
                                         {:name "Bob"
                                          :message "Hello World!"
                                          :timestamp timestamp}
                                         {:connection t-conn})))
                              (is (=
                                    {:name "Bob"
                                     :message "Hello World!"
                                     :timestamp timestamp}
                                    (-> (db/get-messages t-conn {})
                                        (first)
                                        (select-keys [:name :message :timestamp])))))))
