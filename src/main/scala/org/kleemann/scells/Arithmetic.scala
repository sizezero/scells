package org.kleemann.scells

trait Arithmetic { this: Evaluator =>
  operations += (
    "add"  -> { case List(x,y) => x + y },
    "sub"  -> { case List(x,y) => x - y },
    "div"  -> { case List(x,y) => x / y },
    "mul"  -> { case List(x,y) => x * y },
    "mod"  -> { case List(x,y) => x % y },
    
    // scala style guide says the obscure fold operators should not be used; I agree
    
    //"sum"  -> { case xs => (0.0 /: xs)(_ + _) },
    //"prod" -> { case xs => (1.0 /: xs)(_ * _) }
    
    "sum"  -> { case xs => xs.foldLeft(0.0){_ + _} },
    "prod" -> { case xs => xs.foldLeft(1.0){_ * _} }
  )
}