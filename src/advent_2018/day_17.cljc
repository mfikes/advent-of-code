(ns advent-2018.day-17
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input (-> "advent_2018/day_17/input" io/resource slurp))

(defn parse-vein [s]
  (let [[a b1 b2] (map read-string (re-seq #"\d+" s))]
    (map (if (= (first s) \x)
           #(vector a %)
           #(vector % a))
      (range b1 (inc b2)))))

(defn flow [clay water max-y coords]
  (let [down   (fn [[x y]] [x (inc y)])
        left   (fn [[x y]] [(dec x) y])
        right  (fn [[x y]] [(inc x) y])
        sand?  (fn [coords]
                 (and (not (clay coords))
                      (not (water coords))))
        holds? (fn holds? [dir coords]
                 (and (or (clay (down coords))
                          (water (down coords)))
                      (or (clay (dir coords))
                          (holds? dir (dir coords)))))
        fill   (fn fill [water dir coords]
                 (-> (cond-> water
                       (not (clay (dir coords)))
                       (fill dir (dir coords)))
                   (assoc coords \~)))
        spill  (fn [water dir coords]
                 (if (and (sand? (dir coords))
                          (or (clay (down coords))
                              (= \~ (water (down coords)))))
                   (flow clay water max-y (dir coords))
                   water))
        water  (assoc water coords \|)]
    (cond
      (= (second coords) max-y)
      water

      (sand? (down coords))
      (let [water (flow clay water max-y (down coords))]
        (recur clay water max-y coords))

      (and (holds? left coords)
           (holds? right coords))
      (-> water
        (fill left coords)
        (fill right coords))

      :else
      (-> water
        (spill right coords)
        (spill left coords)))))

(defn final-water [input]
  (let [clay  (into #{} (mapcat parse-vein (string/split-lines input)))
        min-y (apply min (map second clay))
        max-y (apply max (map second clay))]
    (flow clay {} max-y [500 min-y])))

(defn part-1 []
  (count (final-water input)))

(defn part-2 []
  (count (filter (fn [[_ y]] (= y \~)) (final-water input))))
