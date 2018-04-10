(ns data-processor
  (:require [parser :as parser]))

(defmulti initialize-counter (fn [params] (count params)))
(defmethod initialize-counter 0 [params] 0)
(defmethod initialize-counter 1 [params] 0)
(defmethod initialize-counter :default [params] {})


;------------------------------------------------------------------------------------------
;FUNCIONES DE OPERADORES
;------------------------------------------------------------------------------------------
(defn includes [coll value]
  (let [s (seq coll)]
    (if s (if (= (first s) value) true (recur (rest s) value)) false)))


(defn starts-with [coll value]
  (= (first coll) value))

(defn ends-with [coll value]
  (= (last coll) value))

(defn new-or [value1 value2]
  (or value1 value2))

(defn new-and [value1 value2]
  (and value1 value2))

(defn new-not [value1 value2]
  (not value1 value2))


(def operators {"=" = "+" + "-" - "*" * "/" / "mod" mod "<" < ">" > "<=" <= ">=" >= "concat" str "!=" distinct? "includes?" includes "starts-with?" starts-with "ends-with?" ends-with "or" new-or "and" new-and "not" new-not})

(defn get-operator [operator]
  ;Toma el operador pasado como parametro y llama a la funcion correspondiente
 (get operators operator))

 (defn apply-operador [opr par1 par2]
   ;Aplica la funcion correspondiente del opr a los parametros par1 y par2.
 (apply (get-operator opr) [par1 par2]))

;------------------------------------------------------------------------------------------


(defn counter-name-and-initial-value [rule]
    "Returns an array with counter name and initial value of the counter"
    (identity [  (parser/parse-rule-name rule) (initialize-counter (parser/parse-counter-params rule))]))

(defn name-and-counter-rule [rule]
    "Returns an array with counter name and with an array with rule elements"
    (identity [ (parser/parse-rule-name rule)
              [(parser/parse-counter-params rule) (parser/parse-rule-condition rule)]]))

(defn name-and-signal-rule [rule]
    "Returns an array with signal name and with an array with rules elements"
    (identity [ (parser/parse-rule-name rule) [(parser/parse-signal-result rule) (parser/parse-rule-condition rule)]]))

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
  "Returns an array with 4 hashmaps. 1 counters initilized, 2 counter rules, 3 signal rules and the last one is for the 'past -data'"
  [(initialize-counters rules) (save-counter-rules rules) (save-signal-rules rules) {}])

(defn get-status [state]
  "Return the status list of the state list."
     (first state))

(defn get-signals [state]
  "Return the signal map of the state list."
  (nth state 2))

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
(defn get-parameters [rule]
  (nth (nth rule 1) 0)
  )

  (defn contains-data-in-map-data [data map-data]
    (def is-data false)
    (if (contains? map-data (first data))
      (if (includes (get map-data (first data)) (last data))
        (def is-data true)))
    is-data
    )

(defmulti evaluate-parameters (fn [state data parameter] (str ( nth parameter 0))))
(defmethod evaluate-parameters "past" [state data parameter]
 (if (contains-data-in-map-data  (conj () (get data (nth parameter 1)) (nth parameter 1)) (nth state 3))
 (def value (get data (nth parameter 1)))
 (def value "NOEXISTEP")
 )
value
 )
(defmethod evaluate-parameters "current" [state data parameter]
    (def value-condition (get data (nth parameter 1)))
     (if-not (contains? data (nth parameter 1) )
       (def value-condition "NOEXISTEC"))
    value-condition)
(defmethod evaluate-parameters :default [state data parameter] 0)


(defn make-key-data [state data parameters]
  "returns values from the data hashmap"
  (def key-data [])
    (if-not (empty? parameters)

      (doseq [parameter parameters]
        (def key-data (conj key-data (evaluate-parameters state data parameter)))
      )
    )
   key-data
)

(defn assoc-if-new [coll k v]
"associate key-value tuple if not exists in the current collection"
  (merge {k v} coll)
)

(defn inc-counter [state rule data counters]

 (def key-counter (get-rule-name rule))
 (def parameters (get-parameters rule))
 (def data-key (make-key-data state data parameters))
 (def coll-key (get counters key-counter))


 (if-not (empty? parameters)
  (update-in
    (assoc-in counters [key-counter]
      (assoc-if-new coll-key data-key 0))
        [key-counter data-key] inc)
  (update counters key-counter inc)
  )

)

(defn query-counter [state counter-name counter-args]
  ; (get counter-name (nth state 0)) diferenciar si es un num o {}
  ; en caso de ser {}, con counter-args entrar a la llave correspondiente
  ; si no fue inicializada crear la llave y asignarle valor 1
  (let [valor (get (get-counter-map state) counter-name)]
    (get-value-counter valor  counter-args)
  ))


;------------------------------------------------------------------------------------------
;FUNCIONES PARA EVALUAR EL PAST
;------------------------------------------------------------------------------------------


(defn add-value-map-data [data map-data]
  (def new-map-data map-data)
    (if-not (includes (get map-data (first data)) (last data))
      (def new-map-data (assoc map-data (first data) (conj (get map-data (first data)) (last data) ))))
    new-map-data

)

(defn add-data-map-data [data map-data]
  (assoc  map-data (first data) (conj () (last data)))
  )

(defn update-map-data [data map-data]
  ;Check if the map-data contains the data key. If si, check if map-data contains
  ;the data value of the data key- If not, the data is added to the data-map correctly.
  ;Return the map-data update
  ;Format map-data: {key [value1 value2..] ..}
  ;Format data: (key value)
    (def new-map-data map-data)
    (if (contains? map-data (first data))
      (def new-map-data (add-value-map-data data map-data)))
    (if-not (contains? map-data (first data))
      (def new-map-data (add-data-map-data data map-data)))
    new-map-data
  )

;------------------------------------------------------------------------------------------
;FUNCIONES PARA EVALUAR LAS EXPRESIONES
;------------------------------------------------------------------------------------------

;Evalua la expresion dentro del parentesis.
(defmulti evaluate-expression (fn [state data expre] (str(nth expre 0))))
(defmethod evaluate-expression "past" [state data expre]
   (contains-data-in-map-data  (conj () (get data (nth expre 1)) (nth expre 1)) (nth state 3)))
(defmethod evaluate-expression "current" [state data expre]
         (get data (nth expre 1)))
(defmethod evaluate-expression "counter-value" [state data expre]
         (query-counter state (nth expre 1) (nth expre 2)))

(defmethod evaluate-expression :default [state data condi]
         ;cuando es mas de 2 parametros
         (apply-operador (str(nth condi 0)) (evaluate-expression state data (nth condi 1)) (evaluate-expression state data (nth condi 2)))

)

;Distingue las condiciones que tienen 1 parametro a evaluar de las que tienen 2.
(defmulti evaluate-conditions (fn [state data condi] (str(nth condi 0))))
(defmethod evaluate-conditions "past" [state data condi]
   (contains-data-in-map-data  (conj () (get data (nth condi 1)) (nth condi 1)) (nth state 3)))
(defmethod evaluate-conditions "current" [state data condi]
         (def value-condition (get data (nth condi 1)))
          (if-not (contains? data (nth condi 1) )
            (def value-condition false))
         value-condition)


(defmethod evaluate-conditions :default [state data condi]
         ;cuando es mas de 2 parametros
         (apply-operador (str(nth condi 0)) (evaluate-conditions state data (nth condi 1)) (evaluate-conditions state data (nth condi 2)))

)

;Distingue las condiciones booleanas a las que son funciones a determinar su verdad.
(defmulti conditions (fn [state data condi] condi))
(defmethod conditions true [state data condi] condi)
(defmethod conditions false [state data condi] condi)
(defmethod conditions :default [state data condi]

          (evaluate-conditions state data condi))


(defn evaluate-conditions-from-rule [state data rule]
 (conditions state data (nth rule 3))

 )
(defn evaluate-condition  [state data condition]
  (conditions state data condition))
(defn evaluate-counters-rules [state new-data]

  (def counters (get-counters-state state))
    (doseq [rule (get-rules-state state)]
      (if (evaluate-condition state new-data (nth (nth rule 1) 1))
        (def counters (inc-counter state rule new-data counters))
      )
    )
  counters)




(defn get-signal-rules
  "Return the signal rules map from the state list."
  [state]
  (nth state 2))

(defn evaluate-signal-condition
  "Returns the result of the evaluation of signal condition"
  [signal-condition state]
  (evaluate-condition state [] signal-condition)
)

(defn calculate-signal-result
  [signal-result state]
  ;Ej1: signal-result = (/ (counter-value "spam-count" []) (counter-value "email-count" []))
  ;Ej2: signal-result = (current "value")
  0
)

(defn name-and-signal-evaluation
  [state signal-rule]
  "Returns an array with name of the signal to eval
  and the result of signal rule evaluation if there is
  not possible result return nil"

  (def signal-name (nth signal-rule 0))
  (def signal-result (nth (nth signal-rule 1) 0))
  (def signal-condition (nth (nth signal-rule 1) 1))

  (if (evaluate-signal-condition signal-condition state)
    {signal-name (calculate-signal-result signal-result state)}
  )
)

(defn update-signal
  [state]
  "Returns signal evaluation"
  (list (into {} (map name-and-signal-evaluation (repeat (get-counter-map state)) (seq (get-signal-rules state))))))

(defn process-data
  "Returns new state after evaluate every rule"
  [old-state new-data]

  (def map-data (nth old-state 3))
  (doseq [data new-data]
    (def map-data (update-map-data (map identity data) map-data)))

  ;(def sg (update-signal old-state))
  (def sg [])
  (def new-counter-state (evaluate-counters-rules old-state new-data))

  (vector (vector new-counter-state (nth old-state 1) (nth old-state 2) map-data) sg))
