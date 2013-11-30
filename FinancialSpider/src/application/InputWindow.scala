package application

import scala.swing.Frame
import java.awt.Dimension
import scala.swing.GridPanel
import scala.swing.ScrollPane
import scala.swing.Button
import scala.swing.ListView
import scala.swing.event.MouseClicked
import scala.swing.MainFrame
import scala.swing.TextField
import scala.swing.TextComponent
import scala.swing.Label
import scala.collection.parallel.mutable.ParArray
import scala.swing.TextArea
import scala.swing.FlowPanel
import scala.swing.SplitPane
import scala.swing.Orientation
import scala.swing.BorderPanel
import scala.swing.Table
import scala.collection.mutable.ArrayBuffer

class InputWindow(country: ParArray[String], price: ParArray[String]) {
  def Open(): Unit = {
    val from2 = new TextField("the") //through refactoring/bugcatching I have been able to make my program work on a query that generates hundreds of news-bytes without throwing an error at about the same speed the previous version of the program worked
    lazy val snd = from2.text
    val lasts = new TextField("a")
    lazy val third = lasts.text
    val url = new TextField("http://news.google.com")
    lazy val enteredurl = url.text
    val in = new TextField("for")
    lazy val fst = in.text
    val ok = new Button("Go!")
    ok.listenTo(ok.mouse.clicks)
    ok.reactions += {
      case e: MouseClicked => {
        val result = new ResultPanel(fst, snd, third, enteredurl)
        result.Open
        frame.dispose
      }
    }
    lazy val frame = new MainFrame
    frame.title = "Forex Web Spider"
    frame.preferredSize = new Dimension(700, 400)
    val Griddy = new GridPanel(4, 1) {
      contents += in
      contents += from2
      contents += lasts
      contents += ok
    }
    val finalcountry = "country" :: country.toList
    val finalprice = "price" :: price.toList
    val zr = (country.toArray[Any], price.toArray[Any]).zipped map (Array(_, _)) //zipWith Martin Odersky stlye i.e. Yay Parametric Types!
    val thetable = new Table(zr, List("Country", "Price"))
    val framey = new ScrollPane

    framey.contents = thetable
    val texty = new TextArea()
    texty.lineWrap = true
    texty.editable = false
    texty.text = "Hello and welcome to the Forex Web Spider! In the center of this window you will find current Forex bid prices that the application downloaded upon launch. Please enter up to three search terms to guide the spider in the right of the window and a valid website URL that has no blind redirects and uses conventional URL formating in the bottom of the window and hit go to have the spider find related news-bytes. Also make sure that all required external libraries have been linked. "
    val bord = new BorderPanel() {
      layout += Griddy -> BorderPanel.Position.East
      layout += framey -> BorderPanel.Position.Center
      layout += texty -> BorderPanel.Position.North
      layout += url -> BorderPanel.Position.South
    }
    frame.contents = bord
    frame.open
  }
}
