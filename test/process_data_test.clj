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
    (def counter-state (initialize-counters rules))
    (def signals (save-signal-rules rules))
    (def rules (save-counter-rules rules))

    (def email-count-rule [:email-count (get rules :email-count )])

 (deftest name-and-signal-evaluation-test
    (testing "increment counter"
    (is (= (inc-counter email-count-rule counter-state) {:email-count 1, :spam-count 0}))))

 
