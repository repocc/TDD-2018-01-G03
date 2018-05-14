(ns core-operator
  (:require [clojure.string :as string])
  )

  (defn rules-values-map
    [counters-values signals-values]
    (prn signals-values)
    (prn (merge counters-values signals-values))
    (merge counters-values signals-values)
  )


  (defn rule-value
    [state signal rule-name]
    (def counters-values (nth state 0))
    (def signals-values (into {} signal))
    (def value (get (rules-values-map counters-values signals-values) rule-name))
    (def not-nil? (complement nil?))
    (if (not-nil? value)
      value
      0)
  )

  (defn rules-maker
    [rules-json]
    (prn rules-json)
    )
