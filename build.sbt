name := """play-rest-api-with-jpa"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.4.0.Final"

PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
