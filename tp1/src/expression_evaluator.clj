(ns expression-evaluator
  (:require [unparser :as unparser])
  )
; (use '[clojure.string :as s])

(defn starts-with?
  "True if s starts with substr."
  {:added "1.8"}
  [^CharSequence s ^String substr]
  (.startsWith (.toString s) substr))

(defn ends-with?
  "True if s ends with substr."
  {:added "1.8"}
  [^CharSequence s ^String substr]
  (.endsWith (.toString s) substr))

(defn includes?
  "True if s includes substr."
  {:added "1.8"}
  [^CharSequence s ^CharSequence substr]
  (.contains (.toString s) substr))
  (defn includes [coll value]
    (let [s (seq coll)]
      (if s (if (= (first s) value) true (recur (rest s) value)) false)))

  (defn includes-otra [substr s]
  (includes? s substr))

  (defn starts-with [s substr]
    (starts-with? s substr))

  (defn ends-with [s substr]
    (ends-with? s substr))

  (defn new-or [value1 value2]
    (or value1 value2))

  (defn new-and [value1 value2]
    (and value1 value2))

  (defn new-not [value1 value2]
    (not value1 value2))

  (defn division [value1 value2]
    (def res nil)
    (if-not (= value2 0) (def res (/ value1 value2)))
    res)

  (def operators {"=" = "+" + "-" - "*" * "/" division "mod" mod "<" < ">" > "<=" <= ">=" >= "concat" str "!=" distinct? "includes?" includes-otra "starts-with?" starts-with "ends-with?" ends-with "or" new-or "and" new-and "not" new-not})

  (defn get-operator [operator]
    ;Toma el operador pasado como parametro y llama a la funcion correspondiente
    (get operators operator))

  (defn query-counter [state counter-name counter-args]
    ; (get counter-name (nth state 0)) diferenciar si es un num o {}
    ; en caso de ser {}, con counter-args entrar a la llave correspondiente
    ; si no fue inicializada crear la llave y asignarle valor 1

    (let [valor (get (unparser/get-counter-map state) counter-name)]
      (unparser/get-value-counter valor counter-args)))

  (defn contains-data-in-map-data [data map-data]
    (def is-data false)
    (if (contains? map-data (first data))
      (if (includes (get map-data (first data)) (last data))
        (def is-data true)))
    is-data)

  (defn apply-operador [opr par1 par2]
     ;Aplica la funcion correspondiente del opr a los parametros par1 y par2.
    (apply (get-operator opr) [par1 par2]))

  (defn apply-operador-with-past [old-operator old-par1 old-par2 value-past]
    (def par1 (nth old-par1 1))
    (if (= (str(nth old-par1 0)) "past") (def par1 value-past) )
    (def par2 (nth old-par2 1))
    (if (= (str(nth old-par2 0)) "past") (def par2 value-past) )
    (apply-operador old-operator par1 par2))

  (defn value-past-function [list-values expre old-operator old-par1 old-par2]
      (def ret-value (nth list-values 0))
      (doseq [value list-values]
        (if (apply-operador-with-past old-operator old-par1 old-par2 value) (def ret-value value)))
      ret-value)

  (defmulti evaluate-expression (fn [state data expre old-expre] (str(nth expre 0))))
  (defmethod evaluate-expression "past" [state data expre old-expre]
     ;chequeo para cada dato del historial, cual me cumple expre y devuelvo ese dato. Si ninguno cumple devuelvo cualquiera.
     ;Si no hay datos en el historial para ese valor retorno "NOEXISTE"
      (let [map-data (unparser/get-past-data state)
                      key-dato (nth expre 1)]

      (def ret-value "NOEXISTE")
      (def old-operator (str(nth old-expre 0)))
      (def old-par1 (nth old-expre 1))
      (if (distinct? (str(nth old-par1 0)) "past") (def old-par1  (conj () (evaluate-expression state data old-par1 0) (nth '(nopast) 0))))
      (def old-par2 (nth old-expre 2))
      (if (distinct? (str(nth old-par2 0)) "past") (def old-par2  (conj () (evaluate-expression state data old-par2 0) (nth '(nopast) 0))))
      (if (contains? map-data key-dato)
          (def ret-value (value-past-function (get map-data key-dato) expre old-operator old-par1 old-par2)))
      ret-value))

  (defmethod evaluate-expression "current" [state data expre old-expre]
    (get data (nth expre 1)))
  (defmethod evaluate-expression "counter-value" [state data expre old-expre]
    (query-counter state (nth expre 1) (nth expre 2)))
  (defmethod evaluate-expression :default [state data expre old-expre]
    (apply-operador (str(nth expre 0)) (evaluate-expression state data (nth expre 1) expre) (evaluate-expression state data (nth expre 2) expre)))

  (defmulti conditions (fn [state data condi] condi))
  (defmethod conditions true [state data condi] condi)
  (defmethod conditions false [state data condi] condi)
  (defmethod conditions :default [state data condi]

            (evaluate-expression state data condi 0))

  (defn evaluate-conditions-from-rule [state data rule]
   (conditions state data (nth rule 3)))

  (defn evaluate-condition  [state data condition]
    (conditions state data condition ))
