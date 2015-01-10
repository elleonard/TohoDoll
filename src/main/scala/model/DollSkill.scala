package model

case class DollSkill(
  val lv: Int,
  val name: String,
  val element: String,
  val category: String,
  val power: String,
  val hit: String,
  val SP: Int,
  val priority: Int,
  val description: String,
  val PP: Int
) {
  def nameInWiki = {
    category match {
      case "集中" => "[[COLOR(Red):"+name+">"+name+"]]"
      case "拡散" => "[[COLOR(Blue):"+name+">"+name+"]]"
      case _ => "[["+name+"]]"
    }
  }
  def categoryWikiText = {
    "CENTER:"+(category match {
      case "集中" => "&color(Red){"+category+"};"
      case "拡散" => "&color(Blue){"+category+"};"
      case _ => category
    })
  }
}

