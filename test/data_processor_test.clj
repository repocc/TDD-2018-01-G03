(ns data-processor-test
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

(def rules (list counter-1 counter-2 counter-3 signal-1 signal-2))


(deftest counter-name-and-initial-value-test
   (testing "get an array with counter name and initial value"
     (is (=  '("email-count" 0) (counter-name-and-initial-value counter-1)))
     (is (=  '("spam-count" 0) (counter-name-and-initial-value counter-2)))
     (is (=  '("spam-important-table" {}) (counter-name-and-initial-value counter-3)))
))

(deftest name-and-counter-rule-test
  (testing "get an array with counter name and rule elements"
    (is (=  '("email-count" [[] true]) (name-and-counter-rule counter-1)))
    (is (=  '("spam-count" [[] (current "spam")]) (name-and-counter-rule counter-2)))
    (is (=  '("spam-important-table" [[(current "spam") (current "important")] true]) (name-and-counter-rule counter-3)))
))

(deftest name-and-signal-rule-test
  (testing "get an array with signal name and signal elementes (result and condition)"
  ; TODO
  ))

(deftest initialize-counters-test
  (testing "hashmap with every counter initialized"
    (is (= {"email-count" 0, "spam-count" 0, "spam-important-table" {}} (initialize-counters rules)))
  ))

(deftest save-counter-rules-test
  (testing ""
  ; (is (= {"email-count" [[] true], "spam-count" [[] (current "spam")], "spam-important-table" [[(current "spam") (current "important")] true]} (save-counter-rules rules)))
))

(deftest save-signal-rules-test
  (testing ""
  ; (is (= {"spam-fraction" [(/ (counter-value "spam-count" []) (counter-value "email-count" [])) true]} (save-signal-rules rules)))
))

(deftest initialize-processor-test
  (testing "initialize processor"
  ; (is (= '({"email-count" 0, "spam-count" 0, "spam-important-table" {}} {"email-count" [[] true], "spam-count" [[] (current "spam")], "spam-important-table" [[(current "spam") (current "important")] true]} {"spam-fraction" ["(/ (counter-value \"spam-count\" []) (counter-value \"email-count\" []))" true}))
  ))

(deftest query-counter-get-value-counter-no-conditions
  (testing "get-value-counter should return the correct value."
   (is (= 2 (get-value-counter 2 []) ))))


(deftest query-counter-get-value-counter-with-conditions
  (testing "get-value-counter should return the correct value."
   (is (= 1 (get-value-counter {[true false] 1} [true false]) ))))
