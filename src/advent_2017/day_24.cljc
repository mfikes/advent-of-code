(ns advent-2017.day-24
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_24/input" io/resource slurp))

(def data (->> input str/split-lines (map #(re-seq #"\d+" %)) (map #(mapv read-string %))))

(defn optimum [base-total pins components component-value]
  (let [matches (filter #(some #{pins} %) components)]
    (if (empty? matches)
      base-total
      (apply max (map (fn [[a b :as match]]
                        (optimum (apply + base-total component-value match)
                          (if (= pins a) b a)
                          (remove #{match} components)
                          component-value))
                   matches)))))

(defn part-1 []
  (optimum 0 0 data 0))

(defn part-2 []
  (rem (optimum 0 0 data 10000) 10000))
