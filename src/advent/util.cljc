(ns advent.util
  (:refer-clojure :exclude [nth]))

(defn nth
  "Like core nth, but using transducers in order to conserve memory
  in ClojureScript when n is large and coll is directly reducible."
  ([coll n]
   (first (into [] (comp (drop n) (take 1)) coll)))
  ([coll n not-found]
   (if (neg? n)
     not-found
     (let [[ret :as xs] (into [] (comp (drop n) (take 1)) coll)]
       (if (empty? xs)
         not-found
         ret)))))
