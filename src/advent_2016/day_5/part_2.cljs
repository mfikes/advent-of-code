(ns advent-2016.day-5.part-2
  (:require [advent-2016.day-5.md5 :refer [md5-hex]]
            [clojure.string :as str]))

(defn ndx-str
  [s]
  (subs s 5 6))

(defn code-str
  [s]
  (subs s 6 7))

(defn valid-ndx-str?
  [s]
  (#{"0" "1" "2" "3" "4" "5" "6" "7" "8"} s))

(defn candidates
  [s]
  (sequence
    (comp
      (map #(md5-hex (str s %)))
      (filter #(str/starts-with? % "00000"))
      (filter #((comp valid-ndx-str? ndx-str) %)))
    (range)))

(defn solve
  [s]
  (loop [seen #{}
         candidates (candidates s)
         result (vec (repeat 8 " "))]
    (let [candidate (first candidates)
          ndx (js/parseInt (ndx-str candidate))]
      (if (seen ndx)
        (recur seen (rest candidates) result)
        (let [result (assoc result ndx (code-str candidate))]
          (if (== 7 (count seen))
            (apply str result)
            (recur (conj seen ndx) (rest candidates) result)))))))
