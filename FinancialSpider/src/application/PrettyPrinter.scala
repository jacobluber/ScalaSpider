package application

import scala.collection.parallel.mutable.ParArray

trait PrettyPrinter {
  def makeTuple(a:String, b:ParArray[String]):(Int,String) = (b.length,a)
}
