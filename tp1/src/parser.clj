(ns parser
  (:require [clojure.string :as string]))
  (defn index-of
    "Return index of value (string or char) in s, optionally searching
    forward from from-index. Return nil if value not found."
    {:added "1.8"}
    ([^CharSequence s value]
    (let [result ^long
          (if (instance? Character value)
            (.indexOf (.toString s) ^int (.charValue ^Character value))
            (.indexOf (.toString s) ^String value))]
      (if (= result -1)
        nil
        result)))
    ([^CharSequence s value ^long from-index]
    (let [result ^long
          (if (instance? Character value)
            (.indexOf (.toString s) ^int (.charValue ^Character value) (unchecked-int from-index))
            (.indexOf (.toString s) ^String value (unchecked-int from-index)))]
      (if (= result -1)
        nil
        result))))


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

(defn is-rule-a-step-counter
  "Returns true if rule is a counter, if not returns false"
  [rule]
  (= "define-step-counter" (str(nth rule 0))))

(defn is-rule-a-counter-or-step-counter
  "Returns true if rule is a counter or step counter, if not returns false"
  [rule]
  (or (is-rule-a-counter rule) (is-rule-a-step-counter rule)))

(defn is-rule-a-signal
  "Returns true if rule is a signal, if not returns false"
  [rule]
  (= "define-signal" (str(nth rule 0))))

(defn parse-rule-name
	"Returns the name of rule"
	[rule]
  (if (is-rule-a-counter-or-step-counter rule)
    (nth rule 1)
    (subs (string/join " " (nth rule 1)) 2  (- (index-of (nth rule 1) " " ) 1))))

(defmulti parse-counter-params (fn [rule] (is-rule-a-step-counter rule)))
(defmethod parse-counter-params true [rule] (nth rule 3))
(defmethod parse-counter-params false [rule] (nth rule 2))
(defmethod parse-counter-params :default [params] {})

(defn parse-signal-result
  "Returns operation to do to get result of a signal"
  [rule]
  (get (nth rule 1) (parse-rule-name rule)))

(defn parse-rule-condition
  "Returns the condition of a rule"
  [rule]
  (last rule))

(defn parse-step-counter-inc
  [rule]
  (nth rule 2))
