(ns advent-2017.day-07
  (:require
   #?(:cljs [planck.core :refer [line-seq read]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]
   [clojure.spec.alpha :as s]
   [clojure.set :as set])
  #?(:clj
     (:import
      (java.io PushbackReader))))

(def input (->> "advent_2017/day_07/input" io/resource io/reader #?(:clj PushbackReader.)))

(s/def ::programs (s/* (s/alt ::solo (s/cat :name symbol? :weight list?)
                              ::deps (s/cat :name symbol? :weight list? ::arrow #{'->} :held (s/* symbol?)))))

(def data (->> (repeatedly #(read {:eof nil} input))
            (take-while some?)
            (s/conform ::programs)
            (map second)
            (map #(update % :weight first))))

(defn part-1 []
  (first (set/difference (into #{} (map :name data)) (into #{} (flatten (keep :held data))))))

(let [index (set/index data [:name])]
  (defn lookup [name]
    (first (index {:name name}))))

(def weight (memoize (fn weight [name]
                       (let [program (lookup name)]
                         (apply + (:weight program) (map weight (:held program)))))))

(defn part-2 []
  (some (fn [program]
          (when-let [held (:held program)]
            (let [weights (map weight held)]
              (when-not (apply == weights)
                (let [unique-weight ((set/map-invert (frequencies weights)) 1)]
                  (+ (:weight (lookup (nth held (.indexOf weights unique-weight))))
                     (- (apply min weights) (apply max weights))))))))
    data))
