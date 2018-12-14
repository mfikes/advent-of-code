(ns advent-2018.day-13
  (:require
   [advent.util :refer [some']]
   #?(:cljs [planck.core :refer [slurp]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input (-> "advent_2018/day_13/input" io/resource slurp))

(def tracks #{\/ \\ \+})

(def dirs #{\v \^ \< \>})

(defn parse-lines [kind lines]
  (into {} (mapcat (fn [y line]
                     (keep-indexed (fn [x c]
                                     (when (kind c)
                                       [[x y] c]))
                       line))
             (range) lines)))

(def turns (cycle [:left :straight :right]))

(defn init-carts [loc->dir]
  (reduce-kv (fn [m loc dir]
               (assoc m loc [dir turns]))
    (sorted-map)
    loc->dir))

(defn update-loc [[x y] dir]
  (case dir
    \< [(dec x) y]
    \> [(inc x) y]
    \^ [x (dec y)]
    \v [x (inc y)]))

(defn move-cart [[_ [dir] :as cart]]
  (update cart 0 update-loc dir))

(defn rotate [dir turn]
  (case [dir turn]
    [\< :left] \v
    [\v :left] \>
    [\> :left] \^
    [\^ :left] \<
    [\< :right] \^
    [\v :right] \<
    [\> :right] \v
    [\^ :right] \>
    dir))

(defn update-dir+turns [[dir [turn & more :as turns]] track]
  (case [dir track]
    ([\> \\] [\< \/]) [\v turns]
    ([\> \/] [\< \\]) [\^ turns]
    ([\^ \\] [\v \/]) [\< turns]
    ([\^ \/] [\v \\]) [\> turns]
    ([\> \+] [\< \+] [\^ \+] [\v \+]) [(rotate dir turn) more]
    [dir turns]))

(defn turn-cart [loc->track [loc :as cart]]
  (update cart 1 update-dir+turns (loc->track loc)))

(defn update-carts-1 [loc->track carts]
  (reduce (fn [carts [old-loc :as cart]]
            (let [[new-loc :as cart] (turn-cart loc->track (move-cart cart))]
              (if (contains? carts new-loc)
                (reduced new-loc)
                (-> carts
                  (conj cart)
                  (dissoc old-loc)))))
    carts
    carts))

(defn solve [extract some-pred advance]
  (let [loc->track (parse-lines tracks (string/split-lines input))
        carts      (init-carts (parse-lines dirs (string/split-lines input)))]
    (string/join "," (extract (some' some-pred (iterate (partial advance loc->track) carts))))))

(defn part-1 []
  (solve identity #(when (vector? %) %) update-carts-1))

(defn update-carts-2 [loc->track carts]
  (second (reduce (fn [[removed carts] [old-loc :as cart]]
                    (let [[new-loc :as cart] (turn-cart loc->track (move-cart cart))]
                      (cond
                        (removed old-loc)
                        [removed carts]

                        (contains? carts new-loc)
                        [(conj removed new-loc)
                         (-> carts
                           (dissoc new-loc)
                           (dissoc old-loc))]

                        :else
                        [removed
                         (-> carts
                           (conj cart)
                           (dissoc old-loc))])))
            [#{} carts]
            carts)))

(defn part-2 []
  (solve ffirst #(when (= 1 (count %)) %) update-carts-2))
