(ns advent-2018.day-14
  (:require
   [clojure.string :as string]))

(def digits
  (merge
    (zipmap (range 10) (map vector (range 10)))
    (zipmap (map #(+ % 10) (range 9)) (map #(vector 1 %) (range 9)))))

(defn solve-1 [n]
  (loop [idx-1 0 idx-2 1 recipes [3 7]]
    (let [recipe-1 (recipes idx-1)
          recipe-2 (recipes idx-2)
          sum      (+ recipe-1 recipe-2)
          recipes  (apply conj recipes (digits sum))
          c        (count recipes)]
      (if (> c (+ n 10))
        (string/join (subvec recipes n (+ n 10)))
        (recur (mod (+ idx-1 recipe-1 1) c) (mod (+ idx-2 recipe-2 1) c) recipes)))))

(defn part-1 []
  (solve-1 825401))

(defn solve-2 [desired]
  (loop [idx-1 0 idx-2 1 recipes [3 7]]
    (let [recipe-1 (recipes idx-1)
          recipe-2 (recipes idx-2)
          sum      (+ recipe-1 recipe-2)
          recipes  (apply conj recipes (digits sum))
          c        (count recipes)]
      (cond
        (< c 10)
        (recur (mod (+ idx-1 recipe-1 1) c) (mod (+ idx-2 recipe-2 1) c) recipes)

        (= desired (subvec recipes (- c (count desired) 1) (dec c)))
        (- c (count desired) 1)

        (= desired (subvec recipes (- c (count desired))))
        (- c (count desired))

        :else
        (recur (mod (+ idx-1 recipe-1 1) c) (mod (+ idx-2 recipe-2 1) c) recipes)))))

(defn part-2 []
  (solve-2 [8 2 5 4 0 1]))
