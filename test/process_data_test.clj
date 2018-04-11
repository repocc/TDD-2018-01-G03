(ns process-data-test
  (:require [clojure.test :refer :all]
     [data-processor :refer :all]))

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

  (def rules (list counter-1 counter-2 counter-3 signal-1 ))
  (def initial-state (initialize-processor rules))
  (def signals (save-signal-rules rules))
  (def counters-rules (save-counter-rules rules))
  (def rules2 '((define-counter "email-count" [] true) (define-counter "spam-count" []
                                                (= (current "spam") (current "spam")))))
  (def counter-state (initialize-counters rules))

  (def data {"spam" true})
  (def data-parameteres {"spam" true "important" true})

  (def email-count-rule ["email-count" (get counters-rules "email-count" )])
  (def spam-important-count-rule ["spam-important-table" (get counters-rules "spam-important-table" )])

  (deftest increment-counter-test
     (testing "increment counter"
     (is (= (inc-counter initial-state email-count-rule data counter-state) {"email-count" 1, "spam-count" 0, "spam-important-table" {}}))))

  (deftest increment-counter-with-parameters-test
    (testing "increment counter with parameters"
    (is (= (inc-counter initial-state spam-important-count-rule  data-parameteres counter-state) {"email-count" 0, "spam-count" 0, "spam-important-table" {[true true] 1}}))))

    (deftest increment-counter-mixed-values-test
      (testing "increment counter mixed values"
      (def counter-0 (inc-counter initial-state spam-important-count-rule  data-parameteres counter-state))
      (def data-parameteres-0 {"important" true "spam" true})
      (def data-parameteres-1 {"important" false "spam" true})
      (is (= (inc-counter initial-state spam-important-count-rule  data-parameteres-0 counter-0) {"email-count" 0, "spam-count" 0, "spam-important-table" {[true true] 2}}))
        (is (= (inc-counter initial-state spam-important-count-rule  data-parameteres-1 counter-0) {"email-count" 0, "spam-count" 0, "spam-important-table" {[true true] 1 [true false] 1}}))))

  (deftest evaluate-condition-true-from-rule
    (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
       (is (evaluate-conditions-from-rule [] {"spam" true} counter-1))))


  (deftest evaluate-condition-current-from-rule
    (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
       (is (evaluate-conditions-from-rule [] {"spam" true} counter-2))))


  (deftest evaluate-condition-operators-from-rule
    (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
       (is (evaluate-conditions-from-rule [] {"spam" true} counter-3))))


  (deftest query-calculate-signal-result
    (testing "calculate the signal result."
      (def data-test {"spam" true})
      (def signal-rule '(/ (counter-value "spam-count" []) (counter-value "email-count" [])))
      (def state [{"spam-count" 1 "email-count" 2} {} {} {}])
      (is (=(calculate-signal-result  signal-rule state data-test) 1/2))))


   ;(deftest evaluate-counters-rules-test
   ;  (testing ""))

  (deftest name-and-signal-evaluation-test
    (testing "evaluate a signal rule"
    (def data-test {"spam" true})
    (def signal-rule (seq (name-and-signal-rule signal-1)))
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
