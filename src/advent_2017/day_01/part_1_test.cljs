(ns advent-2017.day-01.part-1-test
  (:require
   [clojure.test :refer [deftest are]]
   [advent-2017.day-01.part-1 :refer [solve]]))

(deftest solve-test
  (are [x y] (= x (solve y))
    3 [1 1 2 2]
    4 [1 1 1 1]
    0 [1 2 3 4]
    9 [9 1 2 1 2 1 2 9]))
