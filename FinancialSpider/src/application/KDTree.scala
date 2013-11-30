package application

class KDTree[A<%(Int)=>Double](val points:Seq[A], val dimensions:Int) {
  case class Node(lchild:Node,rchild:Node,points:Seq[A],dsplit:Int, splitloc:Double) 
  val root = buildTree(points)
  def buildTree(points:Seq[A]):Node = {
    if (points.size == 1) Node(null, null, points, -1, 0)
    else {
      val diffs = for (i <- 0 until dimensions) yield
         points.map(p=>p(i)).max - points.map(p=>p(i)).min
      val (max,dsplit) = diffs.zipWithIndex.foldRight((-1.0,-1))((a,b)=> {
        if (b._1 > a._1) b else a
      })
      val sortedPoints = points.sortBy(p => p(dsplit))
      val splitloc = sortedPoints(sortedPoints.length/2)(dsplit)
      Node(buildTree(sortedPoints.take(sortedPoints.length/2)),buildTree(sortedPoints.drop(sortedPoints.length/2)),Seq(),dsplit,splitloc)
    }
  }
}