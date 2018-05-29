import com.typesafe.config.ConfigFactory

object TypeSafeConfig {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val schema = config.getString("database.schema")
    val user = config.getString("database.user")
    val password = config.getString("database.password")

    println(s"schema $schema")
    println(s"user $user")
    println(s"password $password")
  }
}
