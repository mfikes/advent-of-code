(ns advent-2017.day-01.part-2-test
  (:require
   [clojure.test :refer [deftest are]]
   [advent-2017.day-01.part-2 :refer [solve]]))

(deftest solve-test
  (are [x y] (= x (solve y))
    6 [1 2 1 2]
    0 [1 2 2 1]
    4 [1 2 3 4 2 5]
    12 [1 2 3 1 2 3]
    4 [1 2 1 3 1 4 1 5]))
