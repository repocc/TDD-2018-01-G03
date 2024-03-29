(ns rest.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
            [data-processor :refer :all]
            [core-operator :refer :all]
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

(defn db-find-last-signal []
  (get (last (rdb/keyspace db :signal)) :nombre)
)

(defn db-find-last-rules [] (rdb/keyspace db :rules))

(defn db-store-rule [rule] (rdb/insert db :rules rule))

(defn save-signal [signal]
 (rdb/insert db :signal
   {:nombre signal}))

(defn save-state [state]
   (rdb/insert db :state
     {:nombre state})
)

(defn save-rule [rule]
  (prn rule)
 (rdb/insert db :rules
   rule
   ))

(defn process-ticket [ ticket ]
  (def rta (process-data (db-find-last-state) ticket))
  (save-state (first rta))
  (save-signal (last rta))
)

(def rules '((define-counter "modification-count" []
               true)

             (define-counter "open-count" []
                 (current "OPEN"))
             (define-counter "todo-count" []
                 (current "TODO"))
             (define-counter "in-progress-count" []
                 (current "IN+PROGRESS"))
             (define-counter "done-count" []
                 (current "DONE"))
             (define-counter "close-count" []
                 (current "CLOSE"))
             (define-signal {"open-actual-count" (- (counter-value "open-count" [])
                                                (counter-value "close-count" []))}
               true)
             (define-signal {"open-fraction" (/ (- (counter-value "open-count" []) (counter-value "close-count" []))
                                                (counter-value "open-count" []))}
               true)
             ))


(defn processor-initialization []
  (prn db-find-last-rules)

  ; TODO: Cambiar, rules debe venir de las reglas que se guardaron en la base de datos
  (save-state (initialize-processor rules))
  ; (save-state (initialize-processor (rules-maker db-find-last-rules))))

  ; (rdb/drop-where db :rules :type "define-signal")
  ; (rdb/drop-where db :rules :type "define-counter")

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

  (GET "/example/api/getLastSignal" []
    {:status 200 :body (db-find-last-signal)}
  )

  (GET "/example/api/processor-initialization" []
    (processor-initialization)
    {:status 200 :body "processor initialized"}
  )

  (GET "/example/api/calculateLastSignal" []
    "Esto es porque el signal se calcula con el estado anterior,
    no con el estado resultante tras la llamada al data-processor,
    tras procesar todos los datos, se guarda el estado final y se
    procesa un dato inutil para poder guardar el signal final"
    (def rta (process-data (db-find-last-state) {:inutil ""}))
    (save-signal (last rta))
    {:status 200 :body (last rta)}
  )

  (POST "/example/api/counterValue" request
    (let [counter-name (get-in request [:params :counter-name])
        counter-value {:value (query-counter (db-find-last-state) counter-name [] )}]
        {:status 201 :body (str (counter-value :value))}
    )
  )

  (POST "/example/api/ruleValue" request
    (let [rule-name (get-in request [:params :rule-name])
        rule-value {:value (rule-value (db-find-last-state) (db-find-last-signal) rule-name  )}]
        {:status 201 :body (str (rule-value :value))}
    )
  )

  (POST "/example/api/setRule" request
    (let [name (get-in request [:params :name])
          params (get-in request [:params :params])
          condition (get-in request [:params :condition])
          type (get-in request [:params :type])
          rule {:type type :name name :condition condition :params params}]
          (save-rule rule)
      {:status 201 :body rule}
    )

  )

  (POST "/example/api/processTicket" request

    (let [ticket (get-in request [:json-params])]
      (process-ticket ticket)
      {:status 201 :body (str "Ticket procesado Ok \nTicket -> " ticket)}

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
