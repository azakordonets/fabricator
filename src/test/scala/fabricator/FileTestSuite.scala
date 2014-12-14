package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class FileTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val util = new UtilityService()
  val alpha = fabr.alphaNumeric()
  val contact = fabr.contact()
  val calendar = fabr.calendar()
  var wordsFaker = fabr.words()
  val internet = fabr.internet()
  val finance = fabr.finance()
  val location = fabr.location()
  val file = fabr.file()

  @Test
  def testMidi() = {
    file.midiFile()
  }


}
