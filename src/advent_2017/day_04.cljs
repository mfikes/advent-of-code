(ns advent-2017.day-04
  (:require
   [planck.core :refer [slurp]]
   [planck.io :as io]
   [clojure.string :as str]))

(def input (str/split-lines (slurp (io/resource "advent_2017/day_04/input"))))

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
