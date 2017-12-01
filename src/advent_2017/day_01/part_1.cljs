(ns advent-2017.day-01.part-1
  (:require
   [advent-2017.day-01.data-1 :as data]))

(defn matches
  "Returns xs which match their successor element, treating xs as a circular
  buffer."
  [xs]
  (->>
    (map vector xs (rest (cycle xs)))
    (filter (partial apply =))
    (map first)))

(defn solve
  "Solves the puzzle for a sequence of digits. If no digits specified, solves
  using the main puzzle input."
  ([] (solve data/puzzle-input))
  ([digits] (apply + (matches digits))))
