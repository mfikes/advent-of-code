(ns advent-2017.day-22
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_22/input" io/resource slurp))

(defn make-initial-state [input]
  (let [infected-locs (->> input
                        str/split-lines
                        (map-indexed (fn [idx line]
                                       (map vector
                                         (keep-indexed #(when (= \# %2) %1) line)
                                         (repeat idx))))
                        (apply concat))]
    {:loc            [12 12]
     :dir            [0  -1]
     :node-states    (zipmap infected-locs (repeat :infected))
     :infected-count 0}))

(defn burst* [update-node-state {:keys [node-states loc dir] :as state}]
  (let [node-state     (node-states loc :clean)
        new-node-state (update-node-state node-state)
        new-dir        (let [[dx dy] dir]
                         (case node-state
                           :clean    [   dy  (- dx)]        ; turn left
                           :weakened [   dx     dy ]        ; unchanged
                           :infected [(- dy)    dx ]        ; turn right
                           :flagged  [(- dx) (- dy)]        ; reverse
                           ))]
    (-> state
      (assoc :dir new-dir
             :loc (mapv + loc new-dir))
      (update :node-states assoc loc new-node-state)
      (cond-> (= :infected new-node-state) (update :infected-count inc)))))

(defn nth' [coll n]
  (transduce (drop n) (completing #(reduced %2)) nil coll))

(defn solve [node-state-cycle initial-state iterations]
  (let [burst (partial burst* (zipmap node-state-cycle (rest (cycle node-state-cycle))))]
    (-> (iterate burst initial-state)
      (nth' iterations)
      :infected-count)))

(defn part-1 []
  (solve [:clean :infected]
    (make-initial-state input) 10000))

(defn part-2 []
  (solve [:clean :weakened :infected :flagged]
    (make-initial-state input) 10000000))
