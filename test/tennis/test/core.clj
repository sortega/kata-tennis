(ns tennis.test.core
  (:use [tennis.core])
  (:use [clojure.test])
  (:use midje.sweet))

(fact "Should recognize winner"
  (winner [:win :lose]) => :p1
  (winner [:lose :win]) => :p2
  (winner [10 40]) => nil)

(fact "Should score sets"
  (reductions score new-set
      [      :p1    :p2     :p1     :p1     :p1     :p2     :p1]) =>
      [[0 0] [10 0] [10 10] [20 10] [30 10] [40 10] [40 20] [:win :lose]]
  (reductions score [30 30]
      [        :p2     :p1             :p2             :p2]) =>
      [[30 30] [30 40] [:deuce :deuce] [40 :advantage] [:lose :win]]
  (reductions score [:deuce :deuce]
      [                :p1             :p2]) =>
      [[:deuce :deuce] [:advantage 40] [:deuce :deuce]])

(fact "Should compute a complete set"
      (set-winner (repeat 5 :p1)) => :p1
      (set-winner (repeat 6 :p1)) => (throws IllegalArgumentException)
      (set-winner [:p1 :p2 :p1 :p2 :p1 :p1 :p2 :p2 :p2 :p2]) => :p2
      (fact "Nadal-like set"
            (set-winner (concat (take 100 (cycle [:p1 :p2]))
                                [:p1 :p1])) => :p1))
