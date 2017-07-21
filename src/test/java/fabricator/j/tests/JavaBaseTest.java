package fabricator.j.tests;

import fabricator.*;
import org.scalatest.testng.TestNGSuite;
import scala.util.Random;

class JavaBaseTest extends TestNGSuite {

	final Alphanumeric alpha = Fabricator.alphaNumeric();
	final Calendar calendar = Fabricator.calendar();
	final Internet internet = Fabricator.internet();
	final FileGenerator file = Fabricator.file();
	final UtilityService util = new UtilityService("us", new Random());
}
