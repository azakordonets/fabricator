package fabricator.j.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class CalendarJavaTest extends JavaBaseTest {

	@Test
	public void testDatesRange() {
		final List<String> yearRange = calendar.datesRangeJavaList(2001, 1, 1, 2010, 1, 1, "year", 1);
		for (String year: yearRange) {
		}
	}
	
	@Test
	public void testDatesRangeWithJson() {
		String config = "{\n"
				+ "  \"start\": {\n"
				+ "    \"year\": 2001,\n"
				+ "    \"month\": 1,\n"
				+ "    \"day\": 1,\n"
				+ "    \"hour\": 0,\n"
				+ "    \"minute\": 0\n"
				+ "  },\n"
				+ "  \"end\": {\n"
				+ "    \"year\": 2010,\n"
				+ "    \"month\": 1,\n"
				+ "    \"day\": 1,\n"
				+ "    \"hour\": 0,\n"
				+ "    \"minute\": 0\n"
				+ "  },\n"
				+ "  \"step\": {\n"
				+ "    \"year\": 1,\n"
				+ "    \"month\": 1,\n"
				+ "    \"day\": 1,\n"
				+ "    \"hour\": 0,\n"
				+ "    \"minute\": 0\n"
				+ "  },\n"
				+ "  \"format\": \"dd-MM-yyyy hh:mm\"\n"
				+ "}";
		final List<String> strings = calendar.datesRangeJavaList(config);

	}

	@DataProvider
	public Object[][] datesRangeDP() {
		return new Object[][]{
				{ 2001, 1, 1, 2010, 1, 1, "year", 1, 9 },
				{ 2001, 1, 1, 2010, 1, 1,"year", 2, 5 },
				{ 2001, 1, 1, 2010, 1, 1,"month", 1, 108},
				{ 2001, 1, 1, 2010, 1, 1,"month", 2, 54},
				{ 2001, 1, 1, 2001, 10, 1,"day", 10, 28},
		};
	}

	
	@Test(dataProvider = "datesRangeDP")
	public void testDatesRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, String type, int step, int expectedSize) {
		final List<String> datesRange = calendar.datesRangeJavaList(startYear, startMonth, startDay, endYear, endMonth, endDay, type, step);
		assertEquals(expectedSize, datesRange.size());
	}
	
}
