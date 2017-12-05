(ns advent-2017.day-01
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(let [c->d (zipmap "0123456789" (range))]
  (defn str->digits
    [s]
    (map c->d s)))

(def input (-> "advent_2017/day_01/input"
             io/resource
             slurp
             str/trim
             str->digits))

(defn matches
  [xs ys]
  (->>
    (map vector xs ys)
    (filter (partial apply =))
    (map first)))

(defn solve
  [pair-up]
  (apply + (matches input (pair-up input))))

(defn part-1 []
  (solve #(rest (cycle %))))

(defn part-2 []
  (solve #(nthrest (cycle %) (/ (count %) 2))))
