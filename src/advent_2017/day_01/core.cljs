(ns advent-2017.day-01.core)

(let [c->d (zipmap "0123456789" (range))]
  (defn str->digits
    [s]
    (map c->d s)))
