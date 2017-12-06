(ns advent-2017.day-06
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_06/input" io/resource slurp))

(def data (as-> input x (str/trim x) (str/split x #"\t") (mapv read-string x)))

(defn redistribute [banks]
  (let [max-ndx     (.indexOf banks (apply max banks))
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
