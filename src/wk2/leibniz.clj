(ns wk2.leibniz)

;sequence of single sums mapped over natural numbers
(defn single-sums [f]
  (map f (iterate inc 0)))

;sequence of partial sums mapped over natural numbers
(defn partial-sums [seq]
  (map #(reduce + (take (inc %) seq)) 
        (iterate inc 0)))

;single round of dampening
(defn dampen [seq]
  (map #(/ (+ %1 %2) 2) seq (rest seq)))

;applies dampening n times
(defn dampen-n [seq n]
  (if (= n 0)
    seq
    (dampen-n (dampen seq) (dec n))))

;Leibniz specific functions
(defn leibniz [n]
  (* 4 
    (/ (int (Math/pow -1 n)) ;force to int, so we get a ratio not a dec
       (inc (* 2 n)))))

(def leibniz-single (single-sums leibniz))
(def leibniz-partial (partial-sums leibniz-single))
(def leibniz-damp (dampen-n leibniz-partial 100))

(println (double (first leibniz-damp)))

