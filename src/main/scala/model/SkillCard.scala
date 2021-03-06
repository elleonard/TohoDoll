package model

import utils.TableUtils

object SkillCard {
  case object FireJavelin      extends SkillCard( 1,"ファイアジャベリン",  "集中")
  case object AquaJavelin      extends SkillCard( 2,"アクアジャベリン",    "集中")
  case object GlassJavelin     extends SkillCard( 3,"グラスジャベリン",    "集中")
  case object WindJavelin      extends SkillCard( 4,"ウィンドジャベリン",  "集中")
  case object SparkJavelin     extends SkillCard( 5,"スパークジャベリン",  "集中")
  case object LightJavelin     extends SkillCard( 6,"ライトジャベリン",    "集中")
  case object ShadowJavelin    extends SkillCard( 7,"シャドウジャベリン",  "集中")
  case object Overskill        extends SkillCard( 8,"オーバースキル",      "集中")
  case object Joruri           extends SkillCard( 9,"狂乱浄瑠璃",          "集中")
  case object Reversal         extends SkillCard(10,"逆転の一手",          "集中")
  case object RisingSun        extends SkillCard(11,"ライジングサン",      "集中")
  case object BurnStrike       extends SkillCard(12,"バーンストライク",    "集中")
  case object FallOfFall       extends SkillCard(13,"滝落",                "集中")
  case object VoiceOfHunger    extends SkillCard(14,"飢餓の声",            "集中")
  case object TumblePlant      extends SkillCard(15,"タンブルプラント",    "集中")
  case object ShootingPress    extends SkillCard(16,"シューティングプレス","集中")
  case object GroundLiberate   extends SkillCard(17,"グランドリベレイト",  "集中")
  case object StoneSpike       extends SkillCard(18,"ストーンスパイク",    "集中")
  case object ImpactRebellion  extends SkillCard(19,"インパクトリベリオン","集中")
  case object OutRider         extends SkillCard(20,"露払い",              "集中")
  case object GensoTyphoon     extends SkillCard(21,"幻想郷直撃タイフーン","集中")
  case object ShockStamp       extends SkillCard(22,"ショックスタンプ",    "集中")
  case object RobberyWind      extends SkillCard(23,"盗難の風",            "集中")
  case object PlasmaHole       extends SkillCard(24,"プラズマホール",      "集中")
  case object LightDance       extends SkillCard(25,"天光の舞",            "集中")
  case object EvilCrushArrow   extends SkillCard(26,"破邪の弓撃",          "集中")
  case object ChangeLing       extends SkillCard(27,"チェンジリング",      "集中")
  case object DarkArrow        extends SkillCard(28,"ダークアロー",        "集中")
  case object LostCrisis       extends SkillCard(29,"ロストクライシス",    "集中")
  case object SankonNanahaku   extends SkillCard(30,"三魂七魄の離散",      "集中")
  case object PoisonArrow      extends SkillCard(31,"毒矢",                "集中")
  case object DecadentFist     extends SkillCard(32,"真空破断拳",          "集中")
  case object MeteorImpact     extends SkillCard(33,"メテオインパクト",    "集中")
  case object BreakShoot       extends SkillCard(34,"ブレイクショット",    "集中")
  case object ChargeStun       extends SkillCard(35,"チャージングスタン",  "集中")
  case object AbyssFlower      extends SkillCard(36,"奈落の花",            "集中")
  case object StElmosFire      extends SkillCard(37,"セントエルモの火",    "集中")
  case object DancingLine      extends SkillCard(38,"ダンシングライン",    "集中")
  case object TriEffect        extends SkillCard(39,"トライエフェクト",    "拡散")
  case object DivineProtection extends SkillCard(40,"天神の加護",          "拡散")
  case object QuadProtection   extends SkillCard(41,"四神の加護",          "拡散")
  case object FlashOver        extends SkillCard(42,"フラッシュオーバー",  "拡散")
  case object Inferno          extends SkillCard(43,"インフェルノ",        "拡散")
  case object ColdRain         extends SkillCard(44,"コールドレイン",      "拡散")
  case object LazyMist         extends SkillCard(45,"レイジィミスト",      "拡散")
  case object LightFlower      extends SkillCard(46,"彩光百花",            "拡散")
  case object MistFog          extends SkillCard(47,"ミストフォッグ",      "拡散")
  case object JeweryStorm      extends SkillCard(48,"ジュエリーストーム",  "拡散")
  case object ChromeRay        extends SkillCard(49,"クロームレイ",        "拡散")
  case object SmashSpin        extends SkillCard(50,"スマッシュスピン",    "拡散")
  case object Indignate        extends SkillCard(51,"雷帝インディグネイト","拡散")
  case object Jinrai           extends SkillCard(52,"疾風迅雷",            "拡散")
  case object ThunderForce     extends SkillCard(53,"サンダーフォース",    "拡散")
  case object StarFlare        extends SkillCard(54,"スターフレア",        "拡散")
  case object LaplaceEye       extends SkillCard(55,"ラプラスの目",        "拡散")
  case object GhostWave        extends SkillCard(56,"ゴーストウェーブ",    "拡散")
  case object Pain             extends SkillCard(57,"ラヴォルペイン",      "拡散")
  case object PoisonBomb       extends SkillCard(58,"ポイズンボム",        "拡散")
  case object Gigantic         extends SkillCard(59,"ギガンティック",      "拡散")
  case object UnknownFlare     extends SkillCard(60,"アンノウンフレア",    "拡散")
  case object GravityBlast     extends SkillCard(61,"グラビティブラスト",  "拡散")
  case object UltraHighTone    extends SkillCard(62,"ウルトラハイトーン",  "拡散")
  case object Barrier          extends SkillCard(63,"森羅結界",            "変化")
  case object BarrierOption    extends SkillCard(64,"バリアオプション",    "変化")
  case object ClearMind        extends SkillCard(65,"無我の境地",          "変化")
  case object Stopper          extends SkillCard(66,"足止め",              "変化")
  case object Thermit          extends SkillCard(67,"テルミット",          "変化")
  case object Drought          extends SkillCard(68,"干ばつ",              "変化")
  case object GustOfWind       extends SkillCard(69,"突風",                "変化")
  case object FieldBarrier     extends SkillCard(70,"フィールドバリア",    "変化")
  case object FieldProtection  extends SkillCard(71,"フィールドプロテクト","変化")
  case object LuckyRainbow     extends SkillCard(72,"幸運の虹",            "変化")
  case object StunGrenade      extends SkillCard(73,"閃光弾",              "変化")
  case object SharkTrade       extends SkillCard(74,"シャークトレード",    "変化")
  case object SweetNightmare   extends SkillCard(75,"甘い悪夢",            "変化")
  case object KagomeKagome     extends SkillCard(76,"後ろの正面",          "変化")
  case object Miasma           extends SkillCard(77,"ミアズマ",            "変化")
  case object ContinueGame     extends SkillCard(78,"コンティニュー",      "変化")
  case object UpBeat           extends SkillCard(79,"アップビート",        "変化")
  case object Claim            extends SkillCard(80,"クレーム",            "変化")
  case object DistortionBomb   extends SkillCard(81,"ディストーションボム","集中")
  case object StealthMurder    extends SkillCard(82,"ステルスマーダー",    "集中")
  case object MazeMist         extends SkillCard(83,"迷霧散花",            "拡散")
  case object Instinction      extends SkillCard(84,"インスティンクション","拡散")
  case object FieldBreak       extends SkillCard(85,"フィールドブレイク",  "拡散")
  case object FlyDance         extends SkillCard(86,"飛翔乱舞",            "集中")
  case object ThunderBlade     extends SkillCard(87,"雷切",                "集中")
  case object DancingSword     extends SkillCard(88,"ダンシングソード",    "集中")
  case object HighTone         extends SkillCard(89,"ハイトーンクラッシュ","集中")
  case object OverRun          extends SkillCard(90,"オーバーラン",        "拡散")
  case object BackDraft        extends SkillCard(91,"バックドラフト",      "拡散")
  case object IcePrison        extends SkillCard(92,"氷獄",                "拡散")
  case object SpinningAir      extends SkillCard(93,"スピニングエア",      "拡散")
  case object DarknessSweets   extends SkillCard(94,"ダークネススイーツ",  "拡散")
  case object BraveBurst       extends SkillCard(95,"ブレイブバースト",    "拡散")
  case object Catastroph       extends SkillCard(96,"カタストロフィ",      "拡散")
  case object Poltergeist      extends SkillCard(97,"ポルターガイスト",    "拡散")
  case object Shiranui         extends SkillCard(98,"不知火",              "変化")
  case object ShockParalyze    extends SkillCard(99,"ショックパライズ",    "変化")
  case object AcidTear         extends SkillCard(100,"酸涙",               "変化")
  case object Flame            extends SkillCard(101,"炎符",               "拡散")
  case object Water            extends SkillCard(102,"水符",               "拡散")
  case object Nature           extends SkillCard(103,"自然符",             "拡散")
  case object Ground           extends SkillCard(104,"大地符",             "拡散")
  case object Steel            extends SkillCard(105,"鋼鉄符",             "拡散")
  case object Wind             extends SkillCard(106,"風符",               "拡散")
  case object Thunder          extends SkillCard(107,"雷符",               "拡散")
  case object Light            extends SkillCard(108,"光符",               "拡散")
  case object Dark             extends SkillCard(109,"闇符",               "拡散")
  case object Phantom          extends SkillCard(110,"冥符",               "拡散")
  case object Poison           extends SkillCard(111,"毒符",               "拡散")
  case object Fight            extends SkillCard(112,"闘符",               "拡散")
  case object Illusion         extends SkillCard(113,"幻符",               "拡散")
  case object Sound            extends SkillCard(114,"音符",               "拡散")
  case object Distortion       extends SkillCard(115,"歪符",               "拡散")

  val concentrateAttack =
    Array(FireJavelin, AquaJavelin, GlassJavelin, WindJavelin,
        SparkJavelin, LightJavelin, ShadowJavelin,
        Overskill, Joruri, Reversal, RisingSun, BurnStrike, FallOfFall,
        VoiceOfHunger, TumblePlant, ShootingPress,
        GroundLiberate, StoneSpike, ImpactRebellion, OutRider, GensoTyphoon,
        ShockStamp, RobberyWind, PlasmaHole,
        LightDance, EvilCrushArrow, ChangeLing, DarkArrow, LostCrisis,
        SankonNanahaku, PoisonArrow, DecadentFist,
        MeteorImpact, BreakShoot, ChargeStun, AbyssFlower, StElmosFire,
        DancingLine, DistortionBomb, StealthMurder, FlyDance, ThunderBlade,
        DancingSword, HighTone)
  val diffuseAttack =
    Array(TriEffect, DivineProtection, QuadProtection, FlashOver, Inferno,
        ColdRain, LazyMist, LightFlower,
        MistFog, JeweryStorm, ChromeRay, SmashSpin, Indignate, Jinrai,
        ThunderForce, StarFlare, LaplaceEye,
        GhostWave, Pain, PoisonBomb, Gigantic, UnknownFlare, GravityBlast,
        UltraHighTone, MazeMist, Instinction, FieldBreak,
        OverRun, BackDraft, IcePrison, SpinningAir, DarknessSweets,
        BraveBurst, Catastroph, Poltergeist,
        Flame, Water, Nature, Ground, Steel, Wind, Thunder, Light, Dark,
        Phantom, Poison, Fight, Illusion, Sound, Distortion)
  val special =
    Array(Barrier, BarrierOption, ClearMind, Stopper, Thermit, Drought,
        GustOfWind, FieldBarrier, FieldProtection,
        LuckyRainbow, StunGrenade, SharkTrade, SweetNightmare, KagomeKagome,
        Miasma, ContinueGame, UpBeat, Claim, Shiranui, ShockParalyze, AcidTear)

  val values = concentrateAttack ++ diffuseAttack ++ special

  def numberToSkill(number: Int) = values.find { x => x.number == number }
}

sealed abstract class SkillCard(
  val number: Int,
  val skillName: String,
  val category: String
){
  def getWikiText(line: Int) = {
    if(number >= 101 && number <= 115)
      "|~101-115|"+TableUtils.evenLineColor(line)+getCategoryColor+"属性符|"
    else
      "|~"+number+"|"+TableUtils.evenLineColor(line)+"[["+getCategoryColor+skillName+">"+skillName+"]]|"
  }
  def getCategoryColor = {
    category match {
      case "集中" => "COLOR(Red):"
      case "拡散" => "COLOR(Blue):"
      case _ => ""
    }
  }
}
