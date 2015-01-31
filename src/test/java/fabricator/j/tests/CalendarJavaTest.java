package fabricator.j.tests;

import org.testng.annotations.Test;
import java.util.List;

import play.api.libs.json.JsObject;
import play.api.libs.json.JsValue;
import scala.util.parsing.json.JSONObject;

public class CalendarJavaTest extends JavaBaseTest {

	@Test
	public void testDatesRange() {
		final List<String> yearRange = calendar.datesRangeJavaList(2001, 1, 1, 2010, 1, 1, "year", 1);
		for (String year: yearRange) {
			System.out.println(year);
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
}
