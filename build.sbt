ThisBuild / organization := "com.example"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "scala-spark-template",
    
    // Scala 3 settings
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-language:higherKinds"
    ),
    
    // Spark dependencies (provided to reduce jar size)
    // Using Scala 2.13 artifacts with cross-version compatibility
    libraryDependencies ++= Seq(
      ("org.apache.spark" %% "spark-core" % "3.5.1" % "provided")
        .cross(CrossVersion.for3Use2_13)
        .exclude("org.scala-lang.modules", "scala-xml_2.13"),
      
      ("org.apache.spark" %% "spark-sql" % "3.5.1" % "provided")
        .cross(CrossVersion.for3Use2_13)
        .exclude("org.scala-lang.modules", "scala-xml_2.13"),
      
      // Add scala-xml for Scala 3 explicitly
      "org.scala-lang.modules" %% "scala-xml" % "2.2.0",
      
      // Test dependencies
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      ("org.apache.spark" %% "spark-core" % "3.5.1" % Test classifier "tests")
        .cross(CrossVersion.for3Use2_13)
        .exclude("org.scala-lang.modules", "scala-xml_2.13"),
      ("org.apache.spark" %% "spark-sql" % "3.5.1" % Test classifier "tests")
        .cross(CrossVersion.for3Use2_13)
        .exclude("org.scala-lang.modules", "scala-xml_2.13")
    ),
    
    // Assembly settings for fat jar creation
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
    
    // Test settings with Java 11+ compatibility
    Test / parallelExecution := false,
    Test / fork := true,
    Test / javaOptions ++= Seq(
      "-Xmx2g",
      "--add-opens=java.base/java.lang=ALL-UNNAMED",
      "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
      "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
      "--add-opens=java.base/java.io=ALL-UNNAMED",
      "--add-opens=java.base/java.net=ALL-UNNAMED",
      "--add-opens=java.base/java.nio=ALL-UNNAMED",
      "--add-opens=java.base/java.util=ALL-UNNAMED",
      "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED",
      "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
      "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
      "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED",
      "--add-opens=java.base/sun.security.action=ALL-UNNAMED",
      "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED",
      "--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED"
    )
  )