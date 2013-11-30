//Some of the logic used on this page comes from work I've done in Functional. All code is original for this project as it has been refactored from Haskell to Scala. 
package application

class TreeOps(inputedList: List[Int]) extends Tree {

  
  def generate():Unit = {
    val bstTree = makeTree(inputedList,EmptyLeaf)
    prettyPrint(bstTree)
  }

  def inserter(x: Int, y: Tree): Tree = y match {
    case Leaf() => balancer(insertLeaf(x, Leaf()))
    case EmptyLeaf => balancer(insertLeaf(x, Leaf()))
    case Node(v, l, r) => balancer(insertNode(x, Node(v, l, r)))
  }

  def insertLeaf(x: Int, y: Leaf): Node = Node(x, EmptyLeaf, EmptyLeaf)

  def insertNode(ty: Int, y: Node): Node = {
    if (ty == y.value) {
      var z = Node(y.value, y.left, y.right)
      z
    } else if (ty < y.value) {
      val recur = inserter(ty, y.left)
      var z = Node(y.value, recur, y.right)
      z
    } else {
      val recur2 = inserter(ty, y.right)
      var z = Node(y.value, y.left, recur2)
      z
    }
  }

  def height(x: Tree): Int = x match {
    case Leaf() => 0
    case EmptyLeaf => 0
    case Node(v, l, r) => 1 + math.max(height(l), height(r))
  }

  def replicate(a: Int, b: String, c: String): String = {
    if (a == 0) { c }
    else {
      replicate(a - 1, b, c ++ b)
    }
  }

  def ppAux(a: Int, b: Tree): List[String] = b match {
    case EmptyLeaf => {
      val indent = math.pow(2, a) - a
      val space = replicate(indent.toInt, " ", " ")
      val topLine = replicate(indent.toInt, " "," ") ++ "L" ++ space 
      List(topLine)
    }
    case Node(v, l, r) => {
      val ls = ppAux(a - 1, l)
      val rs = ppAux(a - 1, r)
      val indent = math.pow(2, a) - a
      val space = replicate(indent.toInt, " ", " ")
      val topLine = space.tail ++ v.toString ++ space //{
      val subres = (ls, rs).zipped map (_ ++ _)
      val res = topLine :: subres
      res
    }
  }

  def makeTree(a: List[Int], b: Tree): Tree = {
    if (a.isEmpty == true) { b }
    else { makeTree(a.tail, inserter(a.head, b)) }
  }

  def rotateL(a: Tree): Tree = a match {
    case EmptyLeaf => { EmptyLeaf }
    case Node(v, l, EmptyLeaf) => { Node(v, l, EmptyLeaf) }
    case Node(v, l, Node(y, left, r)) => { Node(y, Node(v, l, left), r) }
  }

  def rotateR(a: Tree): Tree = a match {
    case EmptyLeaf => { EmptyLeaf }
    case Node(v, EmptyLeaf, r) => { Node(v, EmptyLeaf, r) }
    case Node(v, Node(y, l, r), right) => { Node(y, l, Node(v, r, right)) }
  }

  def size(a: Tree): Int = a match {
    case EmptyLeaf => { 0 }
    case Node(v, l, r) => { size(l) + size(r) + 1 }
  }

  def balancer(a: Tree): Tree = a match { //YAY ADVANCED RECURSION!!
    case EmptyLeaf => { EmptyLeaf }
    case Node(v, l, r) => {
      val imbalance = size(l) - size(r)
      if (math.abs(imbalance) > 1) {
        if (imbalance > 1) {
          val x = rotateR(a)
          x
          //balancer(x)
        } else {
          val x = rotateL(a)
          x
          //balancer(x)
        }
      } else {
        Node(v, balancer(l), balancer(r))
      }
    }

  }

  def prettyPrint(y: Tree): Unit = {
    val subtree = ppAux(height(y), y)
    subtree.foreach(println)
  }


}

  
