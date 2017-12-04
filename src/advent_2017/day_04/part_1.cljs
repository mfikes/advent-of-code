(ns advent-2017.day-04.part-1
  (:require
   [clojure.string :as str]
   [advent-2017.day-04.data-1 :as data]))

(defn valid-passphrase?
  "Returns true iff the supplied passphrase string is valid."
  [passphrase]
  (apply distinct? (str/split passphrase #" ")))

(defn solve
  "Solves the puzzle for a sequence of passphrases. If none provided, solves
  using the main puzzle input."
  ([] (solve data/puzzle-input))
  ([passphrases]
   (->> passphrases
     (filter valid-passphrase?)
     count)))
