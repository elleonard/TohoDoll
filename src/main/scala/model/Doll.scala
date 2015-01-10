package model

import utils.TableUtils

case class DollData(doll: Doll){
  def toWiki = {
    doll.toWiki+"\n&br;\n"+doll.elementRateTable+"\n"+doll.skillTable+"\n"+doll.skillCardTable+"\n"+
    "----\n*コメント[#comment]"
  }
}

case class Doll(
  val image: String,
  val caption: String,
  val style: Array[StyleStatus],
  val description: String,
  val cost: Int,
  val spot: Array[AppearanceSpot],
  val dropItem: Array[String],
  val abilityAnnotation: String
) {
  /* アビリティリスト */
  def ability:String = {
    val abilityList:List[DollAbility] =
      style.flatMap(s => s.ability).toList.distinct
    abilityList.map(ab =>
      "|CENTER:[["+ab.name+"]]|CENTER:"+
        (style.map(s =>
          if(s.hasAbility(ab.name))
            "&color("+s.getStyleColor+"){''"+s.styleSymbol+"''};"
          else
            ""
        ).filter { x => x.nonEmpty }.mkString("/"))+"|>|>|>|>|>|>|>|>|>|>|>|"+ab.description+"|"
    ).mkString("\n")
  }
  /* wiki記法で返す */
  def toWiki = {
    "[[基本情報>#outline]] / [[スキル>#skill]] / [[スキルカード>#card]] / [[コメント>#comment]]\n"+
      "*基本情報 [#outline]\n"+
      "&br;\n|>|&attachref("+image+",nolink, "+caption+");|>|>|>|>|>|>|>|>|>|>|>|~基本ステータス|\n"+
      "|~|~|~スタイル|~属性1|~属性2|>|>|~耐久|~集攻|~集防|~散攻|~散防|~俊敏|~合計|\n"+
      (style.map(s => s.toWiki).mkString("\n"))+"\n"+
      "|~アビリティ|~スタイル|>|>|>|>|>|>|>|>|>|>|>|~効果|\n"+
      ability+"\n|>|>|>|>|>|>|>|>|>|>|>|>|~図鑑説明|~COST|\n"+
      "|>|>|>|>|>|>|>|>|>|>|>|>|"+description+"|CENTER:"+cost+"|\n"+
      "|>|>|>|>|>|>|>|~主な出現場所|>|>|~出現レベル|>|>|~ドロップ&br;アイテム|\n"+
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
    abilityAnnotation+"\n"+
    "&br;"
  }
  /* スキル表 */
  def skillTable = {
    /* スキル情報のリスト レベルソート */
    val skillList: List[DollSkill] = style.flatMap( s =>
      s.getSkillList match {
        case Some(list) => list
        case None => List()
      }
    ).toList.distinct.sortWith((s1,s2) => {
      /* レベル順にソートされる */
      s1.lv < s2.lv
    })
    /* スキルテーブル */
    "*''スキル'' [#skill]\n"+
      "|~取得Lv|~スタイル|~スキル名|~属性|~分類|~威力|~命中|~SP|~優先度|~効果|~取得PP|\n"+
      skillList.map( sk =>
        "|~Lv"+sk.lv+"|CENTER:"+
          style.map(s =>
            if(s.hasSkillLv(sk.name, sk.lv))
              "&color("+s.getStyleColor+"){''"+s.styleSymbol+"''};"
            else
              ""
          ).filter { x => x.nonEmpty }.mkString("/")+"|"+sk.nameInWiki+"|"+
        DollElement.getWikiText(sk.element)+"|"+
        sk.categoryWikiText+"|CENTER:"+sk.power+"|CENTER:"+sk.hit+
        "|CENTER:"+sk.SP+"|CENTER:"+sk.priority+
        "|"+sk.description+"|CENTER:"+sk.PP+"|"
      ).mkString("\n")+"\n&br;"
  }
  /* スキルカード表 */
  def skillCardTable = {
    if(style.exists { x => x.hasSkillCard }){
      val cardList: List[Int] = {
        style.flatMap( s => s.skillCard )
      }.toList.distinct.sorted
      "*''スキルカード'' [#card]\n"+
        skillCardTableHeader+"\n"+skillCardTableSub
    } else
      ""
  }
  def skillCardTableSub: String = {
    val concentrateList =
      SkillCard.concentrateAttack.filter(x => hasSkillCard(x.number))
    val diffuseList =
      SkillCard.diffuseAttack.filter(x => hasSkillCard(x.number))
    val specialList =
      SkillCard.special.filter(x => hasSkillCard(x.number))

    (for(i <- 0 until List(concentrateList.size, diffuseList.size, specialList.size).max) yield {
      (if(concentrateList.size > i){
        separatedSkillCardTable(concentrateList(i).number, concentrateList(i).getWikiText(i), i)
      }else {
        "|~ |"+TableUtils.evenLineColor(i)+"|"+TableUtils.evenLineColor(i)+
        "|"+TableUtils.evenLineColor(i)+"|"+TableUtils.evenLineColor(i)
      })+
      (if(diffuseList.size > i){
        separatedSkillCardTable(diffuseList(i).number, diffuseList(i).getWikiText(i), i)
      }else {
        "|~ |"+TableUtils.evenLineColor(i)+"|"+TableUtils.evenLineColor(i)+
        "|"+TableUtils.evenLineColor(i)+"|"+TableUtils.evenLineColor(i)
      })+
      (if(specialList.size > i){
        separatedSkillCardTable(specialList(i).number, specialList(i).getWikiText(i), i)
      }else {
        "|~ |"+TableUtils.evenLineColor(i)+"|"+TableUtils.evenLineColor(i)+
        "|"+TableUtils.evenLineColor(i)+"|"+TableUtils.evenLineColor(i)
      })+"|\n"
    }).mkString
  }
  def separatedSkillCardTable(skillCardNumber: Int, wikiText: String, line: Int) = {
    wikiText+
      style.map(s =>
        if(s.hasSkillCard(skillCardNumber))
          TableUtils.evenLineColor(line)+"CENTER:''○''"
        else
          TableUtils.evenLineColor(line)
      ).mkString("|")
  }
  def skillCardTableHeader:String = {
    (for(i <- 1 to 3) yield {
      "|~No.|~スキル名|"+
      style.map(s =>
        "~&color("+s.getStyleColor+"){''"+s.styleSymbol+"''};"
      ).mkString("|~")
    }).mkString+"|"
  }
  /* スキルカードを使用可能なスタイルがあるか */
  def hasSkillCard(skillCardNumber: Int): Boolean = {
    style.exists( s => s.hasSkillCard(skillCardNumber) )
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
  val ability: Array[DollAbility],
  val skill: Array[DollSkill],
  val skillCard: Array[Int]
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
  def hasAbility(abilityName: String):Boolean = ability.exists(x => x.name == abilityName)
  /* スキルを持っているかどうか */
  def hasSkill(skillName: String): Boolean =
    skill.exists(x => x.name == skillName)
  def hasSkillLv(skillName: String, lv: Int): Boolean =
    skill.exists(x => x.name == skillName && x.lv == lv)
  def hasSkillCard(skillCardNumber: Int): Boolean =
    skillCard.exists(x => x == skillCardNumber)
  def hasSkillCard: Boolean = Option(skillCard).isDefined
  /* 種族値のwiki記法 */
  def toWiki = {
    "|~|~|CENTER:COLOR("+getStyleColor+"):''"+getStyleName+"''|"+
      DollElement.getWikiText(element1)+"|"+
      DollElement.getWikiText(element2)+"|>|>|RIGHT:"+hp+"|RIGHT:"+concentrateAttack+
      "|RIGHT:"+concentrateDefence+"|RIGHT:"+diffuseAttack+"|RIGHT:"+diffuseDefence+
      "|RIGHT:"+speed+"|CENTER:"+total+"|"
  }
  /* 属性相性表 */
  def elementRateTable = {
    val defender1 = DollElement.getElement(element1).get  // 例外こわい
    val defender2 = DollElement.getElement(element2)
    "|"+TableUtils.evenLineColor(styleId)+"CENTER:&color("+getStyleColor+"){''"+getStyleName+"''};|"+
      (DollElement.values.map(e => TableUtils.evenLineColor(styleId)+elementRateMark(e, defender1, defender2)).mkString("|"))+"|"
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
  def getSkillList = Option(skill)
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
    "|>|>|>|>|>|>|>|CENTER:"+name+"|>|>|"+level+"|>|>|"
  }
  def level = {
    if(minLv == maxLv)
      "CENTER:"+minLv
    else
      "CENTER:"+minLv+"～"+maxLv
  }
}