name := "implicits"
version := "1.0.0"

addScala212

deps ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalautils" %% "scalautils" % "2.1.5"
)
