(ns advent-2016.day-03.part-1
  (:require
    [advent-2016.day-03.data :refer [puzzle-input]]))

(defn triangle?
  [a b c]
  (let [[a' b' c'] (sort [a b c])]
    (> (+ a' b') c')))

(defn list-of-edge-lenghts
  [puzzle-input]
  (partition 3 puzzle-input))

(defn triangles
  [list-of-edge-lenghts]
  (filter #(apply triangle? %) list-of-edge-lenghts))

(defn solve
  []
  (count (triangles (list-of-edge-lenghts puzzle-input))))
