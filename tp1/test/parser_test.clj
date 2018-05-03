(ns parser-test
  (:require [clojure.test :refer :all]
            [parser :refer :all]
            [clojure.string :as string]))

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

(def step-counter-1 '(define-step-counter "new" 1 [] true))

(deftest is-valid-counter-test
  (testing "counter-validation"
      (is (is-valid-counter counter-1))
      (is (is-valid-counter counter-2))
      (is (is-valid-counter counter-3))))

(deftest is-valid-signal-test
 (testing "signal-validation"
     (is (is-valid-signal signal-1))
     (is (is-valid-signal signal-2))))

(deftest is-rule-a-counter-test
  (testing "is rule a counter"
      (is (is-rule-a-counter counter-1))
      (is (is-rule-a-counter counter-2))
      (is (is-rule-a-counter counter-3))
      (is (not (is-rule-a-counter signal-1)))
      (is (not (is-rule-a-counter signal-2)))))

(deftest is-rule-a-signal-test
 (testing "is rule a signal"
     (is (not (is-rule-a-signal counter-1)))
     (is (not (is-rule-a-signal counter-2)))
     (is (not (is-rule-a-signal counter-3)))
     (is (is-rule-a-signal signal-1))
     (is (is-rule-a-signal signal-2))))

(deftest parse-rule-name-test
  (testing "parse name from rule"
      (is (= "email-count"
           (parse-rule-name counter-1)))
      (is (= "spam-count"
            (parse-rule-name counter-2)))
      (is (= "spam-important-table"
            (parse-rule-name counter-3)))
      (is (= "spam-fraction"
            (parse-rule-name signal-1)))
      (is (= "repeated"
            (parse-rule-name signal-2)))
      (is (not (= "spam-fraction"
            (parse-rule-name signal-2))))))

(deftest parse-counter-step-rule-test
  (testing "parse counter step rule"
    (is (= 1 (parse-step-counter-inc step-counter-1)))
    (is (= "new" (parse-rule-name step-counter-1)))
    (is (= [] (parse-counter-params step-counter-1)))
    (is (= true (parse-rule-condition step-counter-1)))
  ))
