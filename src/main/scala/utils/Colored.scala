package utils

import scala.language.dynamics
import scala.language.implicitConversions

object Colored {
  implicit def stringToColered(s:String) = Colored(s)
  implicit def ColoredToString(c:Colored) = c.toString
  val attributes = Map(
    'clear         ->   0,
    'reset         ->   0,
    'bold          ->   1,
    'dark          ->   2,
    'italic        ->   3,
    'underline     ->   4,
    'underscore    ->   4,
    'blink         ->   5,
    'rapid_blink   ->   6,
    'negative      ->   7,
    'concealed     ->   8,
    'strikethrough ->   9,
    'black         ->  30,
    'red           ->  31,
    'green         ->  32,
    'yellow        ->  33,
    'blue          ->  34,
    'magenta       ->  35,
    'cyan          ->  36,
    'white         ->  37,
    'onBlack      ->  40,
    'onRed        ->  41,
    'onGreen      ->  42,
    'onYellow     ->  43,
    'onBlue       ->  44,
    'onMagenta    ->  45,
    'onCyan       ->  46,
    'onWhite      ->  47
  ).map{case (k, v) => (k, "\033[%dm" format v) }

  private val Reset = escape('reset) getOrElse(0)

  private def escape(attr:Symbol):Option[String] = attributes.get(attr)
  private def escape(attr:String):Option[String] = escape(Symbol(attr))

  private def escaped(attr:Symbol)(s: => String):Colored = Colored( escape(attr) + s )
  private def escaped(attr:String)(s: => String):Colored = escaped(Symbol(attr))(s)

  def apply(s:String) = new Colored{ val str = s }
  val colored = Colored("")
}

trait Colored extends Dynamic {
  val str:String

  def +(other:String):Colored = Colored( this.toString + other)
  def +(other:Colored):Colored = Colored( this.toString + other.toString )

  def colored = this

  def applyDynamic(attr: String)(args: Any*):Colored =
    coloring(attr) + args.headOption.getOrElse("")


  def selectDynamic(attr: String) = {
    coloring(attr)
  }
  private def coloring(attr:String) =
    Colored.escape(attr) map{ esc => Colored(esc + str) } getOrElse(this)

  override def toString = str + Colored.Reset

}