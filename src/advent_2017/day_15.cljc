(ns advent-2017.day-15
  #?(:cljs (:require-macros [advent-2017.day-15 :refer [solve a-gen b-gen multiples]])))

(when #?(:clj true :cljs (re-matches #".*\$macros" (name (ns-name *ns*))))

  (def ^:const input-a 512)
  (def ^:const input-b 191)

  (defmacro solve [max-count a a-gen b b-gen]
    `(loop [sum# 0 ~a input-a ~b input-b count# 0]
       (if (== count# ~max-count)
         sum#
         (let [next-a# ~a-gen
               next-b# ~b-gen]
           (if (== (bit-and next-a# 0xFFFF) (bit-and next-b# 0xFFFF))
             (recur (inc sum#) next-a# next-b# (inc count#))
             (recur sum# next-a# next-b# (inc count#)))))))

  (defmacro a-gen [a] `(rem (* ~a 16807) 2147483647))
  (defmacro b-gen [b] `(rem (* ~b 48271) 2147483647))

  (defmacro multiples [n gen m]
    `(loop [~n ~gen]
       (if (zero? (rem ~n ~m))
         ~n
         (recur ~gen)))))

(when #?(:clj true :cljs (not (re-matches #".*\$macros" (name (ns-name *ns*)))))

  (declare a b)

  (defn part-1 []
    (solve 40e6 a (a-gen a) b (b-gen b)))

  (defn part-2 []
    (solve 5e6
      a (multiples a (a-gen a) 4)
      b (multiples b (b-gen b) 8))))
