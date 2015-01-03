package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class BaseTestSuite extends TestNGSuite with LazyLogging {

  val util = UtilityService()
  val alpha = Fabricator.alphaNumeric()
  val location = Fabricator.location()
  val calendar = Fabricator.calendar()
  val contact = Fabricator.contact()
  val finance = Fabricator.finance()
  val internet = Fabricator.internet()
  val mobile = Fabricator.mobile()
  val file = Fabricator.file()
  val debugEnabled: Boolean = util.getProperty("loggerDebug").equals("true")
  var words = Fabricator.words()

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessException() = {
    val result = util.isLess(1, 1.5)
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessOrEqualException() = {
    val result = util.isLessOrEqual(1, 1.5)
  }

}
