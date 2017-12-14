(ns advent-2017.day-10
  (:require
   #?(:cljs [planck.core :refer [read-string]])
   [clojure.string :as str]
   [clojure.pprint :refer [cl-format]]))

(def input "129,154,49,198,200,133,97,254,41,6,2,1,255,0,191,108")

(defn round [lengths [xs current-pos skip-size]]
  (reduce (fn [[xs current-pos skip-size] length]
            (let [[to-reverse all-after] (split-at length (->> xs cycle (drop current-pos)))
                  reversed-and-after (concat (reverse to-reverse) (take (- 256 length) all-after))]
              [(->> reversed-and-after cycle (drop (- 256 current-pos)) (take 256))
               (mod (+ current-pos length skip-size) 256)
               (mod (inc skip-size) 256)]))
    [xs current-pos skip-size]
    lengths))

(defn part-1 []
  (->> [(range 256) 0 0]
    (round (map read-string (str/split input #",")))
    first
    (take 2)
    (apply *)))

(defn knot-hash-decimal
  [s]
  (->> [(range 256) 0 0]
    (iterate (partial round
               (into (mapv #?(:clj int :cljs #(.charCodeAt % 0) ) s)
                 [17, 31, 73, 47, 23])))
    (drop 64)
    ffirst
    (partition 16)
    (mapv #(reduce bit-xor %))))

(defn knot-hash
  [s]
  (->> (knot-hash-decimal s)
    (map #(cl-format nil "~2,'0x" %))
    (apply str)))

(defn part-2 []
  (knot-hash input))
