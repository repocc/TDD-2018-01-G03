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

(deftest query-add-value-map-data-no-exist 
  (testing "add-value-map-data should add the value to the key already created in the map"
    (def data-test (conj () true "spam"))
    (def map-data-test {"spam" (conj () false)})
    (is (= (add-value-map-data data-test map-data-test) {"spam" (conj () false true)}))
    ))

(deftest query-add-value-map-data-exist
  (testing "add-value-map-data should add the value to the key already created in the map"
    (def data-test (conj () true "spam"))
    (def map-data-test {"spam" (conj () true)})
    (is (= (add-value-map-data data-test map-data-test) {"spam" (conj () true)}))
    ))

(deftest query-add-data-map-data
  (testing "add-data-map-data should add the key value to the map"
    (def data-test (conj () true "important"))
    (def map-data-test {"spam" (conj () true)})
    (is (= (add-value-map-data data-test map-data-test) {"spam" (conj () true) "important" (conj () true)}))
    ))

(deftest query-update-map-data-exist-key-and-value
  (testing "update-map-data should update the key value to the map"
    (def data-test (conj () false "spam"))
    (def map-data-test {"spam" (conj () false)})
    (is (= (add-value-map-data data-test map-data-test) {"spam" (conj () false)}))
    ))

(deftest query-update-map-data-exist-key-not-value
  (testing "update-map-data should update the key value to the map"
    (def data-test (conj () false "spam"))
    (def map-data-test {"spam" (conj () true)})
    (is (= (add-value-map-data data-test map-data-test) {"spam" (conj () true false) }))
    ))

(deftest query-update-map-data-not-exist-key-and-value
  (testing "update-map-data should update the key value to the map"
    (def data-test (conj () false "spam"))
    (def map-data-test {"important" (conj () true)})
    (is (= (add-value-map-data data-test map-data-test) {"important" (conj () true) "spam" (conj () false)}))
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

(deftest query-evaluate-conditions-past-exist
  (testing "Return true if the value exist on the map-data or false otherwise"
    (def data-test {"value" 2})
    (def condi '(past "value"))
    (def map-data-test {"value" (conj () 2)})
    (def state [{} {} {} map-data-test])
    (is (= (evaluate-conditions state data-test condi) true))
    ))


(deftest query-evaluate-conditions-past-not-exist
  (testing "Return true if the value exist on the map-data or false otherwise"
    (def data-test {"value" 2})
    (def condi '(past "value"))
    (def map-data-test {"value" (conj () 1)})
    (def state [{} {} {} map-data-test])
    (is (= (evaluate-conditions state data-test condi) false))
    ))

(deftest query-evaluate-conditions-past-exist-more-data
  (testing "Return true if the value exist on the map-data or false otherwise"
    (def data-test {"value" 1})
    (def condi '(past "value"))
    (def map-data-test {"value" (conj () 1) "spam" true})
    (def state [{} {} {} map-data-test])
    (is (= (evaluate-conditions state data-test condi) true))
    ))

(deftest query-evaluate-conditions-past-not-exist-value-more-data
  (testing "Return true if the value exist on the map-data or false otherwise"
    (def data-test {"value" 1})
    (def condi '(past "value"))
    (def map-data-test {"value" (conj () 2) "spam" true})
    (def state [{} {} {} map-data-test])
    (is (= (evaluate-conditions state data-test condi) false))
    ))

(deftest query-evaluate-conditions-past-not-exist-key-more-data
  (testing "Return true if the value exist on the map-data or false otherwise"
    (def data-test {"other-value" 1})
    (def condi '(past "value"))
    (def map-data-test {"value" (conj () 1) "spam" true})
    (def state [{} {} {} map-data-test])
    (is (= (evaluate-conditions state data-test condi) false))
    ))

(deftest query-evaluate-conditions-current-match
  (testing "Return true if the current value exist on the data map or false otherwise"
    (def data-test {"spam" true})
    (def condi '(current "spam"))
    (def state [{} {} {} {}])
    (is (= (evaluate-conditions state data-test condi) true))
    ))

(deftest query-evaluate-conditions-current-not-match
  (testing "Return true if the current value exist on the data map or false otherwise"
    (def data-test {"spam" false})
    (def condi '(current "spam"))
    (def state [{} {} {} {}])
    (is (= (evaluate-conditions state data-test condi) false))
    ))


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