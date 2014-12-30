package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

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

  @Test
  def testFabricatorConstructors() = {
    //testing that it's possible to create fabricator object with another locale
    // For now we have only us file, but in the future we'll be able to extend this test.
    def newFabr = new Fabricator("us")
    assert(newFabr != null)
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessException() = {
    val result = util.isLess(1, 1.5)
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testUtilityLessOrEqualException() = {
    val result = util.isLessOrEqual(1, 1.5)
  }

}
