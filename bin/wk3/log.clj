(ns wk3.log
  (:use [clojure.contrib.string :only (split)])
  (:use [clojure.contrib.duck-streams :only (reader)]))

;Assumptions of the format of the log file are 
; - Header lines are delimited by the same constant
; - The line following the header line defines the arity of the following section (title line)
; - If a line does not match the arity expected it is ignored

;constants
(def header-tag "###")
(def header-regex (re-pattern (str "^" header-tag ".*")))

;returns true if the string matched (contains) the supplied regex
(defn re-match? [regex string]
  (> (count (re-seq regex string)) 0))

;splits a string into tokens by its whitespace
(defn split-by-ws [line]
  (split #"\s+" line))

;splits a map of strings into a map of token vectors
(defn split-all-by-ws [lines]
  (map split-by-ws lines))

;returns true if the line supplied is a header line
(defn is-header? [line]
  (re-match? header-regex line)) 

;returns a seq of seqs, representing the log grouped by header
(defn group-by-header [lines]
   (map #(apply concat  %) 
     (partition 2
       (partition-by is-header? lines))))

;reads a log file and returns as a seq of groupings by header
(defn read-log [file]
  (let [rdr (reader file)]
    (group-by-header 
        (line-seq rdr))))
    
;defines the arity of a string (how many tokens it contains)
(defn define-arity [line]
  (count (split-by-ws line)))

;assetion that arity of passed in token vector matched expected
(defn arity-ok [arity args]
  (= arity (count args)))

;extracts the header data (minus header delimiters)
(defn extract-head-data [head]
  (let [head-data (split-by-ws head)]
    (filter #(not (is-header? %)) head-data))) 

;parses a single line of data into a vector log entry
(defn parse-line [head-data line-data]
  (concat head-data line-data))

;parses the body of a group
;note a group is everything under one header
(defn parse-body [head-data body]
  (let [title (first body)
        arity (define-arity title)
        content (rest body)]
    (map #(parse-line head-data %) 
      (filter #(arity-ok arity %)
        (split-all-by-ws content)))))

;parses the content of a single group
(defn parse-group [group]
  (let [head-data (extract-head-data (first group))
        log (rest group)]
    (parse-body head-data log)))

;main parse method
(defn parse [logfile]
  (let [groups (read-log logfile)]
      (apply concat
        (map #(parse-group %) groups))))
