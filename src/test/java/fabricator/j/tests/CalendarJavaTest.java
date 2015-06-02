package fabricator.j.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CalendarJavaTest extends JavaBaseTest {

	@Test
	public void testDatesRange() {
		final List<String> yearRange = calendar.datesRangeJavaList(2001, 1, 1, 2010, 1, 1, "year", 1);
		for (String year: yearRange) {
		}
	}
	
	@Test
	public void testJavaDaysRange() {
		final List<String> daysRangeList = calendar.daysRangeAsJavaList(2010, 1, 1, 16, 1);
		for (int i = 0; i < daysRangeList.size()-1; i++) {
			int day = Integer.valueOf(daysRangeList.get(i));
			int nextDay = Integer.valueOf(daysRangeList.get(i+1));
			assertTrue(day >= 1 && day <= 16);
			assertTrue(nextDay - day == 1);
		}
	}
	
	@Test
	public void testJavaDatesRangeWithJson() throws IOException {
		String config = readFileContent();
		final List<String> datesRangeList = calendar.datesRangeJavaList(config);
		assertEquals(10, datesRangeList.size());
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
	
	private String readFileContent() throws IOException {
		String path = CalendarJavaTest.class.getResource("/"+ "javaDatesRangeJson.json").toString();
		final List<String> strings = Files.readAllLines(Paths.get(path.substring(5, path.length())), Charset.defaultCharset());
		StringBuilder builder = new StringBuilder();
		for (String el: strings){
			builder.append(el);
		}
		return builder.toString();
	}
	
}
