(import 'java.util.Date)

(def x (ref 1))
(def y (ref 1))

(defn ok-to-inc? [x y]
      (< (+ x y) 3))


(do
      (future
       (dosync
        (ensure y)
        (let [xv @x
              yv @y]
          (println "Trying X" (Date.))
          (Thread/sleep 1000)
          (if (ok-to-inc? xv yv)
            (alter x inc)))))
  
      (future
       (dosync
        (ensure x)
        (let [xv @x
              yv @y]
          (println "Trying Y" (Date.))
          (Thread/sleep 1000)
          (if (ok-to-inc? xv yv)
            (alter y inc))))))


(do
  (Thread/sleep 10000)
  (println "X is ... " @x)
  (println "Y is ... " @y))