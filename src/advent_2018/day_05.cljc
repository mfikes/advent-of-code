(ns advent-2018.day-05
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input (-> "advent_2018/day_05/input" io/resource slurp))

(defn fixed-point-by [g f x]
  (reduce #(if (= (g %1) (g %2))
             (reduced %1)
             %2)
    (iterate f x)))

(defn lower-case [unit]
  #?(:clj  (Character/toLowerCase ^Character unit)
     :cljs (string/lower-case unit)))

(defn reacts? [x y]
  (and (not= x y)
       (= (lower-case x) (lower-case y))))

(defn react [polymer]
  (lazy-seq
    (let [[x y & more] polymer]
      (cond
        (nil? y)      [x]
        (reacts? x y) (react more)
        :else         (cons x (react (rest polymer)))))))

(defn fully-react [polymer]
  (fixed-point-by count react polymer))

(defn part-1 []
  (count (fully-react input)))

(defn remove-units [x polymer]
  (remove (fn [y]
            (or (= x y)
                (reacts? x y)))
    polymer))

(defn part-2 []
  (->> (into #{} (map lower-case input))
    (map #(count (fully-react (remove-units % input))))
    (apply min)))
