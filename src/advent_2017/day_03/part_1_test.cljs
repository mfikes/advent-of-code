(ns advent-2017.day-03.part-1-test
  (:require
   [clojure.test :refer [deftest are]]
   [advent-2017.day-03.part-1 :refer [solve]]))

(deftest solve-test
  (are [distance square] (= distance (solve square))
    0 1
    3 12
    2 23
    31 1024))
