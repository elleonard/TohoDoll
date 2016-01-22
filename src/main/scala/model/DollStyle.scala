package model

/**
 * スタイル
 */
object DollStyle {
  case object Normal          extends DollStyle("ノーマル",                "N", "Chocolate",  false)
  case object Power           extends DollStyle("パワー",                  "P", "Red",        false)
  case object Defence         extends DollStyle("ディフェンス",            "D", "Blue",       false)
  case object Speed           extends DollStyle("スピード",                "S", "Forestgreen",false)
  case object Assist          extends DollStyle("アシスト",                "A", "Deeppink",   false)
  case object DefenceHakutaku extends DollStyle("ディフェンス（ハクタク）","DH","Blue",       true)
  case object Extra           extends DollStyle("エクストラ",              "E", "Purple",     false)

  private val values = Array(Normal, Power, Defence, Speed, Assist, DefenceHakutaku, Extra)

  def getFromSymbol(symbol: String):Option[DollStyle] =
    values.find { x => x.symbol == symbol };
}

/**
 * スタイル
 *
 * styleName スタイルの名前（カタカナ）
 * symbol    スタイルの頭文字
 * color     スタイルの文字色
 * isSpecial 戦闘中に変化する特殊スタイル
 */
sealed abstract class DollStyle(
  val styleName: String,
  val symbol: String,
  val color: String,
  val isSpecial: Boolean
) {

}
