package application

import scala.collection.parallel.mutable.ParArray
import scala.collection.parallel.traversable2ops
import scala.actors.Futures._

class UrlSpidey(url: String, searcha: String, searchb: String, searchlast: String) extends PrettyPrinter { //Yay Classes!!


  val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
  val testparser = parserFactory.newSAXParser()
  val testsource = new org.xml.sax.InputSource("http://www.bloomberg.com/news/currencies/")
  val testadapter = new scala.xml.parsing.NoBindingFactoryAdapter
  val testy = testadapter.loadXML(testsource, testparser)
  
  def findUrlsToParse(a: String): List[String] = {
    val testparser2 = parserFactory.newSAXParser()
    val testsource2 = new org.xml.sax.InputSource(a)
    val testadapter2 = new scala.xml.parsing.NoBindingFactoryAdapter
    val testy2 = testadapter2.loadXML(testsource2, testparser2)
    val kk2 = (testy2 \\ "@href").map(_.text)
    val semifilteredresult = kk2
    val filteredresult2 = semifilteredresult.filter(x => x.contains("http"))
    //val testRegex = semifilteredresult.r 
    //testRegex.replaceAll("\\s","ftp")
    val filteredresult = filteredresult2.filter(x => x.contains(".xml") || x.contains("XML") || x.contains(".html"))
    val item = filteredresult.filterNot(x => x.contains("css") || x.contains(".jpg") || x.head == '/' || x.contains(".js"))
    item.toList
  }
  

  
  var arrayOfTuples = Array[(Int,String)]()
  
  def getTupleArray():Array[(Int,String)] = arrayOfTuples
  
  def getInterestingStuffs(a: List[String], searcha: String, searchb: String, searchc: String): List[String] = {
    def getInterestingStuff(a: String): ParArray[String] = {
      try {
      val testparser1 = parserFactory.newSAXParser()
      val testsource1 = new org.xml.sax.InputSource(a)
      val testadapter1 = new scala.xml.parsing.NoBindingFactoryAdapter
      val testy1 = testadapter1.loadXML(testsource1, testparser1)
      val kk1 = (testy1 \\ "p").map(_.text)
      def filterRes(bn: String): String = {
        if ((bn.contains(searchb) == true || bn.contains(searchb) == true || bn.contains(searchlast)) == true) { bn }
        else { "randomnonsense" }
      }
      val kksemifinal = kk1.map(filterRes)
      val kkfinal = kksemifinal.filterNot(x => x == "randomnonsense")
      val res1 = kkfinal.toParArray
      //val resulty = res1.r
      //String resultString = subjectString.replaceAll("\\P{L}+", "");     
      //help utilized from StackOverflow here
      val tup = makeTuple(a,res1)
      if(arrayOfTuples.length == 0){arrayOfTuples = Array(tup)} else {arrayOfTuples = Array(tup) ++  arrayOfTuples}
      res1
      } catch {
        case e:java.security.PrivilegedActionException =>
          val z:ParArray[String] = ParArray[String]()
          z
        case e:java.io.IOException =>
          val z:ParArray[String] = ParArray[String]()
          z
        case e: Throwable => 
          val z:ParArray[String] = ParArray[String]()
          z
        
      }
    }
    
    //using the Mapper class
    //val mappera = new Mapper (a,getInterestingStuff)
    //val theres = mappera.pmap.toList
    
    //using the built in parallel collection
    //val theres = a.par.map(getInterestingStuff).toList //Yay Multithreading
    
    val input = a.grouped(10).toParArray
    val splits = input.par.map(url => future {url.map(getInterestingStuff)})
    val theres = splits.par.flatMap(_()).toList
    
    def listy(a: ParArray[String]): List[String] = a.toList
    //val theresf = theres.map(listy)
    val mapperb = new Mapper (theres,listy)
    val theresf = mapperb.pmap
    val rr = theresf.par.filter(x => x != List())
    val r = rr.foldLeft(rr.head)((A, B) => A ::: B)
    r
  }

}