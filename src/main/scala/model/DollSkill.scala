package model

import model.dao.SkillDao

case class DollSkill(
  val lv: Int,
  val name: String,
  val PP: Int
) {
  def entity = SkillDao.getByName(name) match {
    case Some(null) | None => throw new Exception("not found skill:"+name)
    case Some(e) => e
  }

  def getWikiText(style: DollStyle,first: Boolean) = {
    "|~"+(first match {
      case true =>
        "&color("+style.color+"){''"+style.symbol+"''};"
      case false => ""
    })+"|~Lv"+lv+"|"+nameInWiki+"|"+
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

