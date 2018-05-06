(ns rest.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
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
(defn db-find-all-rules [] (rdb/keyspace db :rules))

;; Stores a new car
(defn db-store-rule [rule] (rdb/insert db :rules rule))

;; Store an initial set of cars to start with
(rdb/insert db :rules
  {:nombre "email-counter"
   :parametro  ""
   :condicion "true"})

(rdb/insert db :rules
  {:nombre "spam-count"
   :parametro  "Honda"
   :condicion "current 'spam'"})

(defn get-all-formated-rules [x]
  (def rules '((define-counter "email-count" []
                 true)
               (define-counter "spam-count" []
                 (current "spam"))
               (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])
                                                  (counter-value "email-count" []))}
                 true)
               (define-counter "spam-important-table" [(current "spam")
                                                       (current "important")]
                 true)))
  )

(defn init-rules []
   ; (initialize-counters
   ;        (get-all-formated-rules (db-find-all-rules)
   ;        ))
  )



(defroutes app-routes
  (GET "/example/api/rules" []
    {:headers {"Access-Control-Allow-Origin" "*"
    "Access-Control-Allow-Methods" "GET, POST, PUT, OPTIONS"}
    :status 200 :body (db-find-all-rules)}
  )

  (GET "/example/api/start" []
    {:headers {"Access-Control-Allow-Origin" "*"
    "Access-Control-Allow-Methods" "GET, POST, PUT, OPTIONS"}
    :status 200 :body (init-rules)}
  )

  (POST "/example/api/rules" request
    (let [nombre (get-in request [:params :nombre])
          parametro (get-in request [:params :parametro])
          condicion (get-in request [:params :condicion])
          rule {:nombre nombre :parametro parametro :condicion condicion}]
      (db-store-rule rule)
      {:status 201 :body rule}
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
