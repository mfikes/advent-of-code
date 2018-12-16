(ns advent-2018.day-04
  (:require
   [advent.util :refer [map-vals]]
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]))

(def input-lines (->> "advent_2018/day_04/input" io/resource io/reader line-seq))

(defn parse-int [s]
  #?(:clj (Integer/parseInt s) :cljs (js/parseInt s)))

(defn mode [coll]
  (apply max-key val (frequencies coll)))

(defn nap-log [input-lines]
  (:log (reduce (fn [acc input-line]
                  (let [minute (parse-int (subs input-line 15 17))]
                    (case (subs input-line 19 24)
                      "Guard" (assoc acc :guard-id (read-string (subs input-line 26)))
                      "falls" (assoc acc :start minute)
                      "wakes" (update acc :log conj (-> (select-keys acc [:guard-id :start])
                                                        (assoc :end minute))))))
          {:log []} (sort input-lines))))

(defn add-minutes [guard-id->minutes log-entry]
  (update guard-id->minutes (:guard-id log-entry)
    into (range (:start log-entry) (:end log-entry))))

(defn part-1 []
  (let [guard-id->minutes  (->> input-lines nap-log (reduce add-minutes {}))
        [guard-id minutes] (apply max-key (comp count val) guard-id->minutes)
        [minute]           (mode minutes)]
    (* guard-id minute)))

(defn part-2 []
  (let [guard-id->minutes   (->> input-lines nap-log (reduce add-minutes {}))
        guard-id->mode      (map-vals mode guard-id->minutes)
        [guard-id [minute]] (apply max-key (comp val val) guard-id->mode)]
    (* guard-id minute)))
