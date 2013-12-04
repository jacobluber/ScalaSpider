//code from http://debasishg.blogspot.com/2008/06/playing-around-with-parallel-maps-in.html; I merely used this to inspire my actor based parallel map
package application

class Mapper[A, B:Manifest](l: List[A], f: A => B) {
  def pmap = {
    val buffer = new Array[B](l.length)
    val mappers =
      for(idx <- (0 until l.length).toList) yield {
        scala.actors.Futures.future {
          buffer(idx) = f(l(idx))
      }
    }
    for(mapper <- mappers) mapper()
    buffer
  }
}