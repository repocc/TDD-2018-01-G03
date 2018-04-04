(ns data-processor
  (:require [parser :as parser]))

(defmulti initialize-counter (fn [params] (count params)))
(defmethod initialize-counter 0 [params] 0)
(defmethod initialize-counter 1 [params] 0)
(defmethod initialize-counter :default [params] {})

(defn counter-name-and-initial-value[rule]
    "Returns an array with counter name and initial value of the counter"
    (identity [( parser/parse-rule-name rule) (initialize-counter (parser/parse-counter-params rule))]))

(defn name-and-counter-rule [rule]
    "Returns an array with counter name and with an array with rule elements"
    (identity [(parser/parse-rule-name rule)
              [(parser/parse-counter-params rule) (parser/parse-rule-condition rule)]]))

(defn name-and-signal-rule [rule]
    "Returns an array with signal name and with an array with rules elements"
    (identity [(parser/parse-rule-name rule) [(parser/parse-signal-operation rule) (parser/parse-rule-condition rule)]]))

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


;   ;datos que definen state
;   (def condition '(current "spam"))
;   (def counter {:email-count :0  :spam-count :0})
;   (def rules {:spam-count [[2] condition] :email-count [[] true]} )
;   (def state [counter rules])
;   ;datos a procesar
;   (def new-data {"spam" true})
;
; (defn evaluateConditions [data conditions]
;   true)
;
; (defn applyRule [data rule]
;   ;rule
;   (def conditions (nth (nth rule 1) 1))
;   ; (def params (nth (nth rule 1) 0))
;   ; (def name (nth rule 0))
;   ; (def counter-to-update {:key :0})
;     ;en funcion a los parameter se a cual contador aumentar.
;     ;(if (empty? params) "vacio" "lleno")
;       (evaluateConditions data conditions))
;
; (defn update-counters [counters]
;   (prn counters))
;
; (defn process-data [state new-data]
;   ;aplico cada rule al dato a procesar.
;   ;devolverá un hashmap como el de counters para luego sumar los contadores en true. ej {:email-count :true , :spam-count :false}
;     (for [rule (nth state 1)]
;        (applyRule new-data rule)))
;
(defn query-counter [state counter-name counter-args]
  ; (get counter-name (nth state 0)) diferenciar si es un num o {}
  ; en caso de ser {}, con counter-args entrar a la llave correspondiente
  ; si no fue inicializada crear la llave y asignarle valor 1
  )
