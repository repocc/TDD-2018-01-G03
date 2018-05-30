(ns initializer
  (:require [parser :as parser]))
(use '[clojure.string :as s])

(defmulti initialize-counter (fn [params] (count params)))
(defmethod initialize-counter 0 [params] 0)
(defmethod initialize-counter 1 [params] 0)
(defmethod initialize-counter :default [params] {})

(defn counter-name-and-initial-value [rule]
    "Returns an array with counter name and initial value of the counter"
    (identity [  (parser/parse-rule-name rule) (initialize-counter (parser/parse-counter-params rule))]))

(defn name-and-counter-rule [rule]
    "Returns an array with counter name and with an array with rule elements"
    (identity [ (parser/parse-rule-name rule)
              [ (parser/parse-counter-params rule) (parser/parse-rule-condition rule) 1]]))

(defn name-and-step-counter-rule [rule]
    "Returns an array with counter name and with an array with rule elements"
    (identity [ (parser/parse-rule-name rule)
              [ (parser/parse-counter-params rule) (parser/parse-rule-condition rule)
              (parser/parse-step-counter-inc rule)]]))

(defn name-and-signal-rule [rule]
    "Returns an array with signal name and with an array with rules elements"
    (identity [ (parser/parse-rule-name rule) [(parser/parse-signal-result rule) (parser/parse-rule-condition rule)]]))

(defn step-counters-rules-map [rules]
  "Returns a hashmap where every key is a counter name and as value the initial counter value"
     (into {} (map name-and-step-counter-rule (filter parser/is-rule-a-step-counter rules))))

(defn counters-rules-map [rules]
 "Returns a hashmap where every key is a counter name and as value the initial counter value"
  (into {} (map name-and-counter-rule (filter parser/is-rule-a-counter rules))))


(defn initialize-counters [rules]
  "Returns a hashmap where every key is a counter name and as value the initial counter value"
     (into {} (map counter-name-and-initial-value (filter parser/is-rule-a-counter-or-step-counter rules))))

(defn save-counter-rules [rules]
  "Returns a hashmap where every key is the rule name and as value
  a list with params and condition"
  (def counters (counters-rules-map rules))
  (def step-counters (step-counters-rules-map rules))
  (def total (merge counters step-counters))
  total)

(defn save-signal-rules [rules]
  "Returns a hashmap where every key is the rule name and as value
  a list with operation and condition"
     (into {} (map name-and-signal-rule (filter parser/is-rule-a-signal rules))))
