(ns advent-2017.day-12
  (:refer-clojure :exclude [find])
  (:require
   #?(:cljs [planck.core :refer [read]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.spec.alpha :as s])
  #?(:clj (:import (java.io PushbackReader))))

(def input (->> "advent_2017/day_12/input" io/resource io/reader #?(:clj PushbackReader.)))

(s/def ::line (s/cat :node int? :conn '#{<->} :nodes (s/* int?)))

(s/check-asserts true)
(def data (->> (repeatedly #(read {:eof nil} input))
            (take-while some?)
            (s/assert (s/* ::line))
            (s/conform (s/* ::line))))

(defn find [uf x]
  (let [parent (get-in uf [x :parent])]
    (cond
      (nil? parent) [(assoc-in uf [x :parent] x) x]
      (== x parent) [uf parent]
      :else (let [[uf root] (find uf parent)]
              [(assoc-in uf [x :parent] root) root]))))

(defn union [uf x y]
  (let [[uf x-root] (find uf x)
        [uf y-root] (find uf y)]
    (if (== x-root y-root)
      uf
      (let [cmp (compare (get-in uf [x-root :rank] 0) (get-in uf [y-root :rank] 0))]
        (if (neg? cmp)
          (assoc-in uf [x-root :parent] y-root)
          (cond-> (assoc-in uf [y-root :parent] x-root)
            (zero? cmp)
            (update-in [x-root :rank] (fnil inc 0))))))))

(def uf (reduce (fn [uf {:keys [node nodes]}]
                  (reduce #(union %1 node %2) uf nodes))
          {}
          data))

(def root (partial (comp second find) uf))

(defn part-1 []
  (count (filter #(== (root 0) (root %)) (keys uf))))

(defn part-2 []
  (count (distinct (map root (keys uf)))))
