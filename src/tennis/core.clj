(ns tennis.core)

(def new-set [0 0])

(defn winner [[score1 score2]]
  (cond
    (= score1 :win) :p1
    (= score2 :win) :p2))

(defn score-p1 [[score1 score2 :as s]]
  (when (winner s)
    (throw (IllegalArgumentException. "Set already finished")))
  (cond
    (= :advantage score1)    [:win :lose]
    (= :advantage score2)    [:deuce :deuce]
    (= :deuce score1 score2) [:advantage 40]
    (and (= 30 score1)
         (= 40 score2))      [:deuce :deuce]
    (= 40 score1)            [:win :lose]
    :else                    [(+ score1 10) score2]))

(def score-p2 (comp reverse score-p1 reverse))

(defn score [points player]
  ((if (= player :p1) score-p1 score-p2) points))

(defn set-winner [scorers]
  (winner (reduce score new-set scorers)))
