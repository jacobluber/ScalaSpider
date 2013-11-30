package application

class NewsGenerator(url: String, searcha: String, searchb: String, searchc: String) extends UrlSpidey(url: String, searcha: String, searchb: String, searchc: String) { //Yay Inheritance!
  private var thefinaltuple = Array((0,"blah"))
  def generate(): List[String] = {
    val wheretosearch = findUrlsToParse(url)
    val resultdata = getInterestingStuffs(wheretosearch, searcha, searchb, searchc)
    val tuple = getTupleArray
    thefinaltuple = tuple
    resultdata
  }
  def PrettyPrinter():Unit = {
    val tuple = thefinaltuple
    def makeIntList(a:Array[(Int,String)]):List[Int] = {
      def takeFirst(a:(Int,String)):Int = a._1
      val z = a.map (takeFirst)
      val x = z.toList
      x
    } 
    val bstInput = makeIntList(tuple)
    val trees = new TreeOps(bstInput)
    trees.generate
  }
}

