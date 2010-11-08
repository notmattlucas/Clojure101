(ns wk3.grandma-other)

(def *repeat-resp* "HUH? SPEAK UP, SONNY!")
(def *ack-resp* "NO, NOT SINCE %d!")
(def *end-resp* "BYE SONNY!")
 
(defn repeat-resp []
  *repeat-resp*)
 
(defn ack-resp []
  (format *ack-resp* (+ 1930 (rand-int 20))))
 
(defn end-resp []
  *end-resp*)
 
(defn ack? [input]
  (= input (.toUpperCase input)))
 
(defn end? [input]
  (= "BYE" input))
 
(defn get-input []
  (print "Tell garndma: ")
  (flush)
  (read-line))
 
(defn start-grandma []
  (loop [input (get-input)]
    (if (end? input)
      (println (end-resp))
      (do
	(println
	 (if (ack? input)
	   (ack-resp)
	   (repeat-resp)))
	(recur (get-input))))))

