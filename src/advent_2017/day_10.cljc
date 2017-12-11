(ns advent-2017.day-10
  (:require
   [clojure.string :as str]))

(def data [129,154,49,198,200,133,97,254,41,6,2,1,255,0,191,108])

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
    (round data)
    first
    (take 2)
    (apply *)))

(defn xs->ascii [xs]
  (->> xs (str/join ",") (mapv #?(:clj int :cljs #(.charCodeAt % 0)))))

(defn hex [n]
  (let [nybble (fn [n] (nth "0123456789abcdef" n))]
    (str (nybble (bit-and (bit-shift-right n 4) 0xF)) (nybble (bit-and n 0xF)))))

(defn part-2 []
  (->> [(range 256) 0 0]
    (iterate (partial round (into (xs->ascii data) [17, 31, 73, 47, 23])))
    (drop 64)
    ffirst
    (partition 16)
    (map #(reduce bit-xor %))
    (map hex)
    (apply str)))
