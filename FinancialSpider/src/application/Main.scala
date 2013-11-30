package application

import java.awt.Dimension
import java.io.File
import java.net.URL
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.GridPanel
import scala.swing.ListView
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.ScrollPane
import scala.swing.event.MouseClicked
import scala.sys.process.urlToProcess
import org.xml.sax.InputSource
import java.io.FileInputStream
import scala.xml.XML
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import scala.collection.parallel.mutable.ParArray
import scala.collection._
import scala.swing.BorderPanel
import scala.swing.event.ButtonClicked
import scala.swing.SimpleSwingApplication
import scala.swing.TextArea
import java.util.logging.Logger
import java.util.logging.Level
import scala.actors.Actor

object Main {
  // an example below of how some of the networking libraries could be used 
  //new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml") #> new File("Output.xml") !!
  //val currency = xml.XML.loadFile("Output.xml") \\ "@currency"
  //val rate = xml.XML.loadFile("Output.xml") \\ "@rate"
  //val x = currency.zip(rate)
  //val y = x.toList

  val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
  val parser = parserFactory.newSAXParser()
  val source = new org.xml.sax.InputSource("http://rates.fxcm.com/RatesXML")
  val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
  val yy = adapter.loadXML(source, parser)
  val k = (yy \\ "@symbol").map(_.text).toParArray
//  <([@symbol]+) *[^/]*?>
  val price = (yy \\ "Bid").map(_.text).toParArray
//  <([Bid]+) *[^/]*?>
  
  
  def main(args: Array[String]) = {
    val start = new InputWindow(k, price)
    start.Open
    
  }
}
