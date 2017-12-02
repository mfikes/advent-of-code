(ns advent-2017.day-02.part-2-test
  (:require
   [clojure.test :refer [deftest is are]]
   [advent-2017.day-02.part-2 :refer [divides? dividing-pairs first-integer-ratio solve]]))

(deftest divieds?-test
  (are [r d n] (= r (divides? d n))
    false 0 0
    true 1 0
    true 1 1
    true 1 2
    true 2 0
    false 2 1
    true 2 2
    false 2 3
    true 2 4))

(deftest dividing-pairs-test
  (are [ms xs] (= ms (dividing-pairs xs))
    [] []
    [] [1 1]
    [[1 2]] [1 2]
    [[2 4]] [2 3 4 5]
    [[2 4] [2 6] [3 6]] [2 3 4 5 6]))

(deftest first-integer-ratio-test
  (are [r xs] (= r (first-integer-ratio xs))
    nil []
    nil [1 1]
    2 [1 2]
    2 [2 3 4 5]
    2 [2 3 4 5 6]
    4 [5 9 2 8]
    3 [9 4 7 3]
    2 [3 8 6 5]))

(deftest solve-test
  (is (= 9 (solve [[5 9 2 8]
                   [9 4 7 3]
                   [3 8 6 5]]))))
