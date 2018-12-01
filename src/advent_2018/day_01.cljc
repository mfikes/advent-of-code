(ns advent-2018.day-01
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]))

(def input (->> "advent_2018/day_01/input" io/resource io/reader line-seq))

(def data (map read-string input))

(defn part-1 []
  (reduce + data))

(defn part-2 []
  (let [freqs (reductions + (cycle data))]
    (reduce (fn [seen freq]
              (if (seen freq)
                (reduced freq)
                (conj seen freq)))
      #{}
      freqs)))
