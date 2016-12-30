import sbt._
import sbt.Keys._

object Build {
  val buildVersion = "0.1.0"

  // Let integration tests inherit test configs and classpaths
  lazy val it = config("it") extend Test
  lazy val itSettings = inConfig(it)(Defaults.testSettings)

  // These settings can be shared across projects
  val commonSettings = Seq(
    // tell scaladoc where it can find the API documentation for managed dependencies
    autoAPIMappings := true,
    fork in Test := true,
    javacOptions ++= Seq("-source", Dependencies.Java, "-target", Dependencies.Java, "-Xlint"),
    organization := "com.solarmosaic",
    sbtVersion := Dependencies.Sbt,
    scalacOptions += s"-target:jvm-${Dependencies.Java}",
    scalaVersion := Dependencies.Scala,
    // Don't download javadocs from transitive dependencies
    transitiveClassifiers := Seq(Artifact.SourceClassifier),
    version := buildVersion
  )
}

object Dependencies {
  val Jackson = "2.8.4"
  val Java = "1.8"
  val Sbt = "0.13.9"
  val Scala = "2.11.8"
  val Slf4j = "1.7.12"
  val Specs2 = "3.8.5"
  val SpringBoot = "1.4.3.RELEASE"
}
