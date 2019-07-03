package controllers

import javax.inject.{Inject, Singleton}
import kamon.Kamon
import kamon.tag.Tag
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import support.LogSupport

@Singleton
class DummyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with LogSupport {

  def test: Action[AnyContent] = Action {
    val tags = Kamon.currentContext().tags.iterator()
      .map(t => {
        (t.key, Tag.unwrapValue(t).toString)
      })
      .mkString("[", ", ", "]")
    Ok(
      s"""
         |Hello world!
         |Trace ID: ${Kamon.currentSpan().trace.id.string}
         |Span ID: ${Kamon.currentSpan().id.string}
         |Tags: $tags
         |""".stripMargin)
      .withHeaders(CACHE_CONTROL -> "max-age=3600", "Custom-Header" -> "xxx")
    //    Ok(
    //      s"""
    //         |Hello world!
    //         |Trace ID: {Kamon.currentSpan().trace.id.string}
    //         |Span ID: {Kamon.currentSpan().id.string}
    //         |Tags: tags
    //         |""".stripMargin)
  }
}
