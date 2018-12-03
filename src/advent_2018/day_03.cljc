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
  (for [row (range (:top claim)  (+ (:top claim)  (:height claim)))
        col (range (:left claim) (+ (:left claim) (:width claim)))]
    [row col]))

(defn add-claim [fabric claim]
  (reduce
    (fn [fabric loc]
      (update fabric loc conj (:id claim)))
    fabric
    (locs claim)))

(defn loc->claims-ids [claims]
  (reduce add-claim {} claims))

(defn overlapping? [loc-claims]
  (> (count loc-claims) 1))

(defn overlapping-loc-claims [claims]
  (filter overlapping? (vals (loc->claims-ids claims))))

(defn part-1 []
  (count (overlapping-loc-claims claims)))

(defn part-2 []
  (let [overlapping-claim-ids (reduce into #{} (overlapping-loc-claims claims))
        all-claim-ids         (map :id claims)]
    (first (remove overlapping-claim-ids all-claim-ids))))
