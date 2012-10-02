(ns tennis.test.core
  (:use [tennis.core])
  (:use [clojure.test])
  (:use midje.sweet))

(fact "Should recognize winner"
      (winner [ 4  2]) => :p1
      (winner [10 12]) => :p2
      (winner [ 1  3]) => nil)

(fact "Translate player scores"
      (player-score [0 0] :p1) => 0
      (player-score [1 2] :p1) => 15
      (player-score [1 2] :p2) => 30
      (player-score [3 2] :p1) => 40
      (player-score [3 3] :p1) => :deuce
      (player-score [3 4] :p1) => 40
      (player-score [4 2] :p1) => :win
      (player-score [4 3] :p1) => :advantage
      (player-score [4 4] :p2) => :deuce
      (player-score [4 6] :p1) => :lose)

(fact "Count player points"
      (add-point [0 0] :p1) => [1 0]
      (add-point [4 4] :p2) => [4 5])

(fact "Should compute a complete set"
      (game (repeat 4 :p1)) => :p1
      (game (repeat 5 :p1)) => (throws IllegalArgumentException)
      (game [:p1 :p2 :p1 :p2 :p2 :p2]) => :p2
      (fact "Nadal-like set"
            (game (concat (take 100 (cycle [:p1 :p2]))
                          [:p1 :p1])) => :p1))
