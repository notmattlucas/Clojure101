(ns wk4.macro
    (:use clojure.test))

(defmacro xor
  ([] nil)
  ([x] x)
  ([x y] (not (= x y)))
  ([x y z & next]
    `(if (and ~x ~y)
       false
         (let [xor# (xor ~x ~y)]
           (if (and xor# ~z)
              false
              (xor xor# ~z ~@next))))))

;---Tests---

(deftest nullary-test []
  (is (not (xor))))

(deftest unary-test []
  (is (xor true))
  (is (not (xor false))))

(deftest binary-test []
  (is (not (xor true true)))
  (is (not (xor false false)))
  (is (xor true false))
  (is (xor false true)))

(deftest n-ary-test []
  (is (not (xor true true false)))
  (is (not (xor false false true true)))
  (is (not (xor false false true true false)))
  (is (not (xor false true true true false false)))
  (is (xor true false false false))
  (is (xor false false true false)))

(run-tests)


  