object PureConfig {
  case class DatabaseConfig(schema: String, user: String, password: String)

  case class Config(database: DatabaseConfig)


  def main(args: Array[String]): Unit = {
    pureconfig.loadConfig[Config] match {
      case Left(configReaderFailures) =>
        sys.error(s"Encountered the following errors reading the configuration: ${configReaderFailures.toList.mkString("\n")}")
      case Right(config) =>
        println(s"schema ${config.database.schema}")
        println(s"user ${config.database.user}")
        println(s"password ${config.database.password}")
    }
  }
}
