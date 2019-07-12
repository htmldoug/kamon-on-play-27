import com.google.inject.AbstractModule
import kamon.Kamon
import play.api.{Configuration, Environment}

// FIXME it fails when run in DEV mode with kamon-bundle:2.0.0-RC3
class KamonModules(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    println(s"Class loader of ${this.getClass.getName} is: ${this.getClass.getClassLoader}")

    // Class `support.kamon.HeadersPropagation` can be loaded from this#ClassLoader:
    Class.forName("support.kamon.HeadersPropagation", false, this.getClass.getClassLoader)

    // The following line fails with `java.lang.ClassNotFoundException: support.kamon.HeadersPropagation`
    Kamon.reconfigure(configuration.underlying)
  }
}
