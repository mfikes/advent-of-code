(ns advent-2016.day-06.part-1
  (:require 
    [advent-2016.day-06.data :refer [sample-input puzzle-input]]))

(defn input->strings
  [input]
  (map str input))

(defn transpose
  [x]
  (apply map vector x))

(defn mode
  [x]
  (->> x
    frequencies
    (sort-by second)
    last
    first))

(defn solve
  [input]
  (->> input
    input->strings
    transpose
    (map mode)
    (apply str)))
