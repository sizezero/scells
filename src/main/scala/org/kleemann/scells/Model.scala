package org.kleemann.scells

import swing._

class Model(val height: Int, val width: Int)
    extends Evaluator with Arithmetic {
  
  case class Cell(row: Int, column: Int) extends Publisher {
    
    private var f: Formula = Empty
    def formula: Formula = f
    def formula_=(f: Formula) {
      for (c <- references(formula)) deafTo(c)
      this.f = f
      for (c <- references(formula)) listenTo(c)
      value = evaluate(f)
    }
    
    private var v: Double = 0
    def value: Double = v
    def value_=(w: Double) {
      if (!(v == w || v.isNaN && w.isNaN)) {
        v = w
        publish(ValueChanged(this))
      }
    }
    
    override def toString = formula match {
      case Textual(s) => s
      case _ => value.toString
    }
    
    // re-evaluates the model value but does not cause a re-display.  Table needs to 
    // listen to ValueChanged as well.
    reactions += {
      case ValueChanged(_) => value = evaluate(formula)
    }
  }

  // it seems like bad form to have swing events in the model object;
  // moving to another ui would require lots of changes
  
  case class ValueChanged(cell: Cell) extends event.Event

  // It looks like the scala libaries have changed since this example was written
  //val cells = new Array[Array[Cell]](height)(width)
  val cells = Array.ofDim[Cell](height, width)
  
  for (i <- 0 until height; j <- 0 until width)
    cells(i)(j) = new Cell(i, j)
}