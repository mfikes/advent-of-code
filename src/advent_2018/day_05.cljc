(ns advent-2018.day-05
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input (-> "advent_2018/day_05/input" io/resource slurp))

(defn lower-case [unit]
  #?(:clj  (Character/toLowerCase ^Character unit)
     :cljs (string/lower-case unit)))

(defn reacts? [x y]
  (and (not= x y)
       (= (lower-case x) (lower-case y))))

(defn add-unit [polymer unit]
  (if (some-> (peek polymer) (reacts? unit))
    (pop polymer)
    (conj polymer unit)))

(defn react [polymer]
  (reduce add-unit [] polymer))

(defn part-1 []
  (count (react input)))

(defn remove-units [x polymer]
  (remove (fn [y]
            (or (= x y)
                (reacts? x y)))
    polymer))

(defn part-2 []
  (->> (into #{} (map lower-case input))
    (map #(count (react (remove-units % input))))
    (apply min)))
