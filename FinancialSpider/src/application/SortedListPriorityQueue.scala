package application

import scala.collection.mutable.PriorityQueue

//class SortedListPriorityQueue[A : Manifest](comp: (A,A)=>Int) extends
//   PriorityQueue[A]{
class UnsortedArrayPriorityQueue[A : Manifest](comp: (A,A)=>Int){
  private var top = 0
  private var data = new Array[A](10)
//private class Node(var data:A, var prev:Node, var next:Node)
//private val end = new Node(new Array[A](1)(0),null,null)
//end.next = end
//end.prev = end


def enqueue(obj:A) {
  if(top >= data.length) {
    val tmp = new Array[A](data.length*2)
    Array.copy(data,0,tmp,0,data.length)
    data = tmp
  }
  data(top) = obj
  top += 1
  //val tmparr = Array(obj)
  //val tmp1 = data ++ tmparr
  //data = tmp1
}

def dequeue():A = {
  var p = data(0)
  for (i <- 1 until data.length) {
    if(comp(p,data(i)) > 0) {p = p}
    else if (comp(p,data(i)) < 0) {p = data(i)}
    else p = p
  }
  data(data.indexOf(p))=data(top-1)
  top = top -1
  p
}

def peek:A = data(top-1)

def isEmpty:Boolean = top==0
}