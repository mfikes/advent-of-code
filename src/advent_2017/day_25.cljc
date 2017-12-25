(ns advent-2017.day-25
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (-> "advent_2017/day_25/input" io/resource slurp))

(def header-re #"Begin in state ([A-Z]).\nPerform a diagnostic checksum after (\d+) steps.")
(def instruction-re #"In state ([A-Z]):\n  If the current value is 0:\n    - Write the value (\d).\n    - Move one slot to the (right|left).\n    - Continue with state ([A-Z]).\n  If the current value is 1:\n    - Write the value (\d).\n    - Move one slot to the (right|left).\n    - Continue with state ([A-Z]).")

(defn parse-header [input]
  (let [[_ state iterations] (first (re-seq header-re input))]
    [{:state    (keyword state)
      :loc      0
      :one-locs #{}}
     (read-string iterations)]))

(defn parse-program [input]
  (into {} (for [[_ state
                  write-0 move-0 continue-0
                  write-1 move-1 continue-1] (re-seq instruction-re input)]
             [(keyword state) {0 {:write    (read-string write-0)
                                  :move     (keyword move-0)
                                  :continue (keyword continue-0)}
                               1 {:write    (read-string write-1)
                                  :move     (keyword move-1)
                                  :continue (keyword continue-1)}}])))

(defn step* [program state]
  (let [current-val (if (contains? (:one-locs state) (:loc state)) 1 0)
        {:keys [write move continue]} (get-in program [(:state state) current-val])]
    (-> state
      (update :one-locs (case write 1 conj 0 disj) (:loc state))
      (update :loc (case move :left dec :right inc))
      (assoc :state continue))))

(defn nth' [coll n]
  (transduce (drop n) (completing #(reduced %2)) nil coll))

(defn part-1 []
  (let [step (partial step* (parse-program input))
        [state iterations] (parse-header input)]
    (count (:one-locs (nth' (iterate step state) iterations)))))
