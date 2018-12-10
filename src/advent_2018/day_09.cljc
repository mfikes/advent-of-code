(ns advent-2018.day-09
  (:require
   [advent.util :refer [nth']]))

(defn create-game [players]
  {:players players
   :player  1
   :index   1.0
   :marble  1
   :circle  (sorted-map 0.0 0 1.0 1)
   :scores  (zipmap (map inc (range players)) (repeat 0))})

(defn place-index [circle index]
  (let [[lb ub] (keys (rest (concat (subseq circle >= index) (cycle circle))))]
    (if (< lb ub)
      (/ (+ lb ub) 2.0)
      (inc (ffirst (rseq circle))))))

(defn counter-clockwise-index [circle index n]
  (-> (keys (concat (rsubseq circle <= index) (cycle (rseq circle))))
    (nth n)))

(defn advance [game]
  (let [{:keys [players player index marble circle scores]} game
        marble (inc marble)
        player (inc (mod player players))
        game   (assoc game :marble marble :player player)]
    (if (zero? (mod marble 23))
      (let [remove-index (counter-clockwise-index circle index 7)
            scores       (update scores player + marble (circle remove-index))
            circle       (dissoc circle remove-index)
            index        (ffirst (or (subseq circle > remove-index) circle))]
        (assoc game :index index :circle circle :scores scores))
      (let [index  (place-index circle index)
            circle (assoc circle index marble)]
        (assoc game :index index :circle circle)))))

(defn solve [players last-marble]
  (let [game (-> (iterate advance (create-game players))
               (nth' (dec last-marble)))]
    (apply max (vals (:scores game)))))

(defn part-1 []
  (solve 486 70833))

(defn part-2 []
  (solve 486 (* 100 70833)))
