(ns advent-2017.day-13
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (->> "advent_2017/day_13/input" io/resource io/reader line-seq))

(def data (->> input (map #(mapv read-string (re-seq #"\d+" %)))))

(defn caught? [depth range]
  (zero? (mod depth (* 2 (dec range)))))

(defn part-1 []
  (transduce (keep (fn [[depth range]]
                     (when (caught? depth range)
                       (* depth range))))
    + data))

(defn part-2 []
  (some (fn [delay]
          (when (not-any? (fn [[depth range]]
                            (caught? (+ depth delay) range))
                  data)
            delay))
    (range)))
