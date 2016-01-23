package model

import utils.TableUtils
import scala.collection.immutable.TreeMap

case class DollData(doll: Doll){
  def toWiki = {
    doll.toWiki+"\n&br;\n"+doll.elementRateTable+"\n"+doll.skillTable+"\n"+doll.skillCardTable+"\n"+
    "----\n*コメント[#comment]\n"+
    "#pComment("+doll.dollName+"/コメント,"+doll.dollName+"/コメント,below,reply)"
  }
}

case class Doll(
  val dollName: String,
  val image: String,
  val caption: String,
  val style: Array[StyleStatus],
  val description: String,
  val cost: Int,
  val spot: Array[AppearanceSpot],
  val fixSpot: Array[AppearanceSpot],
  val dropItem: Array[String],
  val abilityAnnotation: String
) {
  /* アビリティリスト */
  def ability:String = {
    val abilityList:List[DollAbility] =
      style.flatMap(s => s.getAbility match {
          case Some(a) => a
          case None => Array[DollAbility]()
      }).toList.distinct
    abilityList.map(ab =>
      "|CENTER:[["+ab.name+"]]|CENTER:"+
        (style.map(s =>
          if(s.hasAbility(ab.name))
            "&color("+s.getStyleColor+"){''"+s.getStyleSymbol+"''};"
          else
            ""
        ).filter { x => x.nonEmpty }.mkString("/"))+"|>|>|>|>|>|>|>|>|>|>|>|LEFT:"+ab.description+"|"
    ).mkString("\n")
  }
  /* wiki記法で返す */
  def toWiki = {
    "[[基本情報>#outline]] / [[スキル>#skill]] / [[スキルカード>#card]] / [[コメント>#comment]]\n"+
      "*基本情報 [#outline]\n"+
      "|CENTER:|CENTER:|CENTER:|CENTER:|CENTER:|||RIGHT:|RIGHT:|RIGHT:|RIGHT:|RIGHT:|RIGHT:|CENTER:|c\n"+
      "|>|&attachref("+image+",nolink, "+caption+");|>|>|>|>|>|>|>|>|>|>|>|~基本ステータス|\n"+
      "|~|~|~スタイル|~属性1|~属性2|>|>|~耐久|~集弾|~集防|~散弾|~散防|~俊敏|~合計|\n"+
      (style.map(s => s.toWiki).mkString("\n"))+"\n"+
      "|~アビリティ|~スタイル|>|>|>|>|>|>|>|>|>|>|>|~効果|\n"+
      ability+"\n|>|>|>|>|>|>|>|>|>|>|>|>|CENTER:~図鑑説明|~COST|\n"+
      "|>|>|>|>|>|>|>|>|>|>|>|>|LEFT:"+description+"|"+cost+"|\n"+
      "|>|>|>|>|>|>|>|CENTER:~主な出現場所|>|>|CENTER:~出現レベル|>|>|~ドロップアイテム|\n"+
      spot(0).toWiki+"CENTER:"+dropItem.mkString("&br;")+"|\n"+
      (spot.tail.map(s =>
        s.toWiki+"~|"
      ).mkString("\n"))+"\n"+
      (Option(fixSpot) match {
        case None => ""
        case _ =>
          fixSpot.isEmpty match {
            case true => ""
            case false => "|>|>|>|>|>|>|>|CENTER:~固定エンカウント|>|>|CENTER:~出現レベル|>|>|~備考|\n"+
              (fixSpot.map(s =>s.toWiki
              ).mkString("\n"))
          }
      })
  }
  /* 属性相性表 */
  def elementRateTable = {
    "|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|>|~相性|\n"+
    "|~スタイル|"+DollElement.values.map(e => e.getWikiText).mkString("|")+"|\n"+
    (style.map(s => s.elementRateTable).mkString("\n"))+"\n"+
    abilityAnnotation+"\n"+
    "&color(Red){◎};＝結界を貫通する(4倍) &color(Red){○};＝結界を貫通する &color(Blue){△};＝結界に阻まれる &color(Blue){▲};＝結界に阻まれる(1/4) ''×''＝効果がない\n"+
    "&br;"
  }
  /* スキル表 */
  def skillTable = {
    /**
     * ソートされたスキル一覧
     */
    def sortSkillList(skillList: Option[Array[DollSkill]]): List[DollSkill] = {
      (skillList match {
        case Some(list) => list.toList
        case None => List()
      }).distinct.sortWith((s1, s2) => { s1.lv < s2.lv })
    }
    /**
     * それぞれのスタイルのテーブルを取得
     */
    def getSkillTable(style: DollStyle,
        skillList: List[DollSkill],
        normalSkillList: List[DollSkill] = List()): String = {
      /**
       * 行をレベルでグルーピングして返す
       */
      def getSkillTableLineGroupsByLv(skillList: List[DollSkill], style: DollStyle) = {
        skillList.groupBy(_.lv).mapValues(groupByLv =>
          groupByLv.head.lv match {
            case 1 => groupByLv.map( skill =>
              skill.getWikiText(style, false, true)).mkString("\n")+"\n"
            case _ => {
              groupByLv.head.getWikiText(style, false)+"\n"+
                (groupByLv.tail.map(skill =>
                  skill.getWikiText(style, false, true)).mkString("\n"))
            }
          }
        ).toSeq.sortWith(_._1 < _._1).map(grouped => grouped._2).mkString
      }

      (style.symbol, style.isSpecial) match {
        case (_, true) => ""
        case ("N", false) => {
            "||CENTER:||CENTER:|CENTER:|CENTER:|CENTER:|CENTER:|CENTER:|CENTER:||CENTER:|c\n"+
            "|~スタイル|~習得Lv|~スキル名|~属性|~分類|~種別|~威力|~命中|~SP|~優先度|~効果|~取得PP|\n"+
            (skillList.isEmpty match {
              case true => ""
              case false => {
                skillList.head.getWikiText(style, true)+"\n"+
                getSkillTableLineGroupsByLv(skillList.tail, style)
              }
            })
        }
        case _ => {
          println("[お知らせ]: "+style.getStyleSymbol+""+dollName+"のスキルをロードします。")
          "#region(&color("+style.color+"){''"+style.styleName+"''};,open)\n"+
          "&color("+style.color+"){''"+style.styleName+"''};\n"+
          getSkillTable(DollStyle.Normal, normalSkillList)+
          (skillList.isEmpty match {
            case true => ""
            case false => {
              skillList.head.getWikiText(style, true)+"\n"+
              getSkillTableLineGroupsByLv(skillList.tail, style)
            }
          })+"\n#endregion\n"
        }
      }
    }

    /* ノーマルのテーブル */
    val normalSkillList: List[DollSkill] = sortSkillList(style.head.getSkillList)

    /* ノーマルのテーブルテキストをマージして返す */
    "* スキル [#skill]\n"+
    (style.tail.map( s => {
      s.getStyle match {
        case Some(style) =>
          getSkillTable(style, sortSkillList(s.getSkillList), normalSkillList)
        case None => ""
      }
    }).mkString("\n")+"\n&br;")
  }
  /* スキルカード表 */
  def skillCardTable = {
    if(style.exists { x => x.hasSkillCard }){
      val cardList: List[Int] = {
        style.filter { x => x.hasSkillCard }.flatMap( s => s.skillCard )
      }.toList.distinct.sorted
      "*''スキルカード'' [#card]\n"+
        skillCardTableHeader+"\n"+skillCardTableSub(4)
    } else
      ""
  }
  def skillCardTableSub(styleNum: Int = 4): String = {
    val concentrateList =
      SkillCard.concentrateAttack.filter(x => hasSkillCard(x.number))
    val diffuseList =
      SkillCard.diffuseAttack.filter(x => hasSkillCard(x.number))
    val specialList =
      SkillCard.special.filter(x => hasSkillCard(x.number))

    def extraLine = {

    }

    (for(i <- 0 until List(concentrateList.size, diffuseList.size, specialList.size).max) yield {
      (if(concentrateList.size > i){
        separatedSkillCardTable(concentrateList(i).number, concentrateList(i).getWikiText(i), i)
      }else {
        "|~ |"+List.fill(styleNum+1)(TableUtils.evenLineColor(i)).mkString("|")
      })+
      (if(diffuseList.size > i){
        separatedSkillCardTable(diffuseList(i).number, diffuseList(i).getWikiText(i), i)
      }else {
        "|~ |"+List.fill(styleNum+1)(TableUtils.evenLineColor(i)).mkString("|")
      })+
      (if(specialList.size > i){
        separatedSkillCardTable(specialList(i).number, specialList(i).getWikiText(i), i)
      }else {
        "|~ |"+List.fill(styleNum+1)(TableUtils.evenLineColor(i)).mkString("|")
      })+"|\n"
    }).mkString
  }
  def separatedSkillCardTable(skillCardNumber: Int, wikiText: String, line: Int) = {
    wikiText+
      style.filter { x => x.hasSkillCard }.map(s =>
        if(s.hasSkillCard(skillCardNumber))
          TableUtils.evenLineColor(line)+"CENTER:''○''"
        else
          TableUtils.evenLineColor(line)
      ).mkString("|")
  }
  def skillCardTableHeader:String = {
    "|||CENTER:|CENTER:|CENTER:|CENTER:|||CENTER:|CENTER:|CENTER:|CENTER:|||CENTER:|CENTER:|CENTER:|CENTER:|c\n"+
    (for(i <- 1 to 3) yield {
      "|~No.|~スキル名|"+
      style.filter { x => x.hasSkillCard }.map(s =>
        "~&color("+s.getStyleColor+"){''"+s.getStyleSymbol+"''};"
      ).mkString("|~")
    }).mkString+"|"
  }
  /* スキルカードを使用可能なスタイルがあるか */
  def hasSkillCard(skillCardNumber: Int): Boolean = {
    style.exists( s => s.hasSkillCard && s.hasSkillCard(skillCardNumber) )
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

  /**
   * 汎用スタイルシンボルを取得
   */
  def getStyleSymbol = getStyle match {
    case Some(s) => s.getStyleSymbol
    case None =>
  }

  /**
   * スタイル名を括弧内の注釈付きで取得
   */
  def getStyleNameWithAnnotation = getStyle match {
    case Some(s) => s.getStyleName(true)
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
  def hasAbility(abilityName: String):Boolean =
    Option(ability).isDefined && ability.exists(x => x.name == abilityName)
  /* スキルを持っているかどうか */
  def hasSkill(skillName: String): Boolean =
    Option(skill).isDefined && skill.exists(x => x.name == skillName)
  def hasSkillLv(skillName: String, lv: Int): Boolean =
    Option(skill).isDefined && skill.exists(x => x.name == skillName && x.lv == lv)
  def hasSkillCard(skillCardNumber: Int): Boolean =
    skillCard.exists(x => x == skillCardNumber)
  def hasSkillCard: Boolean = Option(skillCard).isDefined
  /* 種族値のwiki記法 */
  def toWiki = {
    "|~|~|CENTER:COLOR("+getStyleColor+"):''"+getStyleNameWithAnnotation+"''|"+
      DollElement.getWikiText(element1, true)+"|"+
      DollElement.getWikiText(element2, true)+"|>|>|RIGHT:"+hp+"|RIGHT:"+concentrateAttack+
      "|RIGHT:"+concentrateDefence+"|RIGHT:"+diffuseAttack+"|RIGHT:"+diffuseDefence+
      "|RIGHT:"+speed+"|CENTER:"+total+"|"
  }
  /* 属性相性表 */
  def elementRateTable = {
    val defender1 = DollElement.getElement(element1).get  // 例外こわい
    val defender2 = DollElement.getElement(element2)
    "|"+TableUtils.evenLineColor(styleId)+"CENTER:&color("+getStyleColor+"){''"+getStyleNameWithAnnotation+"''};|"+
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
      case 2 => "&color(Red){○};"
      case 0.5 => "&color(Blue){△};"
      case 0.25 => "&color(Blue){▲};"
      case 0 => "×"
      case _ => ""
    }
  }
  def getSkillList = Option(skill)
  def getAbility = Option(ability)
}

case class DollAbility(
  val name: String,
  val description: String
){
}

case class AppearanceSpot(
  val name: String,
  val minLv: Int,
  val maxLv: Int,
  val description: String
){
  def toWiki = {
    description match{
      case null => "|>|>|>|>|>|>|>|CENTER:"+name+"|>|>|CENTER:"+level+"|>|>|"
      case _ => "|>|>|>|>|>|>|>|CENTER:"+name+"|>|>|CENTER:"+level+"|>|>|"+description+"|"
    }
  }
  def level = {
    if(minLv == maxLv)
      "CENTER:"+minLv
    else
      "CENTER:"+minLv+"～"+maxLv
  }
}