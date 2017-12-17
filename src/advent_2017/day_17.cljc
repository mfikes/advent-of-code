(ns advent-2017.day-17)

(def input 345)

(defn spin [step [current-pos value buffer]]
  (let [new-pos (inc (mod (+ current-pos step) value))]
    [new-pos (inc value) (-> (subvec buffer 0 new-pos)
                           (into [value])
                           (into (subvec buffer new-pos)))]))

(defn part-1 []
  (let [[current-pos _ buffer] (nth (iterate (partial spin input) [0 1 [0]]) 2017)]
    (buffer (inc current-pos))))

(defn part-2 []
  (loop [current-pos 0 value 1 after-zero nil]
    (if (== (dec value) 50000000)
      after-zero
      (let [new-pos (inc (mod (+ current-pos input) value))]
        (recur new-pos (inc value) (if (== 1 new-pos)
                                     value
                                     after-zero))))))
