(ns wk2.book-list)

;book
(defstruct book :isbn :title :authors)

;- - - - pure - - - -
;{book} x book -> {book}
(defn add-book [book-coll book]
  (assoc book-coll (book :isbn) book))
  
;{book} x book -> {book}
(defn remove-book [book-coll book]
  (let [isbn (book :isbn)]
    (dissoc book-coll isbn)))
       
;- - - - stateful - - - -
;Libary map of ISBN -> Book
(def book-libary (ref {}))

(defn add-to-lib [book]
  (dosync
    (alter book-libary add-book book)))

(defn rm-from-lib [book]
  (dosync
    (alter book-libary remove-book book)))

;pure search function
(defn search-single [criteria value libary]
  (filter #(= value (% criteria)) (vals libary)))

(defn search [query-map]
  (let [f (first query-map) r (rest query-map)]
    (if (= f nil)
        @book-libary
	    (search-single (key f) (val f) 
	      (search r)))))

(defn check-out [person item]
  (assoc item :loan-data {:person person}))

(defn return [item]
  (dissoc item :loan-data))

(defn add-loan [person item]
  (let [to-lend (first (search item))]
    (if (not (nil? to-lend))
    (let [checked-out (check-out person to-lend)]
      (add-to-lib checked-out)))))

(defn accept-return [item]
  (let [to-return (first (search item))]
    (let [returned (return to-return)]
      (add-to-lib returned))))
