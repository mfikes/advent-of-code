(ns advent-2017.day-02.part-1-test
  (:refer-clojure :exclude [range])
  (:require
   [clojure.test :refer [deftest is are]]
   [advent-2017.day-02.part-1 :refer [range solve]]))

(deftest range-test
  (are [x ys] (= x (range ys))
    0 [1 1]
    2 [1 3 2]
    2 [2 3 1]))

(deftest solve-test
  (is (= 18 (solve [[5 1 9 5]
                    [7 5 3]
                    [2 4 6 8]]))))
