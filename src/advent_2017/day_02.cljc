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

(defn solve [f]
  (transduce
    (map f)
    +
    data))

(defn part-1 []
  (solve #(- (apply max %) (apply min %))))

(defn divides? [x y]
  (and (not= 0 x y)
       (zero? (mod y x))))

(defn dividing-pairs [xs]
  (for [x1 xs
        x2 xs
        :when (and (distinct? x1 x2)
                   (divides? x1 x2))]
    [x1 x2]))

(defn first-integer-ratio [xs]
  (when-let [[x y] (first (dividing-pairs xs))]
    (/ y x)))

(defn part-2 []
  (solve first-integer-ratio))
