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
  case object ExtraRed        extends DollStyle("エクストラ（赤）",        "ER","Purple",     false)
  case object ExtraBlue       extends DollStyle("エクストラ（青）",        "EB","Purple",     true)
  case object ExtraYellow     extends DollStyle("エクストラ（黄）",        "EY","Purple",     true)

  private val values = Array(
      Normal, Power, Defence, Speed, Assist, DefenceHakutaku,
      Extra, ExtraRed, ExtraBlue, ExtraYellow)

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

  /**
   * スタイルの名前を取得する
   *
   * @params withAnnotation Boolean （）内の名前を取得するかどうか
   */
  def getStyleName(withAnnotation: Boolean = false) = {
    (withAnnotation, styleName.contains("（")) match {
      case (false,true) => {
        styleName.substring(0, styleName.indexOf("（"))
      }
      case _ => styleName
    }
  }

  /**
   * シンボルの取得
   * 特殊シンボルは一文字の汎用シンボルに変換する
   */
  def getStyleSymbol = {
    symbol match {
      /* Eヘカーティア */
      case "ER" | "EB" | "EY" => "E"
      /* Dけいね */
      case "DH" => "D"
      case _ => symbol
    }
  }
}
