(use 'clojure.contrib.math)

(def phi (/ (+ 1 (sqrt 5)) 2))

(defn nth-fib [n] (round (/ (expt phi n) (sqrt 5))))

(def mem-nth-fib (memoize nth-fib))

(def try-raw #(time (nth-fib 10)))
(def try-memo #(time (mem-nth-fib 10)))

(println "- - - - NON MEMOIZED - - - -")
(dotimes [n 10] (try-raw)) 

(println "- - - - MEMOIZED - - - -")
(dotimes [n 10] (try-memo)) 