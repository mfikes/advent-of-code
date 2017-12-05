(ns advent-2017.day-04
  (:require
   #?(:cljs [planck.core :refer [line-seq]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (->> "advent_2017/day_04/input"
             io/resource
             io/reader
             line-seq))

(defn valid-passphrase?
  [normalize passphrase]
  (->> (str/split passphrase #" ")
    (map normalize)
    (apply distinct?)))

(defn solve
  [normalize]
  (->> input
    (filter (partial valid-passphrase? normalize))
    count))

(def part-1 (solve identity))

(def part-2 (solve sort))
