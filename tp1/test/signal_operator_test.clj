(ns signal-operator-test
  (:require [clojure.test :refer :all]
            [signal-operator :refer :all]
            [initializer :as initializer]
            [data-processor :as dp]))

(def signal-1 '(define-signal {"spam-fraction"
                                    (/ (counter-value "spam-count" [])
                                    (counter-value "email-count" []))}
                                    true))
(def signal-2 '(define-signal {"repeated" (current "value")}
                                    (= (current "value") (past "value"))))

(def rules (list signal-1 signal-2))

(def initial-state (dp/initialize-processor rules))

(deftest name-and-signal-evaluation-test
  (testing "evaluate a signal rule"
  (def data-test {"spam" true})
  (def signal-rule (seq (initializer/name-and-signal-rule signal-1)))
  (def state [{"spam-count" 1 "email-count" 2} {} {} {}])
  (is (= ((name-and-signal-evaluation  state data-test signal-rule) "spam-fraction") 1/2))))

(deftest evaluate-signal-condition-test
  (testing "evaluate signal condition true"
    (def data-test {"spam" true})
    (is (= (evaluate-signal-condition true initial-state data-test) true ))))

(deftest evaluate-signal-initial-state-test
  (testing "evaluate signal initial state"
    (def data-test {"spam" true})
    (is (= (evaluate-signal-condition '(= (current "value") (past "value")) initial-state data-test) false ))))
