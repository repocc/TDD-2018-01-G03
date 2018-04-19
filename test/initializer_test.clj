(ns initializer-test
  (:require [clojure.test :refer :all]
            [initializer :refer :all]
            ))

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

  (def step-counter-1 '(define-step-counter "new" 1 []
   true))
  (def step-counter-2 '(define-step-counter "stepper" (current "delta") []
                                       true))

  (def rules (list counter-1 counter-2 counter-3 signal-1 step-counter-1 step-counter-2))

(deftest save-signal-rules-test
  (testing ""
  ; (is (= {"spam-fraction" [(/ (counter-value "spam-count" []) (counter-value "email-count" [])) true]} (save-signal-rules rules)))
))

(deftest save-counter-rules-test
  (testing "save counter rules"
  (is (= (name-and-counter-rule counter-1) ["email-count" [[] true 1]]))
  (is (= (name-and-step-counter-rule step-counter-1) ["new" [[] true 1]]))
  (is (= (name-and-step-counter-rule step-counter-2) ["stepper" [[] true '(current "delta")]]))
  ))

(deftest step-counters-rules-map-test
  (testing "create step counters rule map"
    ; (is (= (step-counters-rules-map rules) '("new" [[] true 5], "stepper" [[] true '(current "delta")]))
  ))
