package controllers

import clients.SomeClient
import javax.inject.{Inject, Singleton}
import kamon.Kamon
import kamon.tag.Tag
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import support.LogSupport

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class DummyController @Inject()(val controllerComponents: ControllerComponents,
                                val someClient: SomeClient) extends BaseController with LogSupport {

  def get: Action[AnyContent] = Action {
    logger.info(s"************************************")
    logger.info(s"*** Handling ${controllers.routes.DummyController.get().url}")
    logger.info(s"*** Tags: $currentTags")
    logger.info(s"************************************")
    Ok(bodyResponse(controllers.routes.DummyController.get().url))
      .withHeaders(headers = "Custom-Header" -> "xxx")
  }

  def outgoingRequest: Action[AnyContent] = Action.async {
    logger.info(s"************************************")
    logger.info(s"*** Handling ${controllers.routes.DummyController.outgoingRequest().url}")
    logger.info(s"*** Tags: $currentTags")
    logger.info(s"*** Sending outgoing request to ${someClient.url}")
    logger.info(s"************************************")
    someClient
      .call
      .map(response => {
        logger.info(s"Client response: $response")
        Ok(bodyResponse(name = controllers.routes.DummyController.outgoingRequest().url))
      })
  }

  private def currentTags: String = Kamon.currentContext()
    .tags
    .iterator()
    .map(t => {
      (t.key, Tag.unwrapValue(t).toString)
    })
    .mkString("[", ", ", "]")

  private def bodyResponse(name: String, msg: Option[String] = None): String = {
    s"""
       |Request to: $name
       |Trace ID: ${Kamon.currentSpan().trace.id.string}
       |Span ID: ${Kamon.currentSpan().id.string}
       |Tags: $currentTags
       |${msg.getOrElse("")}
       |""".stripMargin
  }
}
