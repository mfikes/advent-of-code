(ns advent-2016.day-01.part-1
  (:require
    [advent-2016.day-01.data :refer [puzzle-input]]
    [clojure.set :as set]
    [clojure.spec :as s]))

(s/def ::turn #{::right ::left})

(s/def ::blocks (s/with-gen (s/and int? pos?)
                  #(s/gen (set (range 1 200)))))

(s/def ::direction #{::north ::east ::south ::west})

(s/def ::move (s/keys :req [::turn ::blocks]))

(s/def ::blocks-east int?)

(s/def ::blocks-north int?)

(s/def ::location (s/keys :req [::blocks-east ::blocks-north]))

(s/def ::state (s/keys :req [[::direction ::location]]))

(defn parse-turn
  [s]
  (case s
    "R" ::right
    "L" ::left))

(s/def ::turn-rep #{"R" "L"})

(s/fdef parse-turn
  :args (s/cat :s ::turn-rep)
  :ret ::turn)

(defn parse-blocks
  [s]
  (js/parseInt s))

(s/def ::blocks-rep (s/with-gen string?
                      #(s/gen (set (map str (range 1 200))))))

(s/fdef parse-blocks
  :args (s/cat :s ::blocks-rep)
  :ret ::blocks)

(defn parse-move
  [move]
  (let [move-str (str move)]
    {::turn   (parse-turn (first move-str))
     ::blocks (parse-blocks (subs move-str 1))}))

(s/def ::move-rep (s/with-gen symbol?
                    #(s/gen (set (for [turn ["R" "L"]
                                       blocks (range 1 200)]
                                   (symbol (str turn blocks)))))))

(s/fdef parse-move
  :args (s/cat :s ::move-rep)
  :ret ::move)

(def directions-clockwise [::north ::east ::south ::west])

(def right-turns (zipmap directions-clockwise (concat (rest directions-clockwise) [(first directions-clockwise)])))

(def left-turns (set/map-invert right-turns))

(defn update-direction
  [direction turn]
  (let [turns (case turn
                ::right right-turns
                ::left left-turns)]
    (get turns direction)))

(s/fdef update-direction
  :args (s/cat :direction ::direction :turn ::turn)
  :ret ::direction)

(defn update-location
  [location direction blocks]
  (case direction
    ::north (update location ::blocks-north + blocks)
    ::east (update location ::blocks-east + blocks)
    ::south (update location ::blocks-north - blocks)
    ::west (update location ::blocks-east - blocks)))

(s/fdef update-location
  :args (s/cat :location ::location :direction ::direction :blocks ::blocks)
  :ret ::location)

(defn update-state
  [{:keys [::direction ::location]}
   {:keys [::turn ::blocks]}]
  (let [direction (update-direction direction turn)
        location (update-location location direction blocks)]
    {::direction direction
     ::location  location}))

(s/fdef update-state
  :args (s/cat :state ::state :move ::move)
  :ret ::state)

(defn follow-moves
  [state moves]
  (reduce update-state state moves))

(s/fdef follow-moves
  :args (s/cat :state ::state :moves (s/coll-of ::move)))

(defn distance
  [{:keys [::blocks-east ::blocks-north]}]
  (+ (Math/abs blocks-east) (Math/abs blocks-north)))

(s/fdef distance
  :args (s/cat :location ::location)
  :ret int?)

(defn calcluate-solution
  [initial-state moves]
  (-> (follow-moves initial-state moves)
    ::location
    distance))

(s/fdef calcluate-solution
  :args (s/cat :initial-state ::state :moves (s/coll-of ::move)))

(defn solve
  []
  (let [raw-moves puzzle-input
        moves (map parse-move raw-moves)
        initial-state {::direction ::north
                       ::location  {::blocks-east  0
                                    ::blocks-north 0}}]
    (calcluate-solution initial-state moves)))
