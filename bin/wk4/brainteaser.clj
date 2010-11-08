(ns wk4.brainteaser)

(def #^{:private true} myvar "myvalue")

(println (meta myvar))

(println (meta #'myvar))