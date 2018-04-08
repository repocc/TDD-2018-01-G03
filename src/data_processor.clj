(ns data-processor
  (:require [parser :as parser]))

(defmulti initialize-counter (fn [params] (count params)))
(defmethod initialize-counter 0 [params] 0)
(defmethod initialize-counter 1 [params] 0)
(defmethod initialize-counter :default [params] {})

(defn counter-name-and-initial-value[rule]
    "Returns an array with counter name and initial value of the counter"
    (identity [  (parser/parse-rule-name rule) (initialize-counter (parser/parse-counter-params rule))]))

(defn name-and-counter-rule [rule]
    "Returns an array with counter name and with an array with rule elements"
    (identity [ (parser/parse-rule-name rule)
              [(parser/parse-counter-params rule) (parser/parse-rule-condition rule)]]))

(defn name-and-signal-rule [rule]
    "Returns an array with signal name and with an array with rules elements"
    (identity [ (parser/parse-rule-name rule) [(parser/parse-signal-operation rule) (parser/parse-rule-condition rule)]]))

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

  (first status-list)
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
  (def key-counter (get-rule-name rule))
   ;add segun parametros
   (update counters key-counter inc)
   ;(counters)
)

(defn get-counter-state [state]
  "returns counter-map from state"
  (nth state 0)
)

(defn get-signal-rules
  "Return the signal rules map from the state list."
  [state]
  (last state))

(defn evaluate-signal-condition
  "Returns the result of the evaluation of signal condition"
  [counters-state signal-rule]

)

(defn signal-result
  [counters-state signal-rule]
)

(defn name-and-signal-evaluation
  "Returns an array with name of the signal to eval
  and the result of signal rule evaluation if there is
  not possible result return nil"
  [counters-state signal-rule]
  (if (evaluate-signal-condition counters-state signal-rule)
    (signal-result counters-state signal-rule)
  )
)

(defn update-signal
  "Returns signal evaluation"
  [state]
  (list (into {} (map name-and-signal-evaluation (repeat (get-counter-map state)) (get-signal-rules state)))))

(defn evaluate-counters-rules [state new-data]

  (def counters (get-counter-state state))
    (doseq [rule (get-rules-state state)]
      (if (applyRule new-data rule)
        (def counters (inc-counter rule counters ))
    ;  (prn "false, es decir, no cumplio ninguna regla")
      )
    )
  counters
  )

(defn process-data
  "Returns new state after evaluate every rule"
  [old-state new-data]
  (def sg (update-signal old-state))
  (def new-counter-state (evaluate-counters-rules old-state new-data))
  (vector (vector new-counter-state (nth old-state 1) (nth old-state 2) ) sg)

  )


(defn query-counter [state counter-name counter-args]
  ; (get counter-name (nth state 0)) diferenciar si es un num o {}
  ; en caso de ser {}, con counter-args entrar a la llave correspondiente
  ; si no fue inicializada crear la llave y asignarle valor 1

  (let [valor (get (get-counter-map state) counter-name)]
    (get-value-counter valor  counter-args)
  ))
;Faltan operators que me tiran error, por lo cual habria que hacer una funcion para cada una.
(def operators {"=" = "+" + "-" - "*" * "/" / "mod" mod "<" < ">" > "<=" <= ">=" >= "concat" str})  

(defn get-operator [operator]   
  ;Toma el operador pasado como parametro y llama a la funcion correspondiente
 (get operators operator))

(defn apply-operador [opr par1 par2]
  ;Aplica la funcion correspondiente del opr a los parametros par1 y par2.
(apply (get-operator opr) [par1 par2]))



;Distingue las condiciones que tienen 1 parametro a evaluar de las que tienen 2.
(defmulti evaluate-conditions (fn [state data rules] (str(nth rules 0))))
(defmethod evaluate-conditions "past" [state data rules] true)
(defmethod evaluate-conditions "current" [state data rules]
         (= (nth rules 1) (first(keys data))))
(defmethod evaluate-conditions :default [state data rules]
         ;cuando es mas de 2 parametros
         (apply-operador (str(nth rules 0)) (evaluate-conditions state data (nth rules 1)) (evaluate-conditions state data (nth rules 2)))
         
)

;Distingue las condiciones booleanas a las que son funciones a determinar su verdad.
(defmulti conditions (fn [state data rules] rules))
(defmethod conditions true [state data rules] rules)
(defmethod conditions false [state data rules] rules)
(defmethod conditions :default [state data rules]

          (evaluate-conditions state data rules))


(defn evaluate-conditions-from-rule [state data rule]
 (conditions state data (nth rule 3))
 
 )
(defn evaluate-condition
  [state data condition]
  (condition state data condition)
)
