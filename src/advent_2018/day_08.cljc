(ns advent-2018.day-08
  (:require
   #?(:cljs [planck.core :refer [slurp read-string]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]))

(def input (-> "advent_2018/day_08/input" io/resource slurp))

(defn parse-license [input]
  (let [node+more (fn node+more [[child-count metadata-count & more]]
                    (let [[children more] (reduce (fn [[children more] _]
                                                    (let [[child more] (node+more more)]
                                                      [(conj children child) more]))
                                            [[] more]
                                            (range child-count))
                          [metadata more] (split-at metadata-count more)]
                      [{:children children, :metadata metadata} more]))
        nums      (map read-string (re-seq #"\d+" input))]
    (first (node+more nums))))

(defn part-1 []
  (->> (tree-seq (comp seq :children) :children (parse-license input))
    (mapcat :metadata)
    (reduce +)))

(defn part-2 []
  (let [node-value (fn node-value [{:keys [children metadata]}]
                     (if (empty? children)
                       (reduce + metadata)
                       (->> metadata
                         (keep #(get children (dec %)))
                         (map node-value)
                         (reduce +))))]
    (node-value (parse-license input))))
