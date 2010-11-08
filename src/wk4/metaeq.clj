(ns wk4.metaeq
  (:use clojure.test))

(def some-val [2])
(def copy-val some-val)
(def #^{:magic-number 2} meta-val some-val)
(def meta-val-2 (with-meta some-val {:magic-number 2}))
(def meta-val-3 #^{:magic-number 2} some-val)
    

(deftest tryall []
	(is (= some-val copy-val)) ;equivalent values so are equal
	(is (= some-val meta-val)) ;equivalent values so are equal
	(is (= some-val meta-val-2)) ;equivalent values so are equal
	(is (= some-val meta-val-3)) ;equivalent values so are equal
	 
	(is (identical? some-val copy-val)) ;copy-val points at the same object as some-val, so their identity is the same
	(is (identical? some-val meta-val)) ;some-val points at the same object as meta-val, so their identity is the same
	(is (not (identical? some-val meta-val-2))) ;with-meta returns another object with same value, so identity does not match
	(is (identical? some-val meta-val-3)) ;some-val points at the same object as meta-val-3, so their identity is the same
	 
	(is (= (meta meta-val) nil)) ;not referencing the Var but it's value, so we get nil
	(is (= (meta meta-val-2) {:magic-number 2})) ;variant on defn with metadata last - seems to tie the metadata to the referenced object not the Var
	(is (= (meta meta-val-3) nil)) ;not referencing the Var but it's value, so we get nil
	
	(is (= 2 (:magic-number (meta #'meta-val)))) ;metadata stored against the Var itself, so we get 2 back
	(is (nil? (:magic-number (meta #'meta-val-2)))) ;metadata stored against the referenced object not the var, so we get nil
	(is (nil? (:magic-number (meta #'meta-val-3))))) ;def not properly defined, metadata reader macro must come before the form, so we get nil

(run-tests)
