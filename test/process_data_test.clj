(ns process-data-test
  (:require [clojure.test :refer :all]
     [data-processor :refer :all]
     [signal-operator :refer :all]
     [counter-operator :as counter-operator]))

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

  (def step-counter-1 '(define-step-counter "new" 5 []
   true))
  (def step-counter-2 '(define-step-counter "stepper" (current "delta") []
                                       true))

  (def rules (list counter-1 counter-2 counter-3 signal-1 step-counter-1 step-counter-2))
  (def initial-state (initialize-processor rules))
  (def signals (initializer/save-signal-rules rules))
  (def counters-rules (initializer/save-counter-rules rules))
  (def rules2 '((define-counter "email-count" [] true) (define-counter "spam-count" []
                                                (= (current "spam") (current "spam")))))
  (def counter-state (initializer/initialize-counters rules))

  (def data {"spam" true})
  (def data-parameteres {"spam" true "important" true})

  (def email-count-rule ["email-count" (get counters-rules "email-count" )])
  (def spam-important-count-rule ["spam-important-table" (get counters-rules "spam-important-table" )])


  (deftest get-increment-test
    (testing "increment expresion"
      (is (= 1 (counter-operator/get-inc-expression email-count-rule)))
      (def step-counter-1-rule ["new" (get counters-rules "new")])
      (is (= 5 (counter-operator/get-inc-expression step-counter-1-rule)))
      (def step-counter-2-rule ["stepper" (get counters-rules "stepper")])
      (is (= '(current "delta") (counter-operator/get-inc-expression step-counter-2-rule)))
    ))

  (deftest increment-counter-test
     (testing "increment counter"
     (is (= (counter-operator/inc-counter initial-state email-count-rule data counter-state) {"email-count" 1, "spam-count" 0, "spam-important-table" {}, "new" 0, "stepper" 0}))))

  (deftest increment-counter-with-parameters-test
    (testing "increment counter with parameters"
    (is (= (counter-operator/inc-counter initial-state spam-important-count-rule  data-parameteres counter-state) {"email-count" 0, "spam-count" 0, "spam-important-table" {[true true] 1}, "new" 0, "stepper" 0}))))

  (deftest increment-counter-mixed-values-test
    (testing "increment counter mixed values"
    (def counter-0 (counter-operator/inc-counter initial-state spam-important-count-rule  data-parameteres counter-state))
    (def data-parameteres-0 {"important" true "spam" true})
    (def data-parameteres-1 {"important" false "spam" true})
    (is (= (counter-operator/inc-counter initial-state spam-important-count-rule  data-parameteres-0 counter-0) {"email-count" 0, "spam-count" 0, "spam-important-table" {[true true] 2}, "new" 0, "stepper" 0}))
    (is (= (counter-operator/inc-counter initial-state spam-important-count-rule  data-parameteres-1 counter-0) {"email-count" 0, "spam-count" 0, "spam-important-table" {[true true] 1 [true false] 1}, "new" 0, "stepper" 0}))))


  (deftest query-calculate-signal-result
    (testing "calculate the signal result."
      (def data-test {"spam" true})
      (def signal-rule '(/ (counter-value "spam-count" []) (counter-value "email-count" [])))
      (def state [{"spam-count" 1 "email-count" 2} {} {} {}])
      (is (=(calculate-signal-result  signal-rule state data-test) 1/2))))
