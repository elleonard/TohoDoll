package model

import model.dao.SkillDao

case class DollSkill(
  val lv: Int,
  val name: String,
  val PP: Int
) {
  /**
   * スキル詳細情報をDBから取得する
   */
  def entity = {
    SkillDao.getByName(name) match {
      case Some(null) | None => throw new Exception("[error]: not found skill:"+name)
      case Some(e) => e
    }
  }

  /**
   * スキルがDB上に存在するかどうか
   */
  def existSkill = {
    SkillDao.getByName(name).isDefined
  }

  /**
   * wikiに表示する際のテキストを返す
   *
   * @params style DollStyle このスキルを覚えるスタイル
   * @params first Boolean
   * @params ommitLv Boolean レベルを省略するかどうか
   */
  def getWikiText(style: DollStyle,first: Boolean, ommitLv: Boolean = false) = {
    "|~"+(first match {
      case true =>
        "&color("+style.color+"){''"+style.getStyleSymbol+"''};"
      case false => ""
    })+"|~"+(ommitLv match {
      case true => ""
      case false => "Lv"+lv
    })+"|"+nameInWiki+"|"+
    DollElement.getWikiText(element)+"|"+
    categoryWikiText+"|"+buen+"|"+power+"|"+hit+
    "|"+SP+"|"+priority+
    "|"+description+"|"+PP+"|"
  }

  def nameInWiki = {
    entity.category match {
      case "集中" => "[[COLOR(Red):"+name+">"+name+"]]"
      case "拡散" => "[[COLOR(Blue):"+name+">"+name+"]]"
      case _ => "[["+name+"]]"
    }
  }
  def categoryWikiText = {
    "CENTER:"+(entity.category match {
      case "集中" => "&color(Red){"+entity.category+"};"
      case "拡散" => "&color(Blue){"+entity.category+"};"
      case _ => entity.category
    })
  }

  def element = entity.element
  def power = entity.power
  def hit = entity.hit
  def SP = entity.sp
  def priority = entity.speed
  def description = entity.description
  def buen = entity.buen
}

