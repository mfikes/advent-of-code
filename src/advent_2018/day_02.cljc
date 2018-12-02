(ns advent-2018.day-02
  (:require
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [advent-2018.day-01 :as day-01]))

(def input (->> "advent_2018/day_02/input" io/resource io/reader line-seq))

(defn count-with-exact [box-ids n]
  (->> box-ids
    (map frequencies)
    (map vals)
    (keep #(some #{n} %))
    count))

(defn part-1 []
  (* (count-with-exact input 2) (count-with-exact input 3)))

(defn delete [idx s]
  (str (subs s 0 idx) (subs s (inc idx))))

(defn part-2 []
  (some (fn [idx]
          (->> input
            (map (partial delete idx))
            day-01/first-duplicate))
    (range)))
