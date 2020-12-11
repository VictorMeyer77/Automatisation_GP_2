name := "source"

version := "0.1"

scalaVersion := "2.12.2"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "com.google.code.gson" % "gson" % "1.7.1"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.1" % "compile"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1" % "compile"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.18"

assemblyMergeStrategy in assembly := {
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) =>
    xs map {_.toLowerCase} match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case "services" :: _ =>  MergeStrategy.filterDistinctLines
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case _ => MergeStrategy.first
    }
  case _ => MergeStrategy.first
}

mainClass in (Compile, run) := Some("com.esgi.gptwo.Main")
mainClass in assembly := Some("com.esgi.gptwo.Main")