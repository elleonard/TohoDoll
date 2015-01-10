package model

object DollElement {
  case object Plain    extends DollElement("無","#ffefef",None)
  case object Flame    extends DollElement("炎","#ffafaf",None)
  case object Water    extends DollElement("水","#afefff",None)
  case object Nature   extends DollElement("然","#afffaf",None)
  case object Ground   extends DollElement("地","#ddcdad",None)
  case object Steel    extends DollElement("鋼","#eeeeee",None)
  case object Wind     extends DollElement("風","#d8ffc8",None)
  case object Thunder  extends DollElement("雷","#ffe8af",None)
  case object Light    extends DollElement("光","#ffffaf",None)
  case object Dark     extends DollElement("闇","#888888",Some("white"))
  case object Phantom  extends DollElement("冥","#d8c8ff",None)
  case object Poison   extends DollElement("毒","#ffcfff",None)
  case object Fight    extends DollElement("闘","#ffcfaf",None)
  case object Illusion extends DollElement("幻","#ffc8df",None)
  case object Sound    extends DollElement("音","#fff8c8",None)
  case object Dream    extends DollElement("夢","deeppink",None)

  val values = Array(Plain, Flame, Water, Nature, Ground, Steel,
      Wind, Thunder, Light, Dark, Phantom, Poison, Fight, Illusion,
      Sound, Dream)

  def getWikiText(elementName: String) = {
    getElement(elementName) match {
      case Some(e) => "CENTER:BGCOLOR("+e.backgroundColor+")"+
        (e.textColor match {
          case Some(c) => ":COLOR("+c+")"
          case None => ""
        })+
        ":"+e.elementName
      case None => ""
    }
  }
  def getElement(elementName: String):Option[DollElement] =
    values.find( x => x.elementName == elementName )

  /* 属性相性 */
  def damageRate(attacker: DollElement, defender: DollElement) = {
    attacker match {
      case Plain => {  // 無属性攻撃
        defender match {
          case Light | Dark => 0.5
          case Illusion => 0
          case _ => 1
        }
      }
      case Flame => {  // 炎属性攻撃
        defender match {
          case Flame | Water | Ground => 0.5
          case Nature | Steel => 2
          case _ => 1
        }
      }
      case Water => {  // 水属性攻撃
        defender match {
          case Water | Nature | Sound => 0.5
          case Flame | Ground => 2
          case _ => 1
        }
      }
      case Nature => {  // 自然属性攻撃
        defender match {
          case Flame | Nature | Wind | Poison => 0.5
          case Water | Ground => 2
          case _ => 1
        }
      }
      case Ground => {  // 大地属性攻撃
        defender match {
          case Nature | Fight => 0.5
          case Flame | Steel | Thunder | Poison => 2
          case Wind => 0
          case _ => 1
        }
      }
      case Steel => {  // 鋼鉄属性攻撃
        defender match {
          case Flame | Water | Steel => 0.5
          case Nature | Wind | Dark => 2
          case _ => 1
        }
      }
      case Wind => {  // 風属性攻撃
        defender match {
          case Steel | Thunder => 0.5
          case Poison | Fight | Sound => 2
          case _ => 1
        }
      }
      case Thunder => {  // 雷属性攻撃
        defender match {
          case Nature | Thunder | Light => 0.5
          case Water | Wind => 2
          case Ground => 0
          case _ => 1
        }
      }
      case Light => {  // 光属性攻撃
        defender match {
          case Water | Nature | Light | Illusion => 0.5
          case Dark | Phantom => 2
          case _ => 1
        }
      }
      case Dark => {  // 闇属性攻撃
        defender match {
          case Dark | Fight | Illusion => 0.5
          case Plain | Light | Phantom => 2
          case _ => 1
        }
      }
      case Phantom => {  // 冥属性攻撃
        defender match {
          case Light => 0.5
          case Phantom => 2
          case _ => 1
        }
      }
      case Poison => {  // 毒属性攻撃
        defender match {
          case Ground | Phantom | Poison => 0.5
          case Water | Nature => 2
          case Steel => 0
          case _ => 1
        }
      }
      case Fight => {  // 闘属性攻撃
        defender match {
          case Wind | Poison | Illusion => 0.5
          case Ground | Steel | Dark => 2
          case Phantom => 0
          case _ => 1
        }
      }
      case Illusion => {  // 幻属性攻撃
        defender match {
          case Steel | Phantom => 0.5
          case Light | Illusion => 2
          case Plain => 0
          case _ => 1
        }
      }
      case Sound => {  // 音属性攻撃
        defender match {
          case Wind | Sound => 0.5
          case Fight | Illusion => 2
          case _ => 1
        }
      }
      case _ => 1
    }
  }
}

sealed abstract class DollElement(
  val elementName: String,
  val backgroundColor: String,
  val textColor: Option[String]
){
}