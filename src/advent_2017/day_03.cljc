(ns advent-2017.day-03)

(def puzzle-input 368078)

(defn candidate-locations
  "Given a location, produces the four candidate adjacent locations in
  clockwise order."
  [[x y]]
  (map (fn [[dx dy]]
         [(+ x dx) (+ y dy)])
    [[0 -1] [-1 0] [0 1] [1 0]]))

(defn step
  "Given a vector containing a location and the set of used locations,
  produces a vector containing the next location and set of used locations."
  [[location used-locations]]
  ;; This algorithm works by first producing a cycle of the 4 candidate
  ;; next locations in clockwise order. Of the candidates that are not
  ;; used, it chooses, within that cycle, the first unused location that
  ;; follows a used location. This produces a counter-clockwise spiral.
  (let [next-location (->> location
                        candidate-locations
                        (map (fn [candidate-location]
                               [candidate-location (contains? used-locations candidate-location)]))
                        cycle
                        (drop-while (complement second))
                        (drop-while second)
                        ffirst)]
    [next-location (conj used-locations next-location)]))

(def spiral
  "A lazy sequnce of the memory locations."
  (->> [[1 0] #{[0 0] [1 0]}]
    (iterate step) (map first)
    (cons [0 0])))

(defn location
  "Gives the location for a given memory square."
  [square]
  (nth spiral (dec square)))

(defn distance
  "Determines the Manhattan distance of a location from the origin."
  [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(defn part-1 []
  (distance (location puzzle-input)))

(defn adjacent-locations
  "Given a location, produces the eight adjacent locations."
  [[x y]]
  (map (fn [[dx dy]]
         [(+ x dx) (+ y dy)])
    [[-1  1] [ 0  1] [ 1  1]
     [-1  0]         [ 1  0]
     [-1 -1] [ 0 -1] [ 1 -1]]))

(defn part-2 []
  (reduce (fn [acc location]
            (let [value (apply + (map #(acc % 0) (adjacent-locations location)))]
              (if (< puzzle-input value)
                (reduced value)
                (assoc acc location value))))
    {[0 0] 1}
    (rest spiral)))
