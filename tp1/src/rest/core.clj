(ns rest.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
            [data-processor :refer :all]
            ))

;; Utility function to parse an integer
(defn parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*$" s)
    (read-string s)))

;; Creates a new in memory database
(def db (rdb/create "example"))

;; Finds all the cars from the database

(defn db-find-last-state []
  (get (last (rdb/keyspace db :state)) :nombre)
)

(defn db-find-last-rules [] (rdb/keyspace db :rules))

(defn db-store-rule [rule] (rdb/insert db :rules rule))

(def rules '((define-counter "email-counts" []
               true)
             (define-counter "spam-count" []
               (current "spam"))
             (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])
                                                (counter-value "email-count" []))}
               true)
             (define-counter "spam-important-table" [(current "spam")
                                                     (current "important")]
               true)))


(defn save-state [state]
   (rdb/insert db :state
     {:nombre state})
)

(defn process-ticket [ ticket ]

  (rdb/insert db :state
    {:nombre (first (process-data (db-find-last-state) {"spam" true}))})

)

(defn initialize-counters []
  (save-state (initialize-processor rules))
)

(defn query-counterss []
(prn "aca devuelvo el query-counter")
)

(defroutes app-routes
  (GET "/example/api/getRules" []
    {:headers {"Access-Control-Allow-Origin" "*"
    "Access-Control-Allow-Methods" "GET, POST, PUT, OPTIONS"}
    :status 200 :body (db-find-last-rules)}
  )

  (GET "/example/api/getState" []
    {:status 200 :body (db-find-last-state)}
  )

  (GET "/example/api/initialize-counters" []

    (initialize-counters)
    {:status 200 :body "todo bien"}
  )


  (GET "/example/api/GetQueryCounter" []
    {:status 200 :body (query-counterss)}
  )

  (POST "/example/api/setRule" request
    (let [rule (get-in request [:json-params])]
      (prn "Nueva regla")
      (prn rule)
      (db-store-rule rule)
      {:status 201 :body rule}
    )
  )

  (POST "/example/api/processTicket" request

    (let [ticket (get-in request [:json-params])]
            (prn ticket)
      (process-ticket ticket)
      {:status 201 :body "proceso Ok"}
    )
  )

  (route/resources "/")

  (route/not-found "Not encontrado"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)
  )
)

(defn -main [& args]
  (jetty/run-jetty app)
)
