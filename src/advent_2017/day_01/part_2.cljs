(ns advent-2017.day-01.part-2
  (:require
   [advent-2017.day-01.data-2 :as data]))

(defn matches
  [xs]
  "Returns xs which match the element halfway away in xs, treating xs as a
  circular buffer."
  (->>
    (map vector xs (nthrest (cycle xs) (/ (count xs) 2)))
    (filter (partial apply =))
    (map first)))

(defn solve
  "Solves the puzzle for a sequence of digits. If no digits specified, solves
  using the main puzzle input."
  ([] (solve data/puzzle-input))
  ([digits] (apply + (matches digits))))
