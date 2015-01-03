package fabricator.j.tests;

import fabricator.*;
import org.testng.TestNG;

public class JavaBaseTest extends TestNG {

	protected Alphanumeric alpha = Fabricator.alphaNumeric();
	protected Calendar calendar = Fabricator.calendar();
	protected Contact contact = Fabricator.contact();
	protected FileGenerator file = Fabricator.file();
	protected Finance finance = Fabricator.finance();
	protected Internet internet = Fabricator.internet();
	protected Location location = Fabricator.location();
	protected Mobile mobile = Fabricator.mobile();
	protected Words words = Fabricator.words();
}
