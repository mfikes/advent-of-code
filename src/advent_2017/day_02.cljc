(ns advent-2017.day-02
  (:refer-clojure :exclude [range])
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (->> "advent_2017/day_02/input" io/resource io/reader line-seq))

(def data (->> input
            (map #(str/split % #"\t"))
            (map #(map read-string %))))

(defn range
  "Finds the difference between the maximum and minimum values in the
   non-empty sequence of numbers xs."
  [xs]
  (- (apply max xs) (apply min xs)))

(defn solve
  [f]
  (transduce
    (map f)
    +
    data))

(defn part-1 []
  (solve range))

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

(defn part-2 []
  (solve first-integer-ratio))
