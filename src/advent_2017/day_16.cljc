(ns advent-2017.day-16
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_16/input" io/resource slurp str/trim))

(defn parse-dance-move [s]
  (merge
    {::kind (case (subs s 0 1)
              "s" :spin
              "x" :exchange
              "p" :partner)}
    (if-let [slash-idx (str/index-of s \/)]
      {::arg1 (read-string (subs s 1 slash-idx))
       ::arg2 (read-string (subs s (inc slash-idx)))}
      {::arg1 (read-string (subs s 1))})))

(def data (->> (str/split input #",") (map parse-dance-move)))

(defmulti apply-dance-move (fn [xs dance-move] (::kind dance-move)))

(defmethod apply-dance-move :spin [xs {:keys [::arg1]}]
  (let [c (count xs)]
    (vec (take c (drop (- c arg1) (cycle xs))))))

(defn swap [xs idx1 idx2]
  (assoc xs
    idx1 (xs idx2)
    idx2 (xs idx1)))

(defmethod apply-dance-move :exchange [xs {:keys [::arg1 ::arg2]}]
  (swap xs arg1 arg2))

(defmethod apply-dance-move :partner [xs {:keys [::arg1 ::arg2]}]
  (swap xs (.indexOf xs arg1) (.indexOf xs arg2)))

(def starting-position (mapv (comp symbol str) "abcdefghijklmnop"))

(defn part-1 []
  (apply str (reduce apply-dance-move starting-position data)))

(defn part-2 []
  (let [final-positions (iterate #(reduce apply-dance-move % data) starting-position)
        period          (loop [final-positions (rest final-positions) counter 1]
                          (if (= starting-position (first final-positions))
                            counter
                            (recur (rest final-positions) (inc counter))))]
    (apply str (nth final-positions (rem 1000000000 period)))))
