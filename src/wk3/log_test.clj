(ns wk3.log-test
  (:use clojure.test)
  (:use wk3.log))

(deftest test-is-header
  (is (is-header? "### username 2010-03-30 03:50:00 ###")))

(deftest test-define-arity
  (is (= 7 (define-arity "USER         COMM       PID      PPID   %CPU %MEM       TIME"))))

(deftest test-read-log
  (let [log (read-log "resources/log.txt")]
	  (is (= 2 (count log)))
	  (is (= 9 (count (first log))))))


(deftest test-extract-head-data
  (is (= 3 (count (extract-head-data "### username 2010-03-30 03:50:00 ###")))))

(deftest test-arity-ok
  (is (arity-ok 5 [1 2 3 4 5])))

(deftest test-parse-line
  (is (= ["a" "b" "c" "d"] (parse-line ["a" "b"] ["c" "d"]))))

(deftest test-parse-body
  (is (= [["a" "b" "c" "d"]
          ["a" "b" "e" "f"]]
         (parse-body ["a" "b"]
           ["col1 col2" "c d" "e f"])))) 

(run-tests)

(def results (parse "resources/log.txt"))

(doseq [result results]
  (println result))