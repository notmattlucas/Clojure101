(ns wk3.id3-test
  (:use clojure.test)
  (:use wk3.id3))

(def bytestr (loadfile "resources/song.mp3"))
(def id3bytes (get-id3-bytes bytestr))
(def id3 (id3-factory id3bytes))

(deftest test-loadfile
  (is (= 3701050 
        (count bytestr))))

(deftest test-get-id3-bytes
  (is (= 128 (count id3bytes))))

(deftest test-found-tag
  (is (= "TAG" 
        (bytes-to-str (take 3 id3bytes)))))

(deftest test-to-title
  (is (= "Hello" (to-title :hello))))

(deftest tet-f-pad
  (is (= "     hello" (f-pad 10 "hello"))))

(pp-tags "resources/song.mp3" [:title :artist :album :year])

(run-tests)
