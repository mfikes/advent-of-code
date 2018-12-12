(ns advent-2018.day-12
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input (-> "advent_2018/day_12/input" io/resource slurp))

(defn parse-note [note]
  (when (= "#" (subs note 9))
    (map #{\#} (subs note 0 5))))

(defn parse-state [state]
  (into (sorted-set) (keep-indexed (fn [idx pot]
                                     (when (= \# pot) idx))
                       state)))

(defn parse-input [input]
  (let [[initial-state-line _ & notes] (string/split-lines input)]
    [(parse-state (subs initial-state-line 15))
     (into #{} (keep parse-note notes))]))

(defn generation [notes state]
  (reduce (fn [state' idxs]
            (let [grow? (notes (map #(when (state %) \#) idxs))]
              (cond-> state' grow? (conj (nth idxs 2)))))
    (sorted-set)
    (partition 5 1
      (range (- (first state) 5) (inc (+ (first (rseq state)) 5))))))

(defn states [input]
  (let [[state notes] (parse-input input)]
    (iterate (partial generation notes) state)))

(defn solve [states n]
  (reduce + (nth states n)))

(defn solve-extrapolating [states n]
  (let [shifted? #(apply = (apply map - %))
        idx      (first (keep-indexed #(when (shifted? %2) %1)
                          (map vector states (rest states))))
        base     (solve states idx)
        next     (solve states (inc idx))]
    (+ base (* (- next base) (- n idx)))))

(defn part-1 []
  (solve (states input) 20))

(defn part-2 []
  (solve-extrapolating (states input) 50000000000))
