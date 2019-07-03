package support

import play.api.Logger

trait LogSupport {
  val logger = Logger(this.getClass)
}
