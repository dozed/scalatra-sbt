
organization := "org.scalatra"
name := "scalatra-sbt test"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.12.2"

fork in Test := true

val ScalatraVersion = "2.5.0"

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

enablePlugins(JettyPlugin)

containerPort in Jetty := 8090

lazy val check = taskKey[Unit]("check if / is available")


check := {

  import org.http4s._

  val client = org.http4s.client.blaze.defaultClient
  val uri = Uri.uri("http://localhost:8090/")

  client.expect[String](uri).unsafePerformSyncAttempt.fold(
    error => sys.error("unexpected result: " + error),
    res => if (res != "hey") sys.error("unexpected output: " + res) else ()
  )

}
