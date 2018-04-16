(ns signal
  (:require [expression-evaluator :as exp_evaluator]))

  (defn get-signal-rules [state]
    "Return the signal rules map from the state list."
    (nth state 2))

  (defn evaluate-signal-condition [signal-condition state data]
    "Returns the result of the evaluation of signal condition"
    (exp_evaluator/evaluate-condition state data signal-condition))

  (defn calculate-signal-result [signal-result state data]
    (exp_evaluator/evaluate-expression state data signal-result 0))

  (defn name-and-signal-evaluation [state data signal-rule ]
    "Returns an array with name of the signal to eval
    and the result of signal rule evaluation if there is
    not possible result return nil"

    (def signal-name (nth signal-rule 0))
    (def signal-result (nth (nth signal-rule 1) 0))
    (def signal-condition (nth (nth signal-rule 1) 1))
    (def res-value {})
    (if (evaluate-signal-condition signal-condition state data)
      (if-not (= nil (calculate-signal-result signal-result state data))
            (def res-value {signal-name (calculate-signal-result signal-result state data)})))
    res-value)

  (defn update-signal [state data]
    "Returns signal evaluation"
    (def lista-signals (map name-and-signal-evaluation (repeat state) (repeat data) (seq (get-signal-rules state)) ))
    (def res-value (list (into {} lista-signals)))
    (if (= res-value '({}) ) (def res-value []))
     res-value)
