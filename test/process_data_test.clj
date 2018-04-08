 (ns process-data-test
   (:require [clojure.test :refer :all]
     [data-processor :refer :all]))

 (def counter-1 '(define-counter "email-count" []
                                           true))
 (def counter-2 '(define-counter "spam-count" []
                                     (current "spam")))
 (def counter-3 '(define-counter "spam-important-table"
                                     [(current "spam")]
                                     (current "important")
                                     true))
 (def signal-1 '(define-signal {"spam-fraction"
                                     (/ (counter-value "spam-count" []))
                                     (counter-value "email-count" [])
                                     true}))
 (def signal-2 '(define-signal {"repeated" (current "value")
                                     (= (current "value") (past "value"))}))

 (def rules (list counter-1 counter-2 counter-3 signal-1 signal-2))

 (def initial-state (initialize-counters rules))
 (def signals (save-signal-rules rules))
 (def rules (save-counter-rules rules))

 (def email-count-rule {"email-count" (get rules "email-count")})
 (deftest increment-counter-test
    (testing "increment counter"
     (is (= (inc-counter email-count-rule initial-state) {"email-count" 1, "spam-count" 0}))))

(deftest evaluate-counters-rules-test
  (testing ""))

(deftest name-and-signal-evaluation-test
  (testing "evaluate a signal rule")
  (is (= )))


(deftest update-signal-test)
(testing "update signal, evaluates every signal rule with a new given state"
 (is (= (update-signal initial-state)) '({"spam-fraction" 0})))
