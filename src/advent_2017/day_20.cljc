(ns advent-2017.day-20
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (->> "advent_2017/day_20/input" io/resource io/reader line-seq))

(def data (->> input
            (map #(re-seq #"-?\d+" %))
            (map #(mapv read-string %))))

(def indexed (map conj data (range)))

(defn step [particle]
  (let [[p v a idx] (partition-all 3 particle)
        v (map + v a)
        p (map + p v)]
    (concat p v a idx)))

(defn dist [particle]
  (->> particle
    (take 3)
    (map #(Math/abs ^long %))
    (apply +)))

(def closest-particle-idx (eduction (map #(sort-by dist %)) (map first) (map last)
                            (iterate #(map step %) indexed)))

(defn part-1 []
  (take 500 closest-particle-idx))                          ; eyeball result and guess

(defn remove-colliding [particles]
  (->> particles
    (group-by #(take 3 %))
    vals
    (filter #(== 1 (count %)))
    (map first)))

(def particle-count (eduction (map count)
                      (iterate (comp remove-colliding #(map step %)) indexed)))

(defn part-2 []
  (take 500 particle-count))                                ; eyeball result and guess
