(ns wk3.id3
   (:use clojure.contrib.duck-streams)
   (:use clojure.contrib.str-utils))

;map defining the spec of id3v1
;used in id3-factory to define the way the
;bytes are parsed and resultant object constructed
(def id3struct 
  [{:header 3}
   {:title 30}
   {:artist 30}
   {:album 30}
   {:year 4}
   {:comment 28}
   {:zero-byte 1}
   {:track 1}
   {:genre 1}])

;loads 'filename' as a byte-stream
(defn loadfile [filename]
  (with-open [rdr (reader filename)]
    (to-byte-array rdr)))

;converts a byte-stream to string representation
(defn bytes-to-str [bytestr]
  (String. (byte-array bytestr)))

;extracts the 128 id3v1 bytes from an mp3
(defn get-id3-bytes [bytestr]
  (let [totbytes (count bytestr)
        splitidx (- totbytes 128)]
  (get (split-at splitidx bytestr) 1)))

;generates an id3 map based upon the structure defined in
;id3struct map
;basically uses the id3struct map as parse instructions
(defn id3-factory [id3bytes]
  (loop [idef id3struct 
         ibytes id3bytes
         builder {}]
    (if (empty? idef)
      builder
      (let [sentry (first idef)
            skey (first (keys sentry))
            slength (first (vals sentry))
            id3entry {skey (bytes-to-str (take slength ibytes))}
            restbytes (get (split-at slength ibytes) 1)]
          (recur (rest idef) 
                 restbytes 
                 (merge id3entry builder)))))) 

;composite function to reduce file loading, extracting id3 tag and
;converting into a nice map, into a single step
(defn load-id3 [mp3]
  (id3-factory (get-id3-bytes (loadfile mp3))))

;converts a keyword to a camelcase string
(defn to-title [keyword]
  (let [kname (name keyword)
        head-kname (first kname)
        rest-kname (rest kname)]
    (apply str (Character/toUpperCase head-kname) rest-kname)))

;pads a string at the front with spaces if string is
;under the specified length
(defn f-pad [length word]
  (let [to-pad (- length (count word))
        padding (apply str (repeat to-pad " "))]
    (str padding word)))

;prints a single formatted line of info
(defn print-info [field id3]
  (let [title (f-pad 10 (to-title field))
        info (id3 field)]
    (println title " : " info)))
    
;main function, takes the mp3 to load, and a map of fields to print
(defn pp-tags [mp3 fields]
  (let [id3 (load-id3 mp3)]
    (println "ID3v1 Tags for " mp3)
    (println "==================================================")
    (doseq [field fields]
      (print-info field id3))
    (println "--------------------------------------------------")))
        
        
      
      

  

          
        
  
