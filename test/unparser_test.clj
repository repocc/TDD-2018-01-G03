(ns unparser-test
  (:require [clojure.test :refer :all]
            [unparser :refer :all]))
(use '[clojure.string :as s])

(deftest query-counter-get-value-counter-no-conditions
  (testing "get-value-counter should return the correct value."
   (is (= 2 (get-value-counter 2 []) ))))


(deftest query-counter-get-value-counter-with-conditions
  (testing "get-value-counter should return the correct value."
   (is (= 1 (get-value-counter {[true false] 1} [true false]) ))))
