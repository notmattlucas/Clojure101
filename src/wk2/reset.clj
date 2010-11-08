(ns wk2.reset)

(def x (ref 100))
(def y (ref 200))
(def z (ref 300))

(defn reset-stuff-1 []
      (dosync
       (map ref-set
            [x y z] ; could pass in some arguments, of course
            [1 2 3])))

(defn reset-stuff-2 []
       (map #(dosync(ref-set %1 %2))
            [x y z]
            [1 2 3]))

(defn reset-stuff-3 []
      (dosync
        (dorun
	      (map ref-set
	           [x y z] ; could pass in some arguments, of course
	           [4 5 6]))))

(defn print-refs [] (println @x @y @z))