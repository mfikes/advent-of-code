(ns advent-2016.day-5.part-1
  (:require [advent-2016.day-5.md5 :refer [md5-hex]]
            [clojure.string :as str]))

(defn solve
  [s]
  (->> (range)
    (map #(md5-hex (str s %)))
    (filter #(str/starts-with? % "00000"))
    (map #(subs % 5 6))
    (take 8)))
