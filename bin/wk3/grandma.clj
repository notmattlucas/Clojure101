(ns wk3.grandma)

(defn bye? [said] (= "BYE" said))

(defn all-byes? [byes]
  (every? bye? byes))

(defn three-byes? [byes]
  (and
    (= 3 (count byes))
    (all-byes? byes)))

(defn append-common [elm elms]
  (let [new-elms (conj elms elm)]
    (if (all-byes? new-elms)
      new-elms
      [])))

(defn uppercase? [said]
  (= 0 (count (re-seq #"[a-z]" said))))

(defn hello-there [] (println "HELLO THERE SONNY!"))

(defn not-since-year []
   (let [year-add (int (* 20 (rand)))]
     (+ 1930 year-add)))

(defn not-since [] 
  (let [year (not-since-year)]
    (println "NO, NOT SINCE" year "!")))

(defn speak-up [] (println "HUH?! SPEAK UP, SONNY!"))

(defn grandma-speak [line]
  (if (uppercase? line)
    (not-since)
    (speak-up)))

(defn say []
  (read-line))

(defn deaf-grandma []
  (hello-there)
  (loop [byes []]
    (let [said (say) 
          byes (append-common said byes)]
       (when-not (three-byes? byes) 
         (grandma-speak said)
         (recur byes)))))
           
           