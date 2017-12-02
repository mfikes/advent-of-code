(ns advent-2017.day-02.part-2
  (:require
   [advent-2017.day-02.data-2 :as data]))

(defn divides?
  "Returns true iff x divides y."
  [x y]
  (and (not= 0 x y)
       (zero? (mod y x))))

(defn dividing-pairs
  "Returns a lazy sequence of all distinct pairs elements x1 and x2 such that x1
  divides x2."
  [xs]
  (for [x1 xs
        x2 xs
        :when (and (distinct? x1 x2)
                   (divides? x1 x2))]
    [x1 x2]))

(defn first-integer-ratio
  "Returns the ratio of the first pair of distinct elements in xs where one
  divides the other, or nil if none are found."
  [xs]
  (when-let [[x y] (first (dividing-pairs xs))]
    (/ y x)))

(defn solve
  ([] (solve data/puzzle-input))
  ([rows]
   (transduce
     (map first-integer-ratio)
     +
     rows)))
