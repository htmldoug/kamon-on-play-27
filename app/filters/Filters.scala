package filters

import akka.stream.Materializer
import javax.inject.Inject
import kamon.Kamon
import play.api.http.DefaultHttpFilters
import play.api.http.EnabledFilters
import play.api.mvc.{Filter, RequestHeader, Result}
import play.filters.gzip.GzipFilter

import scala.concurrent.Future

class Filters @Inject()(
  defaultFilters: EnabledFilters,
  gzip: GzipFilter,
  kamonKeyFilter: KamonStoreContextTagFilter,
  log: LoggingFilter
) extends DefaultHttpFilters(defaultFilters.filters :+ kamonKeyFilter :+ gzip :+ log: _*)

class KamonStoreContextTagFilter @Inject()(implicit val mat: Materializer) extends Filter {

  override def apply(f: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    Kamon.storeContextTag("filter", "filter") {
      f(rh)
    }
  }
}


