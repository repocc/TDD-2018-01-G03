(ns data-processor)

(defn initialize-processor [rules]
  nil)


  ;datos que definen state
  (def condition '(current "spam"))
  (def counter {:email-count :0  :spam-count :0})
  (def rules {:spam-count [[2] condition] :email-count [[] true]} )
  (def state [counter rules])
  ;datos a procesar
  (def new-data {"spam" true})

(defn evaluateConditions [data conditions]
  true

  )

(defn applyRule [data rule]

  ;rule
  (def conditions (nth (nth rule 1) 1))
  ; (def parameters (nth (nth rule 1) 0))
  ; (def name (nth rule 0))
  ; (def counter-to-update {:key :0})

    ;en funcion a los parametros se a cual contador aumentar.
    ;(if (empty? parameters) "vacio" "lleno")

      (evaluateConditions data conditions)

    )

(defn update-counters [counters]
  (prn counters)
  )

(defn process-data [state new-data]
  ;aplico cada rule al dato a procesar.
  ;devolverá un hashmap como el de counters para luego sumar los contadores en true. ej {:email-count :true , :spam-count :false}
    (for [rule (nth state 1)]
       (applyRule new-data rule)
       
     )


    )
(defn query-counter [state counter-name counter-args]
  0)
