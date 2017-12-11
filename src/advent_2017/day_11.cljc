(ns advent-2017.day-11
  (:require
   #?(:cljs [planck.core :refer [read]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io])
  #?(:clj (:import (java.io PushbackReader))))

(def input (->> "advent_2017/day_11/input" io/resource io/reader #?(:clj PushbackReader.)))

(def data (->> (repeatedly #(read {:eof nil} input)) (take-while some?)))

(defn move [[x y z] dir]
  (case dir
    n  [     x  (inc y) (dec z)]
    ne [(inc x)      y  (dec z)]
    se [(inc x) (dec y)      z]
    s  [     x  (dec y) (inc z)]
    sw [(dec x)      y  (inc z)]
    nw [(dec x) (inc y)      z]))

(defn dist [coords]
  (/ (apply + (map #(Math/abs ^long %) coords)) 2))

(defn part-1 []
  (dist (reduce move [0 0 0] data)))

(defn part-2 []
  (apply max (map dist (reductions move [0 0 0] data))))
