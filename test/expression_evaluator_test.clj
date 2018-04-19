(ns expression-evaluator-test
  (:require [clojure.test :refer :all]
            [expression-evaluator :refer :all]))

(def counter-1 '(define-counter "email-count" []
                                          true))
(def counter-2 '(define-counter "spam-count" []
                                    (current "spam")))
(def counter-3 '(define-counter "spam-important-table"
                                    [(current "spam")
                                    (current "important")]
                                    true))
(def signal-1 '(define-signal {"spam-fraction"
                                    (/ (counter-value "spam-count" [])
                                    (counter-value "email-count" []))}
                                    true))
(def signal-2 '(define-signal {"repeated" (current "value")}
                                    (= (current "value") (past "value"))))

(def rules (list counter-1 counter-2 counter-3 signal-1 signal-2))

(deftest query-evaluate-expression-current-match-bool
  (testing "Return true if the current value exist on the data map or false otherwise"
    (def data-test {"spam" true})
    (def condi '(current "spam"))
    (def state [{} {} {} {}])
    (is (= (evaluate-expression state data-test condi 0) true))
    ))


(deftest query-evaluate-expression-current-match-value
  (testing "Return true if the current value exist on the data map or false otherwise"
    (def data-test {"value" 2})
    (def condi '(current "value"))
    (def state [{} {} {} {}])
    (is (= (evaluate-expression state data-test condi 0) 2))
    ))


(deftest query-apply-operador-with-past
  (testing "Apply the operator for a especific value of the parameter past"
    (def test-par1 (nth '((past "value") 0) 0))
    (def test-par2 '(nopast true))

    (is (= (apply-operador-with-past "and" test-par1 test-par2 true) true))
    ))

(deftest query-value-past-function
  (testing "Apply the operator for every value in list-values and return the one that made the expression true."
    (def list-values-test (conj () 1 2 3 4) )
    (def test-expre (nth '((past "value") 0) 0))
    (def test-par1 (nth '((past "value") 0) 0))
    (def test-par2 '(nopast 2))

    (is (= (value-past-function list-values-test test-expre "=" test-par1 test-par2) 2))
    ))

(deftest query-evaluate-expression-past
  (testing "Apply the operator for every value in list-values and return the one that made the expression true."
    (def map-data-test {"value" (conj () 1 2 3 4)})
    (def test-expre-past (nth '((past "value") 0) 0))
    (def state [{} {} {} map-data-test])
    (def data-test {"value" 2})
    (def test-expre-complete (nth '((= (current "value") (past "value")) 0) 0))
    (is (= (evaluate-expression state data-test test-expre-past test-expre-complete) 2))
    ))


 (deftest query-evaluate-multi-conditions-match
   (testing "Return true if the current value exist on the data map or false otherwise"
     (def data-test {"sender" "vicky" "subject" "RE:tp"})
     (def condi '(and (= (current "sender") (past "receiver"))(includes? (past "subject") (current "subject"))))
     (def map-data-test {"subject" (conj () "tp") "receiver" (conj () "vicky")})
     (def state [{} {} {} map-data-test])
     (is (= (evaluate-expression state data-test condi 0) true))
     ))

 (deftest query-contains-data-in-map-data-true
  (testing "Return true if map-data contains the data or false otherwise"
    (def data-test (conj () true "spam"))
    (def map-data-test {"spam" (conj () true)})
    (is (= (contains-data-in-map-data data-test map-data-test) true))
    ))

 (deftest query-contains-data-in-map-data-false-value
  (testing "Return true if map-data contains the data or false otherwise"
    (def data-test (conj () false "spam"))
    (def map-data-test {"spam" (conj () true)})
    (is (= (contains-data-in-map-data data-test map-data-test) false))
    ))

 (deftest query-contains-data-in-map-data-false-key
  (testing "Return true if map-data contains the data or false otherwise"
    (def data-test (conj () true "important"))
    (def map-data-test {"spam" (conj () true)})
    (is (= (contains-data-in-map-data data-test map-data-test) false))
    ))

(deftest evaluate-condition-true-from-rule
  (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
     (is (evaluate-conditions-from-rule [] {"spam" true} counter-1))))

(deftest evaluate-condition-current-from-rule
  (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
     (is (evaluate-conditions-from-rule [] {"spam" true} counter-2))))

(deftest evaluate-condition-operators-from-rule
  (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
     (is (evaluate-conditions-from-rule [] {"spam" true} counter-3))))
