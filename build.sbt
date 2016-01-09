import AssemblyKeys._

name := "tohodoll"

version := "1.3"

scalaVersion := "2.10.6"

resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
	"com.google.code.gson" % "gson" % "2.3.1",
	"org.scalikejdbc" %% "csvquery" % "1.2",
	"org.skinny-framework" %% "skinny-orm" % "2.0.3"
)

assemblySettings
