package config

object TDConfig {
  val SKILL_LIST_CSV = "./csv/skill-list.csv"
  val SKILL_LIST_COLUMN = Seq("id", "skillname", "element", "category",
      "power","hit", "sp", "speed", "buen", "description")

  val IGNORE_FILES = Seq("template.json")
}
