(ns advent-2016.day-03.part-2
  (:require
    [advent-2016.day-03.data :refer [puzzle-input]]
    [cljs.spec :as s]))

(s/def ::edge-length (s/and int? pos?))

(s/def ::triangle-spec (s/coll-of ::edge-length :count 3))

(s/def ::puzzle-input (s/and (s/coll-of ::edge-length :min-count 3)
                        (fn [coll]
                          (zero? (rem (count coll) 3)))))

(defn triangle?
  [a b c]
  (let [[a' b' c'] (sort [a b c])]
    (> (+ a' b') c')))

(s/fdef triangle?
  :args ::triangle-spec
  :ret boolean?)

(defn triangle-specs
  [puzzle-input]
  (partition 3 puzzle-input))

(s/fdef triangle-specs
  :args (s/cat :puzzle-input ::puzzle-input)
  :ret (s/coll-of ::triangle-spec))

(defn triangles
  [triangle-specs]
  (filter #(apply triangle? %) triangle-specs))

(s/fdef triangles
  :args (s/cat :triangle-specs (s/coll-of ::triangle-spec))
  :ret (s/coll-of ::triangle-spec))

(defn transpose
  [triangle-specs]
  (->> (apply concat (apply map vector triangle-specs))
    advent-2016.day-03.part-2/triangle-specs))

#_(s/fdef transpose
  :args (s/cat :triangle-specs (s/coll-of ::triangle-spec))
  :ret (s/coll-of ::triangle-spec))

(defn solve
  []
  (count (triangles (transpose (triangle-specs puzzle-input)))))
