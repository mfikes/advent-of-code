(ns advent-2016.day-04.part-2
  (:require
    [advent-2016.day-04.data :refer [puzzle-input sample-input]]
    [clojure.string :as str]))

(defn checksum
  [s]
  (as-> s x
    (frequencies x)
    (dissoc x "-")
    (sort-by (juxt (comp - second) first) x)
    (map first x)
    (take 5 x)
    (apply str x)))

(defn valid-room?
  [code expected-checksum]
  (= expected-checksum (checksum code)))

(defn number-char?
  [s]
  ((set (seq "0123456789")) s))

(defn parse-name-sector-id
  [input]
  (let [[code room-num] (split-with (complement number-char?) input)]
    [(apply str code) (js/parseInt (apply str room-num))]))

(defn parse-input
  [input]
  (for [[encrypted-name-sector-id [checksum]] (partition 2 input)]
    [(name encrypted-name-sector-id) (name checksum)]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn rotate
  [s n]
  (str/escape s (zipmap alphabet (nthrest (cycle alphabet) n))))

(defn solve
  ([] (solve puzzle-input))
  ([input]
   (first (for [[encrypted-name-sector-id checksum] (parse-input input)
                :let [[encrypted-name sector-id] (parse-name-sector-id encrypted-name-sector-id)]
                :when (valid-room? encrypted-name checksum)
                :when (str/starts-with? (rotate (str/replace encrypted-name #"-" "") sector-id) "north")]
            sector-id))))