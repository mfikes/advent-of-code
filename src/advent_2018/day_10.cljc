(ns advent-2018.day-10
  (:require
   [advent.util :refer [count' nth']]
   #?(:cljs [planck.core :refer [line-seq read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as string]))

(def input-lines (->> "advent_2018/day_10/input" io/resource io/reader line-seq))

(defn read-point [s]
  (zipmap [:p-x :p-y :v-x :v-y] (map read-string (re-seq #"-?\d+" s))))

(defn update-point [point]
  (-> point
    (update :p-x + (:v-x point))
    (update :p-y + (:v-y point))))

(defn raster-geometry [points]
  (let [min-max (juxt #(apply min %) #(apply max %))
        [min-x max-x] (min-max (map :p-x points))
        [min-y max-y] (min-max (map :p-y points))]
    [min-x min-y (- max-x min-x) (- max-y min-y)]))

(defn big? [points]
  (let [[_ _ width height] (raster-geometry points)]
    (or (> width 80) (> height 10))))

(defn rasterize [points]
  (let [[origin-x origin-y width height] (raster-geometry points)]
    (reduce (fn [raster {:keys [p-x p-y]}]
              (assoc-in raster [(- p-y origin-y) (- p-x origin-x)] "#"))
      (vec (repeat (inc height) (vec (repeat (inc width) "."))))
      points)))

(defn print-raster [raster]
  (println (string/join \newline (map string/join raster))))

(defn part-1 []
  (let [initial-points (map read-point input-lines)
        points (nth' (eduction (drop-while big?) (iterate #(map update-point %) initial-points)) 0)]
    (print-raster (rasterize points))))

(defn part-2 []
  (let [initial-points (map read-point input-lines)]
    (count' (eduction (take-while big?) (iterate #(map update-point %) initial-points)))))
