package model

case class DollData(doll: Doll){
  def toWiki = {
    doll.toWiki+"\n\n"+doll.elementRateTable
  }
}

case class Doll(
  val image: String,
  val style: Array[StyleStatus],
  val description: String,
  val cost: Int,
  val spot: Array[AppearanceSpot],
  val dropItem: Array[String]
) {
  /* アビリティリスト */
  def ability:String = {
    val abilityList:List[DollAbility] =
      style.flatMap(s => s.ability).toList.distinct
    abilityList.map(ab =>
      "|CENTER:[["+ab.name+"]]|CENTER:"+
        (style.map(s =>
          if(s.includeAbility(ab.name)) {
            "&color("+s.getStyleColor+"){''"+s.styleSymbol+"''};"
          }
        ).mkString("/"))+"|>|>|>|>|>|>|>|>|>|>|>|"+ab.description+"|"
    ).mkString("\n")
  }
  /* wiki記法で返す */
  def toWiki = {
    "&br;\n|>|&attachref(" + image +
      ",nolink);|>|>|>|>|>|>|>|>|>|>|>|~基本ステータス|\n"+
      "|~|~|~スタイル|~属性1|~属性2|>|>|~耐久|~集攻|~集防|~散攻|~散防|~俊敏|~合計|\n"+
      (style.map(s => s.toWiki).mkString("\n"))+"\n"+
      "|~アビリティ|~スタイル|>|>|>|>|>|>|>|>|>|>|>|~効果|\n"+
      ability+"\n|>|>|>|>|>|>|>|>|>|>|>|>|~図鑑説明|~COST|\n"+
      "|>|>|>|>|>|>|>|>|>|>|>|>|"+description+"|CENTER:"+cost+"|\n"+
      "|>|>|>|>|>|>|>|~主な出現場所|>|>|>|>|~出現レベル|~ドロップ&br;アイテム|\n"+
      spot(0).toWiki+"CENTER:"+dropItem.mkString("&br;")+"|\n"+
      (spot.tail.map(s =>
        s.toWiki+"~|"
      ).mkString("\n"))
  }
  /* 属性相性表 */
  def elementRateTable = {
    "|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|~相性|\n"+
    "|~スタイル|CENTER:BGCOLOR(pink):無|CENTER:BGCOLOR(red):COLOR(white):炎|CENTER:BGCOLOR(dodgerblue):COLOR(white):水|CENTER:BGCOLOR(green):COLOR(white):然|CENTER:BGCOLOR(brown):COLOR(white):地|CENTER:BGCOLOR(silver):鉄|CENTER:BGCOLOR(lawngreen):風|CENTER:BGCOLOR(gold):雷|CENTER:BGCOLOR(yellow):光|CENTER:BGCOLOR(black):COLOR(white):闇|CENTER:BGCOLOR(mediumorchid):COLOR(white):冥|CENTER:BGCOLOR(mediumpurple):COLOR(white):毒|CENTER:BGCOLOR(orange):闘|CENTER:BGCOLOR(fuchsia):COLOR(white):幻|CENTER:BGCOLOR(khaki):音|CENTER:BGCOLOR(deeppink):COLOR(white):夢|\n"+
    (style.map(s => s.elementRateTable).mkString("\n"))+"\n"+
    "&color(Red){◎};＝結界を貫通する(4倍) &color(Red){○};＝結界を貫通する &color(Blue){△};＝結界に阻まれる &color(Blue){▲};＝結界に阻まれる(1/4) ''×''＝効果がない\n"+
    "※アビリティによる無効属性などはここに記述。\n"+
    "&br;"
  }
}

case class StyleStatus(
  val styleId: Int,
  val styleSymbol: String,
  val element1: String,
  val element2: String,
  val hp: Int,
  val concentrateAttack: Int,
  val concentrateDefence: Int,
  val diffuseAttack: Int,
  val diffuseDefence: Int,
  val speed: Int,
  val ability: Array[DollAbility]
) {
  /* スタイルシンボルからスタイルを取得 */
  def getStyle = DollStyle.getFromSymbol(styleSymbol)
  /* スタイルの名前 */
  def getStyleName = getStyle match {
    case Some(s) => s.styleName
    case None =>
  }
  /* スタイルの色 */
  def getStyleColor = getStyle match {
    case Some(s) => s.color
    case None =>
  }
  /* 種族値合計 */
  def total = hp + concentrateAttack + concentrateDefence + diffuseAttack + diffuseDefence + speed
  /* アビリティを持っているかどうか */
  def includeAbility(abilityName: String):Boolean = ability.exists(x => x.name == abilityName)
  /* 種族値のwiki記法 */
  def toWiki = {
    "|~|~|CENTER:COLOR("+getStyleColor+"):''"+getStyleName+"''|"+
      DollElement.getWikiText(element1)+"|"+
      DollElement.getWikiText(element2)+"|>|>|RIGHT:"+hp+"|RIGHT:"+concentrateAttack+
      "|RIGHT:"+concentrateDefence+"|RIGHT:"+diffuseAttack+"|RIGHT:"+diffuseDefence+
      "|RIGHT:"+speed+"|RIGHT:"+total+"|"
  }
  /* 属性相性表 */
  def elementRateTable = {
    val defender1 = DollElement.getElement(element1).get  // 例外こわい
    val defender2 = DollElement.getElement(element2)
    "|"+evenLineColor+"CENTER:&color("+getStyleColor+"){''"+getStyleName+"''};|"+
      (DollElement.values.map(e => evenLineColor+elementRateMark(e, defender1, defender2)).mkString("|"))+"|"
  }
  /* 偶数行のみ色をつける */
  def evenLineColor = {
    if(styleId == 1){"BGCOLOR(#F8FAFB):"} else {""}
  }
  def elementRateMark(attacker: DollElement, defender1: DollElement, defender2: Option[DollElement]): String = {
    /* 相性計算 */
    val rate = {
      defender2 match{
        case Some(d2) => DollElement.damageRate(attacker, defender1) * DollElement.damageRate(attacker, d2)
        case None => DollElement.damageRate(attacker, defender1)
      }
    }
    /* 対応するマークを返す */
    rate match {
      case 4 => "&color(Red){◎};"
      case 2 => "&color(Red){◯};"
      case 0.5 => "&color(Blue){△};"
      case 0.25 => "&color(Blue){▲};"
      case 0 => "×"
      case _ => ""
    }
  }
}

case class DollAbility(
  val name: String,
  val description: String
){
}

case class AppearanceSpot(
  val name: String,
  val minLv: Int,
  val maxLv: Int
){
  def toWiki = {
    "|>|>|>|>|>|>|>|CENTER:"+name+"|>|>|>|>|CENTER:"+minLv+"～"+maxLv+"|"
  }
}