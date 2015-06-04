package fabricator.j.tests;

import fabricator.enums.DateRangeType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class CalendarJavaTest extends JavaBaseTest {

	@Test
	public void testDatesRange() {
		final List<String> yearRange = calendar.datesRange()
				.startYear(2001)
				.startMonth(1)
				.startMonth(1)
				.stepEvery(1, DateRangeType.YEARS)
				.endYear(2010)
				.endMonth(1)
				.endDay(1)
				.asJavaList();
		assertEquals(9, yearRange.size());
	}

	@DataProvider
	public Object[][] datesRangeDP() {
		return new Object[][]{
				{ 2001, 1, 1, 2010, 1, 1, DateRangeType.YEARS, 1, 9 },
				{ 2001, 1, 1, 2010, 1, 1,DateRangeType.YEARS, 2, 5 },
				{ 2001, 1, 1, 2010, 1, 1, DateRangeType.MONTHS, 1, 108},
				{ 2001, 1, 1, 2010, 1, 1,DateRangeType.MONTHS, 2, 54},
				{ 2001, 1, 1, 2010, 1, 1,DateRangeType.WEEKS, 2, 235},
				{ 2001, 1, 1, 2001, 10, 1,DateRangeType.DAYS, 10, 27},
				{ 2001, 1, 1, 2001, 10, 1,DateRangeType.HOURS, 10, 648},
				{ 2001, 1, 1, 2001, 10, 1,DateRangeType.MINUTES, 10, 38874},
		};
	}

	@Test(dataProvider = "datesRangeDP")
	public void testDatesRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, DateRangeType type, int step, int expectedSize) {
		final List<String> datesRange = calendar.datesRange()
												.startYear(startYear)
												.startMonth(startMonth)
												.startMonth(startDay)
												.stepEvery(step, type)
												.endYear(endYear)
												.endMonth(endMonth)
												.endDay(endDay)
												.asJavaList();
		assertEquals(expectedSize, datesRange.size());
	}
}
