(ns wk4.gol.main
  (:use [wk4.gol.swingfactory])
  (:use [wk4.gol.ui_board])
  (:use [wk4.gol.model])
  (:import (java.awt.event ActionListener))
  (:import (javax.swing JFrame JLabel JTextField JPanel JButton JComponent)))

;text boxes to hold the width and height
(def width (input 3 "0"))
(def height (input 3 "0"))

(def inputs
  (grid
    [[(label "Width") width]
     [(label "Height") height]]))
   
(defn create-board [w h]
  "Starts the game of life"
  (start-game w h
    (random-matrix w h)))

(declare settings)

;proxy action for settings dialog
(def start-action
  (proxy [ActionListener] []
           (actionPerformed [e]
             (let [w (Integer/parseInt (.getText width))
                   h (Integer/parseInt (.getText height))]
               (create-board  w h)
               (.hide settings)))))

(def but (button "Start" start-action))

;definition for settings dialog
(def content
  (panel 
    (header "Game of Life")
    (border "Settings"
      (panel
        inputs
        but))))

(def settings (frame content 200 160))


