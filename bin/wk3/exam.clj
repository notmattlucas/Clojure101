(ns wk3.exam)

(def results [0 0 0 1 0 1 1 1 1 0 1 1 0 1 0 0 1 1 1 0 0 1 0 1])

;imperative
(defn loop-count [res]
  (loop [scores res acc 0]
    (if (empty? scores)
      acc
      (if (= 1 (first scores))
        (recur (rest scores) (inc acc))
        (recur (rest scores) acc)))))

;functional
(defn filt-count [res]
  (count 
    (filter #(= 1 %) res)))

(defn to-perct [num tot]
  (double (* 100  (/ num tot))))

(defn pass-rate [f res]
  (let [passed (f res)
        tot (count res)
        perct (to-perct passed tot)]
    (println (format "%s/%s students passed (%.2f%%)" passed tot perct))))
  
;usage
(pass-rate filt-count results)
(pass-rate loop-count results)