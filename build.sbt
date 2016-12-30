import Build._

lazy val root = (project in file("."))
  .configs(it)
  .settings(itSettings: _*)
  .settings(commonSettings: _*)
  .settings(
    name := "spring-boot-web-scala",
    libraryDependencies ++= Seq(
      // Compile-time
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % Dependencies.Jackson,
      "org.springframework.boot" % "spring-boot-starter-actuator" % Dependencies.SpringBoot,
      "org.springframework.boot" % "spring-boot-starter-web" % Dependencies.SpringBoot,

      // Test
      "org.specs2" %% "specs2-core" % Dependencies.Specs2 % "it, test",
      "org.specs2" %% "specs2-mock" % Dependencies.Specs2 % "it, test",
      "org.springframework.boot" % "spring-boot-starter-test" % Dependencies.SpringBoot % "it, test"
    )
  )
