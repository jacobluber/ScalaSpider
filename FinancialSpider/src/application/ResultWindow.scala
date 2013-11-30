package application

import scala.swing.Frame
import java.awt.Dimension
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.ListView
import scala.collection.parallel.mutable.ParArray
import scala.swing.Table
import scala.swing.Label
import scala.swing.ScrollPane
import scala.swing.TextArea
import scala.swing.GridPanel
import scala.swing.BorderPanel

class ResultPanel(searcha: String, searchb: String, searchc: String, url: String) {
  def Open(): Unit = {
    val data = new NewsGenerator(url, searcha, searchb, searchc)
    val listdata = data.generate
    val finalr = new ListView(listdata)
    val resultPanel = new ScrollPane
    val resultframe = new Frame
    resultframe.title = "ForexSpider Search Results"
    resultframe.preferredSize = new Dimension(1000, 800)
    resultframe.contents = bord
    resultPanel.contents = finalr
    resultframe.open
    println("Hello and Welcome to the Spider Search Analytics Page!")
    println("In this window you will find a representation of the number of news bytes each site contains as a [balanced] BST.")
    println("Below the [balanced] BST you will find a ListView of everything the spider found.")
    println()
    println()
    println("***Spacing on the graphical [balanced] BST representation is still being perfected in this iteration of the program***")
    println()
    println()
    println("The [balanced] BST where each number represents the number of news-bytes found on a linked site:")
    data.PrettyPrinter

    lazy val top = new ScrollPane {
      val area = new TextArea(20, 40) with OutputCatcher { //Streams!
        catchSystemOut
        catchSystemErr
      }
      contents = new BorderPanel {
        layout(new ScrollPane(area)) = BorderPanel.Position.Center
      }
    }
  
    
    lazy val bord = new BorderPanel() {
     //layout += starts -> BorderPanel.Position.East
     layout += resultPanel -> BorderPanel.Position.Center
     layout += top -> BorderPanel.Position.North
     //layout += url -> BorderPanel.Position.South
    }
    
    val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
    val parser = parserFactory.newSAXParser()
    val source = new org.xml.sax.InputSource("http://rates.fxcm.com/RatesXML")
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
    val yy = adapter.loadXML(source, parser)
    val k = (yy \\ "@symbol").map(_.text).toParArray
    val price = (yy \\ "Bid").map(_.text).toParArray


  }
}
