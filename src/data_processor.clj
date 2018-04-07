(ns data-processor
  (:require [parser :as parser]))

(defmulti initialize-counter (fn [params] (count params)))
(defmethod initialize-counter 0 [params] 0)
(defmethod initialize-counter 1 [params] 0)
(defmethod initialize-counter :default [params] {})

(defn counter-name-and-initial-value[rule]
    "Returns an array with counter name and initial value of the counter"
    (identity [ (keyword (parser/parse-rule-name rule)) (initialize-counter (parser/parse-counter-params rule))]))

(defn name-and-counter-rule [rule]
    "Returns an array with counter name and with an array with rule elements"
    (identity [(keyword (parser/parse-rule-name rule))
              [(parser/parse-counter-params rule) (parser/parse-rule-condition rule)]]))

(defn name-and-signal-rule [rule]
    "Returns an array with signal name and with an array with rules elements"
    (identity [(keyword (parser/parse-rule-name rule)) [(parser/parse-signal-operation rule) (parser/parse-rule-condition rule)]]))

(defn initialize-counters [rules]
  "Returns a hashmap where every key is a counter name and as value the initial counter value"
     (into {} (map counter-name-and-initial-value (filter parser/is-rule-a-counter rules))))

(defn save-counter-rules [rules]
  "Returns a hashmap where every key is the rule name and as value
  a list with params and condition"
     (into {} (map name-and-counter-rule (filter parser/is-rule-a-counter rules))))

(defn save-signal-rules [rules]
  "Returns a hashmap where every key is the rule name and as value
  a list with operation and condition"
     (into {} (map name-and-signal-rule (filter parser/is-rule-a-signal rules))))

(defn initialize-processor [rules]
  "Returns an array with 3 hashmaps. 1 counters initilized, 2 counter rules
  and 3 signal rules"
  [(initialize-counters rules) (save-counter-rules rules) (save-signal-rules rules)])

(defn get-status [state]
  "Return the status list of the state list."
    (first state))

(defn get-signals [state]
  "Return the signal map of the state list."
  (last state))

(defn get-counter-map [status-list]

  (first (get-status status-list))
  )



(defmulti get-value-counter (fn [valor counter-args] (type valor)))
(defmethod get-value-counter clojure.lang.PersistentArrayMap [valor counter-args]
  (get valor counter-args))
(defmethod get-value-counter java.lang.Long [valor counter-args] valor)
(defmethod get-value-counter :default [valor counter-args] 0)



(defn get-counters-state [state]
  "returns counter map from state"
  (nth state 0)
  )

(defn get-rules-state [state]
  "returns a collectioon of rules from state"
  (nth state 1)
  )

(defn get-condition [rule]
  "returns condition of a rule previously parsed"
  (str (nth (nth rule 1) 1))
)
(defn get-rule-name [rule]
  "get the name of a rule parsed from state"
  (nth rule 0)
  )

(defn applyRule [data rule]
  "return true if the data fullfile the condition of the rule"

  (if (= "true" (get-condition rule))
  true ;sumar contador
  ;(prn "evaluar condicion pasandole data") ; evaluar condicion no true
  )
)


(defn inc-counter [rule counters]
 ;(prn rule)
 (def keyCounter (get-rule-name rule))

 ; (update counters keyCounter inc)
 ;add segun parametros
 (update counters keyCounter inc)
 ;(counters)
)

(defn get-counter-state [state]
  "returns counter-map from state"
  (nth state 0)
)


(defn process-data [state new-data]
  ;todo :
  ;defn apply rule signals - > has to return a cole

(def counters (get-counter-state state))

(doseq [rule (get-rules-state state)]
    (prn rule)
    (if (applyRule new-data rule)
      (def counters (inc-counter rule counters ))
        (prn counters)
  ;  (prn "false, es decir, no cumplio ninguna regla")
    )
  )

  (def signal [])

 [state signal]
)
;
;
;
;
;
;
;
;
;
;
;
;
;

(defn query-counter [state counter-name counter-args]
  ; (get counter-name (nth state 0)) diferenciar si es un num o {}
  ; en caso de ser {}, con counter-args entrar a la llave correspondiente
  ; si no fue inicializada crear la llave y asignarle valor 1
  (let [valor (get (get-counter-map state) counter-name)]
    (get-value-counter valor counter-args)
  ))
