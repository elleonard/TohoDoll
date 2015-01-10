package utils

object TableUtils {
/* 偶数行のみ色をつける */
  def evenLineColor(lineNum: Int) = {
    if(lineNum%2 != 0){"BGCOLOR(#F8FAFB):"} else {""}
  }
}