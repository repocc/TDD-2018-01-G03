(ns parser-test
  (:require [clojure.test :refer :all]
            [parser :refer :all]))

(def counter-1 '(define-counter "email-count" []
                                          true))
(def counter-2 '(define-counter "spam-count" [] (
                                current "spam")))
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


(deftest is-valid-counter-test
  (testing "counter-validation"
      (is (parser/is-valid-counter counter-1))
      (is (parser/is-valid-counter counter-2))
      (is (parser/is-valid-counter counter-3))
           ))

(deftest is-valid-signal-test
 (testing "signal-validation"
     (is (parser/is-valid-signal signal-1))
     (is (parser/is-valid-signal signal-2))
          ))

(deftest is-rule-a-counter-test
  (testing "is rule a counter"
      (is (parser/is-rule-a-counter counter-1))
      (is (parser/is-rule-a-counter counter-2))
      (is (parser/is-rule-a-counter counter-3))
      (is (not (parser/is-rule-a-counter signal-1)))
      (is (not (parser/is-rule-a-counter signal-2)))
           ))
(deftest is-rule-a-signal-test
 (testing "is rule a signal"
     (is (not (parser/is-rule-a-signal counter-1)))
     (is (not (parser/is-rule-a-signal counter-2)))
     (is (not (parser/is-rule-a-signal counter-3)))
     (is (parser/is-rule-a-signal signal-1))
     (is (parser/is-rule-a-signal signal-2))
          ))

(deftest parse-rule-name-test
  (testing "Get name from rule"
      (is (= "email-count"
           (parser/parse-rule-name counter-1)))
      (is (= "spam-count"
            (parser/parse-rule-name counter-2)))
      (is (= "spam-important-table"
            (parser/parse-rule-name counter-3)))
      (is (= "spam-fraction"
            (parser/parse-rule-name signal-1)))
      (is (= "repeated"
            (parser/parse-rule-name signal-2)))
      (is (not (= "spam-fraction"
            (parser/parse-rule-name signal-2))))
           ))
