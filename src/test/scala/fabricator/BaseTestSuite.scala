package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite

class BaseTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val util = new UtilityService()
  val alpha = fabr.alphaNumeric()
  val location = fabr.location()
  val calendar = fabr.calendar()
  val contact = fabr.contact()
  val finance = fabr.finance()
  val internet = fabr.internet()
  val mobile = fabr.mobile()
  val file = fabr.file()
  val debugEnabled: Boolean = util.getProperty("loggerDebug").equals("true")
  var words = fabr.words()

}
