(ns data-processor
  (:require [initializer :as initializer]
            [signal-operator :as signal-operator]
            [unparser :as unparser]
            [counter-operator :as counter-operator]))
(use '[clojure.string :as s])


(defn initialize-processor [rules]
  "Returns an array with 4 hashmaps. 1 counters initilized, 2 counter rules, 3 signal rules and the last one is for the 'past -data'"
  [(initializer/initialize-counters rules) (initializer/save-counter-rules rules) (initializer/save-signal-rules rules) {}])

(defn query-counter [state counter-name counter-args]
  ; (get counter-name (nth state 0)) diferenciar si es un num o {}
  ; en caso de ser {}, con counter-args entrar a la llave correspondiente
  ; si no fue inicializada crear la llave y asignarle valor 1

  (let [valor (get (unparser/get-counter-map state) counter-name)]
    (unparser/get-value-counter valor counter-args)))

(defn process-data [old-state new-data]
  "Returns new state after evaluate every rule"
  (def new-map-data (unparser/get-past-data old-state))
  (doseq [data new-data]
    (def new-map-data (counter-operator/update-map-data (map identity data) new-map-data)))

  (def sg (signal-operator/update-signal old-state new-data))
  (def new-counter-state (counter-operator/evaluate-counters-rules old-state new-data))
  (vector (vector new-counter-state (unparser/get-rules-state old-state) (unparser/get-signal-rules old-state) new-map-data) sg))
