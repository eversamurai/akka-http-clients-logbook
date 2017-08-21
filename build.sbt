name := "microservice-client-book"

version := "1.0"

scalaVersion := "2.11.7"

  libraryDependencies +=  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0"
  libraryDependencies += "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0"
  libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "1.0"
  libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0"
  libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "0.11.7"
  libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  libraryDependencies +=   "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0"


resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"