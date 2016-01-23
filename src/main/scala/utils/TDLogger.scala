package utils

import utils.Colored._

object TDLogger {
  /**
   * お知らせを表示する
   */
  def info(msg: String) = {
    println(("[お知らせ]".colored.green)+": "+msg)
  }

  /**
   * エラーメッセージを出す
   */
  def error(msg: String) = {
    println("[エラー]".colored.red +": "+ msg);
  }

  /**
   * 警告メッセージを出す
   */
  def warning(msg: String) = {
    println("[警告]".colored.yellow +": "+msg)
  }
}