(ns advent-2017.day-04.part-2
  (:require
   [clojure.string :as str]
   [advent-2017.day-04.data-2 :as data]))

(defn valid-passphrase?
  "Returns true iff the supplied passphrase string is valid."
  [passphrase]
  (->> (str/split passphrase #" ")
    (map sort)
    (apply distinct?)))

(defn solve
  "Solves the puzzle for a sequence of passphrases. If none provided, solves
  using the main puzzle input."
  ([] (solve data/puzzle-input))
  ([passphrases]
   (->> passphrases
     (filter valid-passphrase?)
     count)))
