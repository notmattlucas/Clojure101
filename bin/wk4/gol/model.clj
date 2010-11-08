(ns wk4.gol.model)

(defn- random-bool []
  "Generates a random boolean"
  (let [i (rand)]
    (if (< 0.8 (double (rand)))
      true
      false)))

(defn- random-seq [size]
  "Generates a random seq of booleans"
  (vec
    (repeatedly size random-bool)))

(defn random-matrix [width height]
  "Generates a matrix of random bools"
  (vec 
    (repeatedly height 
      #(random-seq width))))

(defn- safe-cell [pos upper]
  (if (< pos 0)
    (dec upper)
    (if (>= pos upper)
      0
      pos)))

(defn- cell-val [x y life]
  "Returns a boolean value of cell, protected for out of range"
  (let [upper-x (count (first life))
	    upper-y (count life)
        x (safe-cell x upper-x)
        y (safe-cell y upper-y)]
      ((life y) x)))

(defn discard-current [x y cells]
  "Discards the cell specified from the vector"
  (filter #(not (= [x y] %)) cells))

(defn- potential-neighbours [x y]
  "Returns a list of all neighbours of a cell, living of dead"
  (let [x-lower (- x 1)
        x-upper (+ x 2)
        y-lower (- y 1)
        y-upper (+ y 2)]
	  (discard-current x y 
	        (for [xi (range x-lower x-upper)
                  yi (range y-lower y-upper)]
                  [xi yi]))))
  

(defn- count-neighbours [x y life]
  "Returns a count of live neighbours"
  (let [ns (potential-neighbours x y)]
    (count
	    (filter 
	      #(= true %)
		    (for [n ns]
		      (cell-val (n 0) (n 1) life))))))

(defn- dies [n-count]
  "Returns true if a cell dies"
  (or (< n-count 2) (> n-count 3)))

(defn- evolve-live [x y life]
  "Determines next evolution of a live cell"
  (let [n-count (count-neighbours x y life)]
    (if (dies n-count)
      false
      true)))

(defn- evolve-dead [x y life]
  "Determines next evolution of a dead cell"
  (let [n-count (count-neighbours x y life)]
    (if (= 3 n-count)
      true
      false)))

(defn- evolve-cell [x y life]
  "Determines the evolution of a single cell"
  (let [cell (cell-val x y life)]
    (if cell
      (evolve-live x y life)
      (evolve-dead x y life))))

(defn evolve [life]
  "Determines the next step evolution of the full environment"
  (let [width (count (first life))
        height (count life)]
    (vec (for [y (range 0 height)]
      (vec (for [x (range 0 width)]
        (evolve-cell x y life)))))))


