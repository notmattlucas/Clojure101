(ns wk3.book-list-test
  (:use clojure.test)
  (:use wk3.book-list))

(defn set-up [f]
  (dosync
    (ref-set book-libary {}))
  (f))

(use-fixtures :each set-up)

(def mybook (struct book "isbn" "title" ["author"]))
(def yourbook (struct book "nbsi" "eltit" ["rohtua"]))
(def thirdbook (struct book "1234" "moretitle" ["author" "author2"]))

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
    
(deftest test-search-by-authors
  (do
    (is (= 0 (lib-count)))
    (add-to-lib mybook)
    (add-to-lib yourbook)
    (add-to-lib thirdbook)
    (is (= 3 (lib-count)))
    (is (= 2 
          (count 
            (search-by-author "author" @book-libary))))
    (is (= 0
          (count 
            (search-by-author "poop" @book-libary))))
    (is (= 1
          (multi-author @book-libary)))))

(run-tests)



;(add-loan person item)
 
 ;   (accept-return person item)
 
;`add-loan` should accept optional keyword arguments:
 
 ;   (add-loan person item :return-by some-date)