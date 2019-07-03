import javax.inject.Singleton
import play.api.http.HttpErrorHandler
import play.api.http.Status._
import play.api.libs.json.{Json, Writes}
import play.api.mvc.Results._
import play.api.mvc.{RequestHeader, Result}
import support.LogSupport

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler with LogSupport {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    statusCode match {
      case NOT_FOUND => Future.successful(NotFound(Json.toJson(ErrorResponse("not_found", message, "C001"))))
      case _ => Future.successful(Status(statusCode))
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error("Unexpected error ", exception)
    Future.successful(InternalServerError(Json.toJson(ErrorResponse("internal_server_error", exception.getMessage, "C002"))))
  }
}

case class ErrorResponse(error: String, message: String, errorCode: String)

object ErrorResponse {
  implicit val errorResponseWrites: Writes[ErrorResponse] = Json.writes[ErrorResponse]
}
