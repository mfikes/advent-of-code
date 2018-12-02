(ns advent-2018.day-01
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]))

(def input (->> "advent_2018/day_01/input" io/resource io/reader line-seq))

(def data (map read-string input))

(defn part-1 []
  (reduce + data))

(defn first-duplicate [xs]
  (let [result (reduce (fn [seen x]
                         (if (seen x)
                           (reduced x)
                           (conj seen x)))
                 #{} xs)]
    (if (set? result)
      nil
      result)))

(defn part-2 []
  (let [freqs (cons 0 (reductions + (cycle data)))]
    (first-duplicate freqs)))
