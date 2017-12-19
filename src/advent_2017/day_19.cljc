(ns advent-2017.day-19
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_19/input" io/resource slurp))

(def matrix (mapv vec (str/split-lines input)))

(defn move [state]
  (apply update-in state
    (case (:dir state)
      :down  [[:loc 0] inc]
      :up    [[:loc 0] dec]
      :left  [[:loc 1] dec]
      :right [[:loc 1] inc])))

(defn update-dir [state]
  (let [adjacent (fn [idx] (get-in matrix (update (:loc state) idx dec)))]
    (cond-> state (= (get-in matrix (:loc state)) \+)
      (assoc :dir (case (:dir state)
                    (:down :up)    (if (#{\space \|} (adjacent 1)) :right :left)
                    (:right :left) (if (#{\space \-} (adjacent 0)) :down :up))))))

(def path (eduction
            (map :loc)
            (take-while #(not= \space (get-in matrix %)))
            (iterate (comp update-dir move)
              {:dir :down :loc [0 (.indexOf (matrix 0) \|)]})))

(defn part-1 []
  (let [letters (set "ABCDEFGHIJKLMNOPQRSTUVWXYZ")]
    (transduce (keep #(letters (get-in matrix %))) str path)))

(defn part-2 []
  (transduce identity (completing (fn [c _] (inc c))) 0 path))
