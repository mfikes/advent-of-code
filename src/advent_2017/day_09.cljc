(ns advent-2017.day-09
  (:require
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]))

(def input (->> "advent_2017/day_09/input" io/resource slurp str/trim))

(defn solve [stream]
  (reduce (fn [acc ch]
            (case (:state acc)
              :cancel
              (assoc acc :state :garbage)

              :garbage
              (case ch
                \> (assoc acc :state :group)
                \! (assoc acc :state :cancel)
                (update acc :count inc))

              :group
              (case ch
                \< (assoc acc :state :garbage)
                \{ (update acc :level inc)
                \} (-> acc
                     (update :level dec)
                     (update :score + (:level acc)))
                acc)

              (assoc acc :state :group)))
    {:level 1 :score 0 :count 0}
    stream))

(defn part-1 []
  (:score (solve input)))

(defn part-2 []
  (:count (solve input)))
