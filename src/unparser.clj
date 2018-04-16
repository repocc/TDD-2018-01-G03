(ns unparser)

(defn get-past-data [state]
  "returns past data"
  (nth state 3)
  )

(defn get-condition-rule [rule]
  "gets the rule condition - non str"
  (nth (nth rule 1) 1)
  )

(defn get-status [state]
  "Return the status list of the state list."
     (first state))


(defn get-counter-map [status-list]
  (first status-list))

(defmulti get-value-counter (fn [valor counter-args] (type valor)))
(defmethod get-value-counter clojure.lang.PersistentArrayMap [valor counter-args]
  (get valor counter-args))
(defmethod get-value-counter java.lang.Long [valor counter-args] valor)
(defmethod get-value-counter java.lang.Double [valor counter-args] valor)
(defmethod get-value-counter :default [valor counter-args] 0)

(defn get-counters-state [state]
  "returns counter map from state"
  (nth state 0))

(defn get-rules-state [state]
  "returns a collectioon of rules from state"
  (nth state 1))

(defn get-condition [rule]
  "returns condition of a rule previously parsed"
  (str (nth (nth rule 1) 1)))

(defn get-rule-name [rule]
  "get the name of a rule parsed from state"
   (nth rule 0))

(defn get-parameters [rule]
  (nth (nth rule 1) 0))
