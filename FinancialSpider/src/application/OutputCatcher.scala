//This class is taken from an open source library written by Frank Teubler and thus my entire program is distributed under a non commerical apache open source license. 
package application

import java.io.OutputStream 
import java.io.PrintStream
import javax.swing.SwingUtilities
import scala.swing.TextArea
import scala.swing.Swing.Runnable

/**
 * catches data from OutputStream and appends the data as text
 * @author Frank Teubler
 */
trait OutputCatcher {
  self: TextArea =>

  /* empty method to log the last output position
   * should be overridden in subclasses
   */
  def lastPosition( position:Int ) {}

  lazy val outputStream = new OutputStream() { 

    /* abstract method of OutputStream
     */
    def write( b:Int ) {
      SwingUtilities.invokeLater (
        Runnable{
          append( b.toChar.toString )
          lastPosition( caret.position )
        }
      )
    }

    /* more efficient
     */
    override def write( b:Array[Byte], off:Int, len:Int) {
      val text = new String(b, off, len);
      SwingUtilities.invokeLater(
        Runnable{
          append(text)
          lastPosition( caret.position )
        }
      )
    }
  };

  /* catches Console.out */
  def catchConsoledOut {
    Console.setOut( outputStream)
  }

  /* catches scala Console.err*/
  def catchConsoleErr {
    Console.setErr( outputStream )
  }

  /* catches System.out */
  def catchSystemOut {
    System.setOut( new PrintStream( outputStream ))
  }

  /* catches System.err */
  def catchSystemErr {
    System.setErr( new PrintStream( outputStream ))
  }

}