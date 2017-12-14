(ns advent-2017.day-14
  (:refer-clojure :exclude [find])
  (:require
   [advent-2017.day-10 :refer [knot-hash-decimal]]
   [advent-2017.day-12 :refer [union find]]))

(def input "ljoxqyyw")

(defn knot-hash-matrix [input]
  (mapv knot-hash-decimal (map #(str input "-" %) (range 128))))

(defn part-1 []
  (apply + (map #?(:clj #(Long/bitCount %) :cljs bit-count) (flatten (knot-hash-matrix input)))))

(defn part-2 []
  (let [matrix     (knot-hash-matrix input)
        bit-set?   (fn [[row col]]
                     (bit-test ((matrix row) (quot col 8)) (- 7 (mod col 8))))
        linear-ndx (fn [[row col]]
                     (+ (* row 128) col))
        union-find (reduce union
                     {}
                     (for [coord (for [row (range 128)
                                       col (range 128)]
                                   [row col]) :when (bit-set? coord)
                           neigh (for [offset [[0 0] [-1 0] [1 0] [0 -1] [0 1]]]
                                   (map + coord offset)) :when (and (every? #(<= 0 % 127) neigh)
                                                                    (bit-set? neigh))]
                       [(linear-ndx coord) (linear-ndx neigh)]))]
    (count (distinct (map #(second (find union-find %)) (keys union-find))))))
