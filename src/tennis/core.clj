(ns tennis.core)

(def new-game [0 0])

(defn finished [[score1 score2]]
  (and (>= (max score1 score2) 4)
       (>= (Math/abs (- score1 score2)) 2)))

(defn player-score [score player]
  (let [[s1 s2] (if (= player :p1) score (reverse score))]
    (if (< s1 3)
      (nth [0 15 30] s1)
      (case (- s1 s2)
        -2  :lose
        -1  40
         0  :deuce
         1  (if (= s1 3) 40 :advantage)
         2  :win
            (if (< s1 s2) :lose :win)))))

(defn winner [score]
  (when (finished score)
    (if (= :win (player-score score :p1)) :p1 :p2)))

(defn add-point [score player]
  (when (finished score)
    (throw (IllegalArgumentException. "Set already finished")))
  (update-in score 
             ({:p1 [0], :p2 [1]} player)
             inc))

(defn game [scorers]
  (winner (reduce add-point new-game scorers)))
