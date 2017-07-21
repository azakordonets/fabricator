package fabricator

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class BaseTestSuite extends TestNGSuite with LazyLogging {

  lazy val util: UtilityService = UtilityService()
  lazy val alpha: Alphanumeric = Fabricator.alphaNumeric()
  lazy val location: Location = Fabricator.location()
  lazy val calendar: Calendar = Fabricator.calendar()
  lazy val contact: Contact = Fabricator.contact()
  lazy val finance: Finance = Fabricator.finance()
  lazy val internet: Internet = Fabricator.internet()
  lazy val mobile: Mobile = Fabricator.mobile()
  lazy val file: FileGenerator = Fabricator.file()
  lazy val debugEnabled: Boolean = util.getProperty("loggerDebug").equals("true")
  var words: Words = Fabricator.words()

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessException() = {
    assert(util.isLess(1, 1.5))
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessOrEqualException() = {
    assert(util.isLessOrEqual(1, 1.5))
  }

}
