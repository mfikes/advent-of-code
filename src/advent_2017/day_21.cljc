(ns advent-2017.day-21
  (:require
   [advent.util :as util]
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_21/input" io/resource slurp))

(defn dihedral [pattern]
  (let [r #(apply map (comp reverse vector) %)
        s #(map reverse %)
        rotations (take 4 (iterate r (map seq pattern)))]
    (concat rotations (map s rotations))))

(defn make-pattern-map [input]
  (transduce (comp (mapcat #(str/split % #" => "))
                   (map #(str/split % #"/"))
                   (partition-all 2)
                   (map (fn [[input output]]
                          (zipmap (dihedral input) (repeat 8 (map seq output))))))
    merge
    (str/split-lines input)))

(defn split-xf [size]
  (comp (map #(partition size %))
        (partition-all size)
        (map #(apply map vector %))))

(def join-xf
  (comp (mapcat #(apply map vector %))
        (map flatten)))

(defn step* [pattern-map input]
  (let [size (if (zero? (rem (count (first input)) 2))
               2
               3)]
    (into [] (comp (split-xf size) (map #(map pattern-map %)) join-xf) input)))

(defn solve [iterations]
  (let [step (partial step* (make-pattern-map input))]
    (->> iterations
      (util/nth (iterate step [".#." "..#" "###"]))
      flatten
      (filter #{\#})
      count)))

(defn part-1 []
  (solve 5))

(defn part-2 []
  (solve 18))
