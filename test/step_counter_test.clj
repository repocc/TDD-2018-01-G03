(ns step-counter-test
  (:require [clojure.test :refer :all]
            [initializer :as initializer]
            [clojure.string :as string]
            [parser :as parser]))

(def rule '(define-step-counter "new" 1 []
                                        true))
(deftest get-name-rule-step-couter-test
    (testing ""
    (is (= (parser/parse-rule-name rule) "new" )))
)


(deftest name-and-step-counter-rule-test
    (testing ""
    (is (= (initializer/name-and-step-counter-rule rule) ["new" [[] true 1]])))
)

(deftest initialize-counters-test
  (testing "hashmap with every counter initialized"
    (is (= {"new" 0} (initializer/initialize-counters '(rule))
  ))))
