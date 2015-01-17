package model

object DollStyle {
  case object Normal extends DollStyle("ノーマル","N","Chocolate")
  case object Power extends DollStyle("パワー","P","Red")
  case object Defence extends DollStyle("ディフェンス","D","Blue")
  case object Speed extends DollStyle("スピード","S","Forestgreen")
  case object Assist extends DollStyle("アシスト","A","Deeppink")
  case object DefenceHakutaku extends DollStyle("ディフェンス（ハクタク）","DH","Blue")

  private val values = Array(Normal, Power, Defence, Speed, Assist, DefenceHakutaku)

  def getFromSymbol(symbol: String):Option[DollStyle] =
    values.find { x => x.symbol == symbol };
}

sealed abstract class DollStyle(
  val styleName: String,
  val symbol: String,
  val color: String
) {

}
