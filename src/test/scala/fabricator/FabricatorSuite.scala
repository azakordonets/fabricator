package fabricator

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
/**
  * Created by Andrew Zakordonets on 16/05/14.
 */
@RunWith(classOf[JUnitRunner])
class FabricatorSuite extends FunSuite{

  test("test"){
    assert(true)
  }

  test("Check first name"){
    val fabr = new Fabricator()
    assert(fabr.firstName().equals("Andrew"))
  }

  test("Check last name"){
    val fabr = new Fabricator()
    assert(fabr.lastName().equals("Zakordonets"))
  }

//  test("Check file read"){
//    readFile()
//  }

}
