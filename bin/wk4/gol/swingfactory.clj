(ns wk4.gol.swingfactory
  (:import (javax.swing JFrame JLabel JTextField JPanel JButton JComponent))
  (:import (javax.swing.border TitledBorder))
  (:import (java.awt GridLayout Font Color)))

(defn label 
  "Creates a JLabel containing supplied text"
  [text]
  (JLabel. text))

(defn header
  "Creates a header label"
  [text]
  (let [hdr (label text)]
    (.setFont hdr (Font. "Dialog" 1 16))
    hdr))
    
(defn input 
  "Creates a JTextField of the supplied width" 
  [width default]
  (JTextField. default width))
    
(defn button 
  "Creates a JButton with supplied text"
  [text & actions]
  (let [button (JButton. text)]
  (if actions
    (doall
      (map #(.addActionListener button %) actions)))
  button))

(defn panel 
  "Creates a panel containing the supplied elements"
  [& elms]
  (let [container (JPanel.)]
    (doseq [elm elms]
      (.add container elm))
    container))
  
(defn border [title panel]
  "Creates a bordered panel using the supplied title"
  (.setBorder panel (TitledBorder. title))
  panel)

(defn grid 
  "Creates a 2D grid defined by the formation of the
   supplied 2D vector"
  [elms]
  (let [cols (count (first elms))
        rows (count elms)
        grid (GridLayout. rows cols 4 4)
        panel (JPanel.)]
    (.setLayout panel grid)
     (doseq [elm (reduce #(apply merge %1 %2) elms)]
      (.add panel elm))
     panel))

(defn frame
  "Creates a shows a frame with the specified content"
  [content width height]
  (doto (JFrame.)
    (.add content)
    (.setSize width height)
    (.setLocationRelativeTo nil)
    (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
    (.setVisible true)))



