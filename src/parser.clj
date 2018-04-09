(ns parser
  (:require [clojure.string :as string]))

(defn is-valid-counter
  "Returns true if the rule is valid, if not returns false"
  [rule]
  (= 4 (count rule))) ;TODO: improve fn

(defn is-valid-signal
    "Returns true if the rule is valid, if not returns false"
    [rule]
    (= 3 (count rule))) ;TODO: improve fn

(defn is-rule-a-counter
  "Returns true if rule is a counter, if not returns false"
  [rule]
  (= "define-counter" (str(nth rule 0))))

(defn is-rule-a-signal
  "Returns true if rule is a signal, if not returns false"
  [rule]
  (= "define-signal" (str(nth rule 0))))

(defn parse-rule-name
	"Returns the name of rule"
	[rule]
  (if (is-rule-a-counter rule)
    (nth rule 1)
    (subs (string/join " " (nth rule 1)) 2  (- (string/index-of (nth rule 1) " " ) 1))))

(defn parse-counter-params
  "Returns the params of a counter"
  [rule]
  (nth rule 2))

(defn parse-signal-result
  "Returns operation to do to get result of a signal"
  [rule]
  (get (nth rule 1) (parse-rule-name rule)))

(defn parse-rule-condition
  "Returns the condition of a rule"
  [rule]
  (last rule))
