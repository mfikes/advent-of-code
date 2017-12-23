(ns advent-2017.day-23
  (:require
   #?(:cljs [planck.core :refer [read]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.spec.alpha :as s])
  #?(:clj (:import (java.io PushbackReader))))

(def input (->> "advent_2017/day_23/input" io/resource io/reader #?(:clj PushbackReader.)))

(s/def ::arg (s/or :sym simple-symbol? :val int?))
(s/def ::instr (s/cat :op '#{set add sub mul mod jnz} :arg1 ::arg :arg2 ::arg))

(s/check-asserts true)
(def data (->> (repeatedly #(read {:eof nil} input))
            (take-while some?)
            (s/assert (s/* ::instr))
            (s/conform (s/* ::instr))))

(defn step* [program {:keys [ip regs] :as state}]
  (if (contains? program ip)
    (let [{:keys [op arg1 arg2]} (program ip)               ; fetch
          state  (update state :ip inc)
          eval   (fn [[kind x]]
                   (cond-> x (= :sym kind) (regs 0)))
          mutate (fn [f]
                   (let [reg (second arg1)]
                     (-> state
                       (update-in [:regs reg] (fnil f 0) (eval arg2))
                       (cond-> (= reg (:break-write state)) (assoc :break state)))))]
      (-> (case op
            set (mutate (fn [a b] b))
            add (mutate +)
            sub (mutate -)
            mul (mutate *)
            jnz (cond-> state (not (zero? (eval arg1))) (assoc :ip (+ ip (eval arg2)))))
        (cond-> (= (:count-op state) op) (update-in [:regs 'h] (fnil inc 0)))))
    (throw (ex-info "jumped out" {:state state}))))

(defn part-1 []
  (let [step (partial step* data)]
    ('h (->> (iterate step {:ip          0
                            :regs        {}
                            :break-write 'h
                            :count-op    'mul})
          (some :break)
          :regs))))

(comment
  ;; Observe b set to 108100 and c to 125100 for this chunk of code
  (try
    (let [step (partial step* (subvec data 0 8))]
      (doall (iterate step {:ip   0
                            :regs {'a 1}})))
    (catch #?(:clj Throwable :cljs :default) e
      (ex-data e)))

  ;; Observe f set to 1 iff b is prime for this chunk of code
  (try
    (let [step (partial step* (subvec data 8 24))]
      (doall (iterate step {:ip   0
                            :regs {'b 19}})))
    (catch #?(:clj Throwable :cljs :default) e
      (ex-data e))))

;; We could optimize code, or just calculate what it would do. Choose latter.

(defn prime? [n]
  (and (odd? n)
       (let [root (int (Math/sqrt n))]
         (loop [i 3]
           (or (> i root)
               (and (not (zero? (mod n i)))
                    (recur (+ i 2))))))))

(defn part-2 []
  (count (remove prime? (range 108100 125101 17))))