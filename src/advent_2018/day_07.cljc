(ns advent-2018.day-07
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input (-> "advent_2018/day_07/input" io/resource slurp))

(defn parse-line [s]
  [(nth s 36) (nth s 5)])

(defn input->dep-map [input]
  (->> (string/split-lines input)
    (map parse-line)
    (reduce (fn [m [k v]]
              (update m k (fnil conj #{}) v))
      {})))

(defn eliminate [dep-map completed]
  (reduce-kv (fn [m k v]
               (if-some [v' (not-empty (reduce disj v completed))]
                 (assoc m k v')
                 m))
    {}
    dep-map))

(defn decrement [remaining in-process]
  (reduce-kv (fn [m k v]
               (if (some #{k} in-process)
                 (if (== 1 v)
                   m
                   (assoc m k (dec v)))
                 (assoc m k v)))
    {}
    remaining))

(defn solve [workers f init]
  (loop [acc       init
         dep-map   (input->dep-map input)
         remaining (zipmap "ABCDEFGHIJKLMNOPQRSTUVWXYZ" (iterate inc 61))]
    (if (seq remaining)
      (let [available  (reduce disj (set (keys remaining)) (keys dep-map))
            in-process (take workers (sort available))]
        (let [completed (filter #(== 1 (remaining %)) in-process)]
          (recur
            (f acc completed)
            (eliminate dep-map completed)
            (decrement remaining in-process))))
      acc)))

(defn part-1 []
  (solve 1 (partial apply str) ""))

(defn part-2 []
  (solve 5 (fn [seconds _] (inc seconds)) 0))
