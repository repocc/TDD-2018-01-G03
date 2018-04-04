(ns data-processor)

(defn elementos-map-counter [rule]
;Devuelve un array con el nombre y contador, inicialmente en 0, de la regla.
    (let [nombre (nth rule 1)]
    (identity [nombre 0])) 
    
)

(defn elementos-map-rules [rule]
;Devuelve un array con el nombre y lista con parametros y condiciones de la regla.
    (let [nombre (nth rule 1)
          parametros (nth rule 2)
          condicion (nth rule 3)]
    (identity [nombre [parametros condicion]])) 
    
)

(defn generar-counter [rules]
;Genera un hashmap con el nombre de la regla como key y un contador inicialmente en 0 como value.
     (into {} (map elementos-map-counter rules))
  )


(defn generar-rules [rules]
;Genera un hashmap con el nombre de la regla como key y una lista con paramtros y condiciones como value.
     (into {} (map elementos-map-rules rules))
  )

(defn solo-counter [rule]
;Devuelve true si la regla es define-counter o false en caso contrario
  (def tipo (str (nth rule 0)))
  (= tipo "define-counter") 
  )

(defn initialize-processor [rules]
  ;rules es una lista de reglas. Cada regla tiene:
  ;   define-counter/ define-signal nombre parametro condicion
  ;Devuelve un array con dos hashmap, el primero con los contadores el segundo con las reglas
  (let [new-rules (filter solo-counter rules) ]
  
  [(generar-counter new-rules) (generar-rules new-rules)])
)


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
