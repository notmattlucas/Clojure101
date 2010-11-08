(ns wk2.book-list-test
  (:use clojure.test)
  (:use wk2.book-list))

(defn set-up [f]
  (dosync
    (ref-set book-libary {}))
  (f))

(use-fixtures :each set-up)

(def mybook (struct book "isbn" "title" ["author"]))
(def yourbook (struct book "nbsi" "eltit" ["rohtua"]))

(deftest test-addbook
  (let [coll (add-book {} mybook)]
    (is (= 1 (count coll)))))
  
(deftest test-rembook
  (let [coll (remove-book {"isbn" mybook} mybook) ]
    (is (= 0 (count coll)))))

(defn lib-count []
   (count @book-libary))

(deftest test-libary
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (is (= 1 (lib-count)))
    (rm-from-lib mybook)
    (is (= 0 (lib-count)))))
    
(deftest test-search-single
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (is (= 2 (lib-count)))
    (let [sRes (search-single :title "eltit" @book-libary)]
      (is (= 1 (count sRes))))))

(deftest test-search-one
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (is (= 2 (lib-count)))
    (let [sRes (search {:title "eltit"})]
      (is (= 1 (count sRes))))))

(deftest test-search-zero
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (is (= 2 (lib-count)))
    (let [sRes (search {:title "eltit" :authors ["Stephen King"]})]
      (is (= 0 (count sRes))))))

(deftest test-add-loan
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (is (= 2 (lib-count)))
    (add-loan "matt" {:isbn "isbn"})
    (let [b (@book-libary "isbn")]
      (is (= "matt" ((b :loan-data) :person))))))

(deftest test-add-bad-loan
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (is (= 2 (lib-count)))
    (add-loan "matt" {:isbn "bad-isbn"})
    (is (= 2 (lib-count)))))

(deftest test-accept-return
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (is (= 2 (lib-count)))
    (add-loan "matt" {:isbn "isbn"})
    (let [b (@book-libary "isbn")]
      (is (= "matt" ((b :loan-data) :person))))
    (accept-return {:isbn "isbn"})
    (let [b (@book-libary "isbn")]
      (is (= nil (b :loan-data))))))
    

(run-tests)



;(add-loan person item)
 
 ;   (accept-return person item)
 
;`add-loan` should accept optional keyword arguments:
 
 ;   (add-loan person item :return-by some-date)