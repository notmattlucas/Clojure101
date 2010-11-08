(ns wk4.meta
  (:use clojure.set))

(def vars (map val (ns-publics 'clojure.core)))

(println "How many public vars are there in `clojure.core`?")
(def ans (count vars))
(println ans)

(println "Of these, how many are macros?")
(def macros 
  (count
    (filter #((meta %) :macro) vars)))
(println macros)

(println "How many characters of documentation are there in `clojure.core`?")
(def doc-lines
  (count
    (apply str
      (map #((meta %) :doc) vars))))
(println doc-lines)

(println "How many of these vars have no line number?")
(defn no-lines [vars]
  (filter #(not ((meta %) :line)) vars))
(def no-lines
  (count
    (no-lines vars)))
(println no-lines)

(println "Of those that have line numbers, if you were to order them by line number, which is the first? last? hundredth?")
(defn by-lines [vars]
  (filter #((meta %) :line) vars))
(def ordered-by-line
  (sort
    (map #((meta %) :line) 
      (by-lines vars))))
(println "first : " (first ordered-by-line))
(println "last : " (last ordered-by-line))
(println "hundredth : " (nth ordered-by-line 100))


(println "What types of metadata appear that are not described above?")
(def mentioned (set [:private :doc :test :tag :file :line :name :ns :macro :arglists]))
(def metatypes
  (set
   (reduce into
	  (map #(keys %)
	    (map #(meta %) vars)))))
(def others (difference metatypes mentioned))
(println others)













