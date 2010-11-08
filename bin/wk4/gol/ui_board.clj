(ns wk4.gol.ui_board
  (:use [wk4.gol.swingfactory])
  (:use [wk4.gol.model])
  (:import (javax.swing JFrame JLabel JTextField JPanel JButton JComponent Timer))
  (:import (java.awt.event ActionListener)))

;constants
(def x-px 5)
(def y-px 5)
(def turn-millis 50)

;state
(def life (atom []))
(def board-width (ref 0))
(def board-height (ref 0))

(defn- fillRect [g x y]
  "Fills the rectangle at point x y"
  (.fillRect g (* x x-px) (* y y-px) x-px y-px))

(defn- fillIfOn [g x y on]
  "Fills a rectangle if it is on"
  (if on
    (fillRect g x y)))

(defn- paintAll [g cells]
  "Paints all cells containing a true value"
  (let [xcnt (count (first cells))
        ycnt (count cells)]
    (doseq [x (range 0 xcnt)
          y (range 0 ycnt)]
      (fillIfOn g x y ((cells y) x)))))

(defn- update-board []
  "updates the game board"
  (reset! life (evolve @life)))

(defn- draw-board []
  "Draws the initial game board"
  (proxy [JComponent ActionListener] []
         (paint [g]
           (paintAll g @life))
         (actionPerformed [e]
           (update-board)
           (.repaint this))))

(defn- setup-board [width height model]
  "Sets up state for the game of life"
  (dosync
    (ref-set board-width width)
    (ref-set board-height height))
  (reset! life model)
  (draw-board))
  
(defn start-game [width height model]
  "Sets up and kicks off the game"
  (let [board (setup-board width height model)
        px-width (* width x-px)
        px-height (* height y-px)
        timer (Timer. turn-millis board)]
    (frame board px-width px-height)
    (.start timer)))



