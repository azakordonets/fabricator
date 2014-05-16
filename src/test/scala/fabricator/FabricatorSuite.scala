package fabricator

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import Fabricator._

/**
  * Created by Andrew Zakordonets on 16/05/14.
 */
@RunWith(classOf[JUnitRunner])
class FabricatorSuite extends FunSuite{

  test("test"){
    assert(true)
  }

  test("Check first name"){
    assert(firstName().equals("Andrew"))
  }

  test("Check last name"){
    assert(lastName().equals("Zakordonets"))
  }

}
