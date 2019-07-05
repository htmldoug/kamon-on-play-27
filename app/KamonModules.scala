import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory
import kamon.Kamon

class KamonModules extends AbstractModule {
  override def configure(): Unit = {
    Kamon.reconfigure(ConfigFactory.load())
  }
}
