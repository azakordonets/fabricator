package fabricator.j.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AlphaNumericJavaTest extends JavaBaseTest {

	@DataProvider(name = "numerifyDP")
	public Object[][] numerifyDP() {
		return new Object[][] {
				{ "###ABC", "\\d{3}\\w{3}" },
				{ "###ABC###", "\\d{3}\\w{3}\\d{3}" },
				{ "ABC###", "\\w{3}\\d{3}" },
				{ "A#B#C#", "\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}" },
				{ "ABC", "\\w{3}" },
				{ "154,#$$%ABC", "\\d{3}\\W{1}\\d{1}\\W{3}\\w{3}" }
		};
	}

	@Test(dataProvider = "numerifyDP")
	public void testNumerify(String value, String matchPattern) {
		final String result = alpha.numerify(value);
		assertTrue(result.matches(matchPattern));
	}

}
