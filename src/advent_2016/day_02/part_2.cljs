(ns advent-2016.day-02.part-2
  (:require
    [advent-2016.day-02.data :refer [puzzle-input]]
    [clojure.set :as set]
    [cljs.spec :as s]))


(def pad '[[_ _ 1 _ _]
           [_ 2 3 4 _]
           [5 6 7 8 9]
           [_ A B C _]
           [_ _ D _ _]])

(def key-lookup
  (delay (into {} (for [y (range 5)
                        x (range 5)
                        :when (not= '_ (get-in pad [y x]))]
                    [[x y] (get-in pad [y x])]))))

(defn on-pad?
  [coordinates]
  (contains? @key-lookup coordinates))

(s/def ::coordinate (s/int-in 0 5))

(s/def ::key '#{1 2 3 4 5 6 7 8 9 A B C D})

(s/def ::coordinates (set (keys @key-lookup)))

(s/def ::move #{::up ::down ::left ::right})

(defn parse-move
  [move]
  (case move
    "U" ::up
    "D" ::down
    "L" ::left
    "R" ::right))

(s/fdef parse-move
  :args (s/cat :input #{"U" "D" "L" "R"})
  :ret ::move)

(defn parse-moves
  [moves]
  (map parse-move (str moves)))

(defn update-state
  [[x y :as coordinates] move]
  (let [new-coordinates (case move
                          ::up [x (dec y)]
                          ::down [x (inc y)]
                          ::left [(dec x) y]
                          ::right [(inc x) y])]
    (if (on-pad? new-coordinates)
      new-coordinates
      coordinates)))

(s/fdef update-state
  :args (s/cat :coordinates (s/spec ::coordinates) :move ::move)
  :ret ::coordinates)

(defn coordinates->key
  [coordinates]
  (@key-lookup coordinates))

(s/fdef coordinates->key
  :args (s/cat :coordinates (s/spec ::coordinates))
  :ret ::key)

(defn process-moves
  [coordinates moves]
  (reduce update-state
    coordinates
    moves))

(s/fdef process-moves
  :args (s/cat :coordinates (s/spec ::coordinates) :moves (s/coll-of ::move))
  :ret ::coordinates)

(defn solve
  []
  (let [moves-list (map parse-moves puzzle-input)]
    (->> (reductions process-moves
           ((set/map-invert @key-lookup) 5)
           moves-list)
      rest
      (map coordinates->key)
      (apply str))))
