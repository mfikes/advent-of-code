(ns advent-2017.day-02.part-1
  (:refer-clojure :exclude [range])
  (:require
   [advent-2017.day-02.data-1 :as data]))

(defn range
  "Finds the difference between the maximum and minimum values in the
   non-empty sequence of numbers xs."
  [xs]
  (- (apply max xs) (apply min xs)))

(defn solve
  ([] (solve data/puzzle-input))
  ([rows]
   (transduce
     (map range)
     +
     rows)))
