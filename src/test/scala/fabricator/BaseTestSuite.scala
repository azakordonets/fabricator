package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class BaseTestSuite extends TestNGSuite with LazyLogging {

  lazy val util = UtilityService()
  lazy val alpha = Fabricator.alphaNumeric()
  lazy val location = Fabricator.location()
  lazy val calendar = Fabricator.calendar()
  lazy val contact = Fabricator.contact()
  lazy val finance = Fabricator.finance()
  lazy val internet = Fabricator.internet()
  lazy val mobile = Fabricator.mobile()
  lazy val file = Fabricator.file()
  lazy val debugEnabled: Boolean = util.getProperty("loggerDebug").equals("true")
  var words = Fabricator.words()

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessException() = {
    assert(util.isLess(1, 1.5))
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessOrEqualException() = {
    assert(util.isLessOrEqual(1, 1.5))
  }

}
