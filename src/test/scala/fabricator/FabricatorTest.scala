package fabricator

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import Fabricator._

/**
  * Created by Andrew Zakordonets on 16/05/14.
 */
@RunWith(classOf[JUnitRunner])
class FabricatorTest extends FunSuite{


  test("Check first name"){
    assert(firstName() === "Andrew")
  }

  test("Check last name"){
    assert(lastName() === "Zakordonets")
  }

}
