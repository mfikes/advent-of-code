(ns advent-2016.day-04.part-1
  (:require
    [advent-2016.day-04.data :refer [puzzle-input sample-input]]))

(defn checksum
  [s]
  (as-> s x
    (frequencies x)
    (dissoc x "-")
    (sort-by (juxt (comp - second) first) x)
    (map first x)
    (take 5 x)
    (apply str x)))

(defn number-char?
  [s]
  ((set (seq "0123456789")) s))

(defn parse
  [input]
  (let [[code room-num] (split-with (complement number-char?) input)]
    [code (js/parseInt (apply str room-num))]))

(defn valid-room?
  [code expected-checksum]
  (= expected-checksum (checksum code)))

(defn solve
  ([] (solve puzzle-input))
  ([input]
   (apply + (for [[encrypted-name-sector-id [checksum]] (partition 2 input)]
              (let [[encrypted-name sector-id] (parse (name encrypted-name-sector-id))]
                (if (valid-room? encrypted-name (name checksum))
                  sector-id
                  0))))))