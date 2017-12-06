(ns advent-2017.day-06
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_06/input" io/resource slurp))

(def data (as-> input x (str/trim x) (str/split x #"\t") (mapv read-string x)))

;; like (apply max-key [3 1 2 3] (range 4)), but reversing things
(defn max-ndx [v]
  (- (dec (count v))
     (apply max-key (vec (rseq v)) (range (count v)))))

(defn redistribute [banks]
  (let [max-ndx     (max-ndx banks)
        target-ndxs (map #(mod (+ max-ndx 1 %) (count banks))
                      (range (banks max-ndx)))]
    (merge-with + (assoc banks max-ndx 0) (frequencies target-ndxs))))

(defn solve [banks]
  (reduce (fn [[last-seen banks] steps]
            (if (last-seen banks)
              (reduced [steps (last-seen banks)])
              [(assoc last-seen banks steps) (redistribute banks)]))
    [{} banks]
    (range)))

(defn part-1 []
  (first (solve data)))

(defn part-2 []
  (apply - (solve data)))
