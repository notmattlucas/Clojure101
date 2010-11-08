(ns #^{:doc "The code for exercise 2 of week 2 of the RubyLearning.org 
            Clojure 101 course."
       :author "Matthew Lucas"}
  temp)

(def cities [{:city "Vienna" :temp 55} 
             {:city "Krakow" :temp 52} 
             {:city "Pune" :temp 85} 
             {:city "Houston" :temp 57}])

(defn avg-temp [cities]
  (let [temps (map :temp cities)]
      (/ (reduce + temps) (count cities))))
  
(defn to-dec [ratio]
  (let [dec (bigdec ratio)]
    (do (.setScale dec 2) dec)))

(defn avg-temp-dec [cities]
  (to-dec (avg-temp cities)))
    
(defn to-celcius [farnht]
  (to-dec 
    (* (- farnht 32) (/ 5 9))))
  
(println (avg-temp-dec cities))
(println (to-celcius (avg-temp-dec cities)))
