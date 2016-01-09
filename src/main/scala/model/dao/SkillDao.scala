package model.dao

import config.TDConfig
import csvquery._
import scalikejdbc._
import model.entity.SkillEntity

object SkillCSV extends SkinnyCSVMapper[SkillEntity] {
  def csv = CSV(TDConfig.SKILL_LIST_CSV, TDConfig.SKILL_LIST_COLUMN)
  override def extract(rs: WrappedResultSet,
      rn: ResultName[SkillEntity]): SkillEntity = autoConstruct(rs, rn)
}

object SkillDao {
  implicit val session = autoCSVSession
  def getByID(id: Int): Option[SkillEntity] = {
    SkillCSV.where('id -> id).apply().headOption
  }

  def getByName(name: String): Option[SkillEntity] = {
    SkillCSV.where('skillname -> name).apply().headOption
  }
}
