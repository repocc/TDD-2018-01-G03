(ns data-processor
  (:require [parser :as parser]
            [initializer :as initializer]
            [signal-operator :as signal-operator]
            [unparser :as unparser]
            [expression-evaluator :as exp-evaluator]))
(use '[clojure.string :as s])

(defn update
  "'Updates' a value in an associative structure, where k is a
  key and f is a function that will take the old value
  and any supplied args and return the new value, and returns a new
  structure.  If the key does not exist, nil is passed as the old value."
  {:added "1.7"
   :static true}
  ([m k f]
   (assoc m k (f (get m k))))
  ([m k f x]
   (assoc m k (f (get m k) x)))
  ([m k f x y]
   (assoc m k (f (get m k) x y)))
  ([m k f x y z]
   (assoc m k (f (get m k) x y z)))
  ([m k f x y z & more]
   (assoc m k (apply f (get m k) x y z more))))
   
(defn initialize-processor [rules]
  "Returns an array with 4 hashmaps. 1 counters initilized, 2 counter rules, 3 signal rules and the last one is for the 'past -data'"
  [(initializer/initialize-counters rules) (initializer/save-counter-rules rules) (initializer/save-signal-rules rules) {}])

;------------------------------------------------------------------------------------------
;FUNCION QUERY-COUNTER
;------------------------------------------------------------------------------------------
(defn query-counter [state counter-name counter-args]
  ; (get counter-name (nth state 0)) diferenciar si es un num o {}
  ; en caso de ser {}, con counter-args entrar a la llave correspondiente
  ; si no fue inicializada crear la llave y asignarle valor 1

  (let [valor (get (unparser/get-counter-map state) counter-name)]
    (unparser/get-value-counter valor counter-args)))


;------------------------------------------------------------------------------------------
;FUNCIONES PARA EVALUAR EL PAST
;------------------------------------------------------------------------------------------

(defn add-value-map-data [data map-data]
  (def new-map-data map-data)
    (if-not (exp-evaluator/includes (get map-data (first data)) (last data))
      (def new-map-data (assoc map-data (first data) (conj (get map-data (first data)) (last data) ))))
    new-map-data)

(defn add-data-map-data [data map-data]
  (assoc  map-data (first data) (conj () (last data))))

(defn update-map-data [data map-data]
  "Check if the map-data contains the data key. If si, check if map-data contains
  the data value of the data key- If not, the data is added to the data-map correctly.
  Return the map-data update
  Format map-data: {key [value1 value2..] ..}
  Format data: (key value)"
    (def new-map-data map-data)
    (if (contains? map-data (first data))
      (def new-map-data (add-value-map-data data map-data)))
    (if-not (contains? map-data (first data))
      (def new-map-data (add-data-map-data data map-data)))
    new-map-data
  )


(defn make-key-data [state data parameters]
  "returns values from the data hashmap"
  (def key-data [])
  (if-not (empty? parameters)
  (doseq [parameter parameters]
    (def key-data (conj key-data (exp-evaluator/evaluate-expression state data parameter 0)))))
  key-data)

(defn assoc-if-new [coll k v]
  "associate key-value tuple if not exists in the current collection"
  (merge {k v} coll))


(defmulti evaluate-inc-expression (fn [inc-expression data state] (type inc-expression)))
(defmethod evaluate-inc-expression java.lang.Long [inc-expression data state] inc-expression)
(defmethod evaluate-inc-expression :default [inc-expression data state]
(exp-evaluator/evaluate-expression state data inc-expression 0))

(defn get-inc-expression [rule]
  (nth (nth rule 1) 2))

(defn get-increment [rule data state]

  (def inc-expression (get-inc-expression rule))
  (evaluate-inc-expression inc-expression data state))

(defn inc-counter [state rule data counters]

  (let [key-counter (unparser/get-rule-name rule)
        parameters (unparser/get-parameters rule)
        data-key (make-key-data state data parameters)
        coll-key (get counters key-counter)
        incre (get-increment rule data state)]

  (if-not (empty? parameters)
    (update-in
    (assoc-in counters [key-counter]
      (assoc-if-new coll-key data-key 0))
        [key-counter data-key] + incre)
  (update counters key-counter + incre))
  ))


(defn evaluate-counters-rules [state new-data]
  (def counters (unparser/get-counters-state state))
  (doseq [rule (unparser/get-rules-state state)]
    (if (exp-evaluator/evaluate-condition state new-data (unparser/get-condition-rule rule))
      (def counters (inc-counter state rule new-data counters))))
  counters)


;------------------------------------------------------------------------------------------
;FUNCION PROCESS DATA
;------------------------------------------------------------------------------------------

(defn process-data [old-state new-data]
  "Returns new state after evaluate every rule"
  (def new-map-data (unparser/get-past-data old-state))
  (doseq [data new-data]
    (def new-map-data (update-map-data (map identity data) new-map-data)))

  (def sg (signal-operator/update-signal old-state new-data))
  (def new-counter-state (evaluate-counters-rules old-state new-data))
  (vector (vector new-counter-state (unparser/get-rules-state old-state) (unparser/get-signal-rules old-state) new-map-data) sg))
