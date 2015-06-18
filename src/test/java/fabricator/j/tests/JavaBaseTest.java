package fabricator.j.tests;

import fabricator.Alphanumeric;
import fabricator.Calendar;
import fabricator.Fabricator;
import fabricator.Internet;
import org.scalatest.testng.TestNGSuite;

public class JavaBaseTest extends TestNGSuite {

	final Alphanumeric alpha = Fabricator.alphaNumeric();
	final Calendar calendar = Fabricator.calendar();
	final Internet internet = Fabricator.internet();
}
