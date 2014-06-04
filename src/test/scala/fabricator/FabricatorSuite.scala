package fabricator

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.github.nscala_time.time.Imports._
/**
  * Created by Andrew Zakordonets on 16/05/14.
 */
@RunWith(classOf[JUnitRunner])
class FabricatorSuite extends FunSuite{

  val fabr = new Fabricator

  test("Check first name"){
    assert(fabr.firstName().nonEmpty)
  }

  test("Check last name"){
    assert(fabr.lastName().toString.nonEmpty)
  }

  test("Check e-mail"){
    assert(fabr.email().nonEmpty)
  }

  test("Testing numerify "){
    assert(fabr.numerify("###ABC").matches("\\d{3}\\w{3}"))
  }

  test("Testing letterify "){
    assert(fabr.letterify("123???").matches("\\d{3}\\w{3}"))
  }

  test("Testing default date getter"){
    assert(fabr.date().getDate().equals(DateTime.now.toString("dd-mm-yyyy")))
  }

}
