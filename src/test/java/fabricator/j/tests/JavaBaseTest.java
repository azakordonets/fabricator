package fabricator.j.tests;

import fabricator.*;
import org.scalatest.testng.TestNGSuite;

public class JavaBaseTest extends TestNGSuite {

	final Alphanumeric alpha = Fabricator.alphaNumeric();
	final Calendar calendar = Fabricator.calendar();
}
