(ns advent-2017.day-18
  (:require
   #?(:cljs [planck.core :refer [read]])
   [#?(:clj clojure.java.io :cljs planck.io) :as io]
   [clojure.spec.alpha :as s])
  #?(:clj (:import (java.io PushbackReader))))

(def input (->> "advent_2017/day_18/input" io/resource io/reader #?(:clj PushbackReader.)))

(s/def ::arg (s/or :sym simple-symbol? :val int?))
(s/def ::instr (s/alt :unary (s/cat :op '#{snd rcv} :arg1 ::arg)
                      :binary (s/cat :op '#{set add mul mod jgz} :arg1 ::arg :arg2 ::arg)))

(s/check-asserts true)
(def data (->> (repeatedly #(read {:eof nil} input))
            (take-while some?)
            (s/assert (s/* ::instr))
            (s/conform (s/* ::instr))
            (map val)
            vec))

(defn step* [snd-fn rcv-fn {:keys [ip regs] :as state}]
  (let [{:keys [op arg1 arg2]} (data ip)                    ; fetch
        state  (update state :ip inc)
        eval   (fn [[kind x]]
                 (cond-> x (= :sym kind) (regs 0)))
        mutate (fn [f]
                 (update-in state [:regs (second arg1)] (fnil f 0) (eval arg2)))]
    (case op
      snd (snd-fn state (eval arg1))
      set (mutate (fn [a b] b))
      add (mutate +)
      mul (mutate *)
      mod (mutate mod)
      rcv (rcv-fn state (eval arg1) (second arg1))
      jgz (cond-> state (pos? (eval arg1)) (assoc :ip (+ ip (eval arg2)))))))

(defn part-1 []
  (let [snd-fn (fn [state v]
                 (assoc-in state [:flags :last-snd] v))
        rcv-fn (fn [state prior-val reg]
                 (if (zero? prior-val)
                   state
                   (let [last-snd (get-in state [:flags :last-snd])]
                     (-> state
                       (assoc-in [:regs reg] last-snd)
                       (assoc-in [:flags :rcv] last-snd)))))
        step   (partial step* snd-fn rcv-fn)]
    (->> (iterate step {:ip 0 :regs {}})
      (some (fn [state] (get-in state [:flags :rcv]))))))

(defn part-2 []
  (let [queue-0     (volatile! #?(:clj clojure.lang.PersistentQueue/EMPTY :cljs #queue []))
        queue-1     (volatile! #?(:clj clojure.lang.PersistentQueue/EMPTY :cljs #queue []))
        snd-fn      (fn [{:keys [snd-queue] :as state} v]
                      (vswap! snd-queue conj v)
                      (update state :snd-count (fnil inc 0)))
        rcv-fn      (fn [{:keys [rcv-queue] :as state} prior-val reg]
                      (if-let [val (peek @rcv-queue)]
                        (do (vswap! rcv-queue pop)
                            (-> state
                              (assoc-in [:regs reg] val)
                              (assoc :need-input? false)))
                        (-> state
                          (update :ip dec)
                          (assoc :need-input? true))))
        step        (partial step* snd-fn rcv-fn)
        blocked?    (fn [state]
                      (and (:need-input? state) (empty? @(:rcv-queue state))))
        deadlocked? (fn [& states]
                      (every? blocked? states))]
    (loop [state-0 {:ip 0 :regs {'p 0} :snd-queue queue-1 :rcv-queue queue-0}
           state-1 {:ip 0 :regs {'p 1} :snd-queue queue-0 :rcv-queue queue-1}]
      (cond
        (deadlocked? state-0 state-1) (:snd-count state-1)
        (blocked? state-0) (recur state-0 (step state-1))
        :else (recur (step state-0) state-1)))))
