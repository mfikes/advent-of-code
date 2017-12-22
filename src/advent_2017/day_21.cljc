(ns advent-2017.day-21
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_21/input" io/resource slurp))

(def transpose (partial apply map vector))

(defn dihedral [pattern]
  (let [rotations (take 4 (iterate (comp reverse transpose) (map seq pattern)))]
    (concat rotations (map reverse rotations))))

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
        (map transpose)))

(def join-xf (comp (mapcat transpose)
                   (map flatten)))

(defn step* [pattern-map input]
  (let [size (if (even? (count (first input)))
               2
               3)]
    (eduction (split-xf size)
              (map #(map pattern-map %))
              join-xf
              input)))

(defn solve [iterations]
  (let [step (partial step* (make-pattern-map input))]
    (->> iterations
      (nth (iterate step [".#." "..#" "###"]))
      (transduce (comp (map #(filter #{\#} %))
                       (map count))
        +))))

(defn part-1 []
  (solve 5))

(defn part-2 []
  (solve 18))
