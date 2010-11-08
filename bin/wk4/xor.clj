(ns wk4.xor 
  (:use clojure.test))

(defmacro xor
  "Evaluates exprs one at a time, from left to right.  If only one form returns
  a logical true value (neither nil nor false), returns true.  If more than one
  value returns logical true or no value returns logical true, retuns a logical
  false value.  As soon as two logically true forms are encountered, no
  remaining expression is evaluated.  (xor) returns nil."
  ; write your macro replacing the next line
  ([] nil)
  ([x] x)
  ([x y] `(let [x# (boolean ~x) y# (boolean ~y)]
                (not (= x# y#))))
  ([x y & next]
    `(let [x# (boolean ~x) y# (boolean ~y)]
       (if (and x# y#)
         false
         (xor (xor x# y#) ~@next)))))



(defn- doit
  "Returns val and creates the side effect of printing num."
  [num val]
  (print num)
  val)

(deftest nullary
  (is (not (xor))))

(deftest unary
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 true)))
    (is (= "1" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil))))
    (is (= "1" (.toString *out*)))))

(deftest binary
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil) (doit 2 nil))))
    (is (= "12" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 true) (doit 2 true))))
    (is (= "12" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 nil) (doit 2 true)))
    (is (= "12" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 false) (doit 2 nil))))
    (is (= "12" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 true) (doit 2 nil)))
    (is (= "12" (.toString *out*)))))


(deftest ternary
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil) (doit 2 nil) (doit 3 nil))))
    (is (= "123" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 nil) (doit 2 nil) (doit 3 true)))
    (is (= "123" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 nil) (doit 2 true) (doit 3 nil)))
    (is (= "123" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil) (doit 2 true) (doit 3 true))))
    (is (= "123" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 true) (doit 2 nil) (doit 3 nil)))
    (is (= "123" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 true) (doit 2 nil) (doit 3 true))))
    (is (= "123" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 true) (doit 2 true) (doit 3 nil))))
    (is (= "12" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 true) (doit 2 true) (doit 3 true))))
    (is (= "12" (.toString *out*)))))

(deftest n-ary
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil) (doit 2 nil) (doit 3 nil)
                  (doit 4 nil) (doit 5 nil) (doit 6 nil))))
    (is (= "123456" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil) (doit 2 true) (doit 3 nil)
                  (doit 4 true) (doit 5 nil) (doit 6 nil))))
    (is (= "1234" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (xor (doit 1 true) (doit 2 nil) (doit 3 nil)
             (doit 4 nil) (doit 5 nil) (doit 6 nil)))
    (is (= "123456" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 true) (doit 2 true) (doit 3 true)
                  (doit 4 true) (doit 5 true) (doit 6 true))))
    (is (= "12" (.toString *out*))))
  (binding [*out* (java.io.StringWriter.)]
    (is (not (xor (doit 1 nil) (doit 2 nil) (doit 3 nil)
                  (doit 4 nil) (doit 5 true) (doit 6 true))))
    (is (= "123456" (.toString *out*)))))


(clojure.test/run-tests 'wk4.xor)