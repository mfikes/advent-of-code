(ns advent-2018.day-03
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]))

(def input-lines (->> "advent_2018/day_03/input" io/resource io/reader line-seq))

(defn read-claim [s]
  (->> (rest (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" s))
    (map read-string)
    (zipmap [:id :left :top :width :height])))

(def claims (map read-claim input-lines))

(defn locs [claim]
  (for [row (range (:top claim) (+ (:top claim) (:height claim)))
        col (range (:left claim) (+ (:left claim) (:width claim)))]
    [row col]))

(defn add-claim [fabric claim]
  (reduce
    (fn [fabric loc]
      (update fabric loc (fnil inc 0)))
    fabric
    (locs claim)))

(defn part-1 []
  (let [fabric-counts (reduce add-claim {} claims)
        overlapping-counts (filter #(> (val %) 1) fabric-counts)]
    (count overlapping-counts)))
