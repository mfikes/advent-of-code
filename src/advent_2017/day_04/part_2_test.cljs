(ns advent-2017.day-04.part-2-test
  (:require
   [clojure.test :refer [deftest is are]]
   [advent-2017.day-04.part-2 :refer [valid-passphrase? solve]]))

(deftest valid-passphrase?-test
  (are [valid? passphrase] (= valid? (valid-passphrase? passphrase))
    true "abcde fghij"
    false "abcde xyz ecdab"
    true "a ab abc abd abf abj"
    true "iiii oiii ooii oooi oooo"
    false "oiii ioii iioi iiio"))

(deftest solve-test
  (is (= 3 (solve ["abcde fghij"
                   "abcde xyz ecdab"
                   "a ab abc abd abf abj"
                   "iiii oiii ooii oooi oooo"
                   "oiii ioii iioi iiio"]))))
