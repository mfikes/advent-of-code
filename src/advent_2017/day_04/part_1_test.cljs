(ns advent-2017.day-04.part-1-test
  (:require
   [clojure.test :refer [deftest is are]]
   [advent-2017.day-04.part-1 :refer [valid-passphrase? solve]]))

(deftest valid-passphrase?-test
  (are [valid? passphrase] (= valid? (valid-passphrase? passphrase))
    true "aa bb cc dd ee"
    false "aa bb cc dd aa"
    true "aa bb cc dd aaa"))

(deftest solve-test
  (is (= 2 (solve ["aa bb cc dd ee"
                   "aa bb cc dd aa"
                   "aa bb cc dd aaa"]))))
