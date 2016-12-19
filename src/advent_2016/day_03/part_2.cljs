(ns advent-2016.day-03.part-2
  (:require
    [advent-2016.day-03.data :refer [puzzle-input]]))

(defn triangle?
  [a b c]
  (let [[a' b' c'] (sort [a b c])]
    (> (+ a' b') c')))

(defn list-of-edge-lenghts
  [puzzle-input]
  (partition 3 puzzle-input))

(defn transpose
  [edges]
  (->> (apply concat (apply map vector edges))
    list-of-edge-lenghts))

(defn triangles
  [list-of-edge-lenghts]
  (filter #(apply triangle? %) list-of-edge-lenghts))

(defn solve
  []
  (count (triangles (transpose (list-of-edge-lenghts puzzle-input)))))
