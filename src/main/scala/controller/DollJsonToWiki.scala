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

object DollJsonToWiki {
  def main(args: Array[String]):Unit = {
    getJsonList.map(jsonFileName => {
      val doll: DollData = jsonToDoll(jsonFileName)
      val printWriter: PrintWriter = new PrintWriter(new OutputStreamWriter( new FileOutputStream("wikitext/"+(jsonFileName.split("\\.")(0))+".txt") ))
      printWriter.print(doll.toWiki)
      printWriter.close
    })
  }

  /* JSONファイルを読み込み、case classにマッピングする */
  def jsonToDoll(jsonFileName: String): DollData = {
    val ir: InputStreamReader = new InputStreamReader( new FileInputStream("resource/"+jsonFileName), "UTF-8" )
      val jsr: JsonReader = new JsonReader(ir)
      val gson: Gson = new Gson()
      gson.fromJson(ir, classOf[DollData])
  }

  /* resourceディレクトリ内の.json一覧 */
  def getJsonList = {
    val dir: File = new File("resource")
    dir.list.filter(x => x.endsWith(".json"))
  }
}