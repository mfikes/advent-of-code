(ns advent-2017.day-05
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (->> "advent_2017/day_05/input" io/resource io/reader line-seq))

(def data (map read-string input))

(defn solve [maze update-fn]
  (reduce (fn [[maze ndx] counter]
            (if-let [offset (get maze ndx)]
              [(update maze ndx update-fn) (+ ndx offset)]
              (reduced counter)))
    [(vec maze) 0]
    (range)))

(defn part-1 []
  (solve data inc))

(defn part-2 []
  (solve data (fn [v]
                 (if (<= 3 v)
                   (dec v)
                   (inc v)))))
