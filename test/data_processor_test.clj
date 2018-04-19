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
     (is (=  '("email-count" 0) (initializer/counter-name-and-initial-value counter-1)))
     (is (=  '("spam-count" 0) (initializer/counter-name-and-initial-value counter-2)))
     (is (=  '("spam-important-table" {}) (initializer/counter-name-and-initial-value counter-3)))
))

(deftest name-and-counter-rule-test
  (testing "get an array with counter name and rule elements"
    (is (=  '("email-count" [[] true 1]) (initializer/name-and-counter-rule counter-1)))
    (is (=  '("spam-count" [[] (current "spam") 1]) (initializer/name-and-counter-rule counter-2)))
    (is (=  '("spam-important-table" [[(current "spam") (current "important")] true 1]) (initializer/name-and-counter-rule counter-3)))
))

(deftest name-and-signal-rule-test
  (testing "get an array with signal name and signal elementes (result and condition)"
  ; TODO
  ))

(deftest initialize-counters-test
  (testing "hashmap with every counter initialized"
    (is (= {"email-count" 0, "spam-count" 0, "spam-important-table" {}} (initializer/initialize-counters rules)))
  ))

(deftest initialize-processor-test
  (testing "initialize processor"
  ; (is (= '({"email-count" 0, "spam-count" 0, "spam-important-table" {}} {"email-count" [[] true], "spam-count" [[] (current "spam")], "spam-important-table" [[(current "spam") (current "important")] true]} {"spam-fraction" ["(/ (counter-value \"spam-count\" []) (counter-value \"email-count\" []))" true}))
  ))

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
