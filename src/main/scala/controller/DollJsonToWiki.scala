package controller

import java.io.InputStreamReader
import java.io.FileInputStream
import com.google.gson.Gson
import model.Doll
import com.google.gson.stream.JsonReader
import model.DollData
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.OutputStreamWriter
import config.TDConfig
import utils.TDLogger

/**
 * JSONを読み込み、Wikiソース形式にして出力する
 *
 * 幻想人形演舞 ユメノカケラ仕様
 */
object DollJsonToWiki {
  def main(args: Array[String]):Unit = {
    if(args.length > 0){
      args(0) match {
        /* スキルDBチェック */
        case "checkskill" => {
          getJsonList.map(jsonFileName => {
            TDLogger.info("JSONからスキルデータをチェックします。from:resource/"+jsonFileName)
            val doll: DollData = jsonToDoll(jsonFileName)
            val errorSkillNum = doll.checkSkill
            if(errorSkillNum > 0){
              TDLogger.warning(""+errorSkillNum+"個のスキルが未定義です。")
            } else {
              TDLogger.info("DB未定義のスキルはありません。")
            }
          })
        }
        case _ =>
      }
    } else {  /* jsonからwikiテキストへ変換 */
      getJsonList.map(jsonFileName => {
        TDLogger.info("JSONからWikiソース形式に変換を行います。from:resource/"+jsonFileName)
        val doll: DollData = jsonToDoll(jsonFileName)
        val printWriter: PrintWriter = new PrintWriter(new OutputStreamWriter( new FileOutputStream("wikitext/"+(jsonFileName.split("\\.")(0))+".txt") ))
        printWriter.print(doll.toWiki)
        printWriter.close
        TDLogger.info("Wikiソース形式に変換しました！ to:wikitext/"+(jsonFileName.split("\\.")(0))+".txt")
      })
    }
  }

  /* JSONファイルを読み込み、case classにマッピングする */
  def jsonToDoll(jsonFileName: String): DollData = {
    TDLogger.info("JSONファイルresource/"+jsonFileName+"をロードします。")
    val ir: InputStreamReader = new InputStreamReader( new FileInputStream("resource/"+jsonFileName), "UTF-8" )
      val jsr: JsonReader = new JsonReader(ir)
      val gson: Gson = new Gson()
      gson.fromJson(ir, classOf[DollData])
  }

  /* resourceディレクトリ内の.json一覧 */
  def getJsonList = {
    val dir: File = new File("resource")
    dir.list.filter(x =>
      x.endsWith(".json") && !TDConfig.IGNORE_FILES.contains(x))
  }
}
