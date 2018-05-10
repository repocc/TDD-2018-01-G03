(ns core-operator
  (:require [clojure.string :as string])
  )

  (defn rules-values-map
    [counters-values signals-values]
    (merge counters-values signals-values)
  )

  (defn rule-value
    [state signal rule-name]
    (def counters-values (nth state 0))
    (def signals-values (into {} signal))
    (get (rules-values-map counters-values signals-values) rule-name)
  )

  (defn rules-maker
    [rules-json]
    (prn rules-json)
    )
