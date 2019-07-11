package clients

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.ws._
import support.LogSupport

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SomeClient @Inject()(val ws: WSClient, config: Configuration)
                          (implicit ec: ExecutionContext) extends LogSupport {

  lazy val domain = s"http://localhost:${config.get[Int]("play.server.http.port")}"
  lazy val url = s"$domain${controllers.routes.DummyController.get.url}"

  def call: Future[WSResponse] = {
    ws
      .url(url)
      .addHttpHeaders("Extra-Header" -> "value 1")
      .get()
  }
}
