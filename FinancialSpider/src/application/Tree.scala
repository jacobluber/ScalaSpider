package application

abstract class Tree{
  case class Node(value: Int, left: Tree, right: Tree) extends Tree
  case class SNode(value: String, left: Tree, right: Tree) extends Tree
  case class Leaf() extends Tree
  case object EmptyLeaf extends Tree

}
 
  
  
 
