(ns advent-2017.day-08
  (:refer-clojure :exclude [inc dec])
  (:require
   #?(:cljs [planck.core :refer [eval read]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.string :as str]
   [clojure.spec.alpha :as s])
  #?(:clj (:import (java.io PushbackReader))))

(def input (->> "advent_2017/day_08/input" io/resource io/reader #?(:clj PushbackReader.)))

(s/def ::instr (s/* (s/cat :tgt symbol? :upd symbol? :val number? :if #{'if} :lhs symbol? :cmp symbol? :rhs number?)))

(def data (->> (repeatedly #(read {:eof nil} input)) (take-while some?) (s/conform ::instr)))

(def inc +)
(def dec -)
(def != not=)

(def register-history
  (reductions (fn [acc {:keys [tgt upd val lhs cmp rhs]}]
                (eval `(cond-> '~acc (~cmp ('~acc '~lhs 0) ~rhs) (update '~tgt (fnil ~upd 0) ~val))))
    (zipmap (map :lhs data) (repeat 0))
    data))

(defn max-register-val [registers]
  (apply max (or (vals registers) [0])))

(defn part-1 []
  (max-register-val (last register-history)))

(defn part-2 []
  (apply max (map max-register-val register-history)))
