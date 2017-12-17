(ns advent-2017.day-16
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_16/input" io/resource slurp str/trim))

(defn parse-dance-move [s]
  (if (= "s" (subs s 0 1))
    (let [arg1 (read-string (subs s 1))]
      (fn [xs] (let [c (count xs)]
                 (vec (take c (drop (- c arg1) (cycle xs)))))))
    (let [slash-idx (str/index-of s \/)
          arg1      (read-string (subs s 1 slash-idx))
          arg2      (read-string (subs s (inc slash-idx)))
          swap      (fn [xs idx1 idx2] (assoc xs idx1 (xs idx2) idx2 (xs idx1)))]
      (case (subs s 0 1)
        "x" (fn [xs] (swap xs arg1 arg2))
        "p" (fn [xs] (swap xs (.indexOf xs arg1) (.indexOf xs arg2)))))))

(def dance (->> (str/split input #",") (map parse-dance-move) reverse (apply comp)))

(def starting-position (mapv (comp symbol str) "abcdefghijklmnop"))

(defn part-1 []
  (apply str (dance starting-position)))

(defn part-2 []
  (let [final-positions (iterate dance starting-position)
        period          (inc (count (take-while (complement #{starting-position}) (rest final-positions))))]
    (apply str (nth final-positions (rem 1000000000 period)))))
