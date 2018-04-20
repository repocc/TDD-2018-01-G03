(ns counter-operator
  (:require [unparser :as unparser]
            [expression-evaluator :as exp-evaluator]
            [unparser :as unparser])
  )

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
