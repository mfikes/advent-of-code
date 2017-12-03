(ns advent-2017.day-03.part-2-test
  (:require
   [clojure.test :refer [deftest are]]
   [advent-2017.day-03.part-2 :refer [solve]]))

(deftest solve-test
  (are [output input] (= output (solve input))
    2 1
    4 3
    10 7
    133 122
    330 325
    747 400))
