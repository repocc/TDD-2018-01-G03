 (ns process-data-test
   (:require [clojure.test :refer :all]
     [data-processor :refer :all]))

     (def rules '((define-counter "email-count" []
                    true)
                  (define-counter "spam-count" []
                    (current "spam"))
                  (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])
                                                     (counter-value "email-count" []))}
                    true)
                  ; (define-counter "spam-important-table" [(current "spam")
                  ;                                         (current "important")]
                  ;   true)))
                  ))

     (def rules2 '((define-counter "email-count" [] true)
                   (define-counter "spam-count" [] (= (current "spam") (current "spam")))))


  (def counter-state (initialize-counters rules))
   ;(def signals (save-signal-rules rules))
   (def counter-rules (save-counter-rules rules))

   (def email-count-rule ["email-count" (get counter-rules "email-count" )])


(deftest name-and-signal-evaluation-test
   (testing "increment counter"
   (is (= (inc-counter email-count-rule counter-state) {"email-count" 1, "spam-count" 0}))))


(deftest evaluate-condition-true-from-rule
  (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
     (is (evaluate-conditions-from-rule {"spam" true} (nth rules 0)))))


(deftest evaluate-condition-current-from-rule
  (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
     (is (evaluate-conditions-from-rule {"spam" true} (nth rules 1)))))


(deftest evaluate-condition-operators-from-rule
  (testing "evaluate-conditions-from-rule should return true if the condition is fulfilled or false if not."
     (is (evaluate-conditions-from-rule {"spam" true} (nth rules2 1)))))