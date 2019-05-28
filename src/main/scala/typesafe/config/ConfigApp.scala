import com.typesafe.config.ConfigFactory

object ConfigApp extends App {
  val config = ConfigFactory.load()
  println(s"config : ${config.getString("my.key")}")

  val alphaConfig = ConfigFactory.load("alpha")
  println(s"alpha config : ${alphaConfig.getString("my.key")}")
}
