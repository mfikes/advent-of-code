(ns advent-2018.day-11
  (:require
   [clojure.string :as string]))

(def input 8868)

(defn max-by [f xs]
  (let [[ndx max] (reduce-kv (fn [[ndx max] k v]
                               (if (> (f v) (f max))
                                 [k v]
                                 [ndx max]))
                    [-1 (nth xs 0)]
                    (vec (rest xs)))]
    [(+ ndx 2) max]))

(defn hundreds-digit [x]
  (mod (quot x 100) 10))

(defn power-level [serial-number x y]
  (let [rack-id (+ x 10)]
    (-> rack-id
      (* y)
      (+ serial-number)
      (* rack-id)
      hundreds-digit
      (- 5))))

(defn power-levels [serial-number]
  (for [y (range 1 301)]
    (for [x (range 1 301)]
      (power-level serial-number x y))))

(defn solve [power-levels square-size]
  (let [[y [x m]] (->> power-levels
                    (partition square-size 1)
                    (map #(->> %
                            (apply map +)
                            (partition square-size 1)
                            (map (partial apply +))
                            (max-by identity)))
                    (max-by second))]
    [m x y]))

(defn part-1 []
  (string/join "," (rest (solve (power-levels input) 3))))

(def pmap' #?(:clj pmap :cljs map))

(defn part-2 []
  (let [[ndx [_ x y]] (->> (range 1 301)
                        (pmap' (partial solve (power-levels input)))
                        (max-by first))]
    (string/join "," [x y ndx])))
