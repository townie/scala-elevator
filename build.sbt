lazy val root = (project in file(".")).
  settings(
    name := "elevator",
    version := "1.0",
    scalaVersion := "2.11.7",
    mainClass in (Compile, run) := Some("Run")
)
