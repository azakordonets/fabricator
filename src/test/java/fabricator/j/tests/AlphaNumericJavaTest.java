package fabricator.j.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AlphaNumericJavaTest extends JavaBaseTest {

	@DataProvider(name = "numerifyJavaDP")
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

	@Test(dataProvider = "numerifyJavaDP")
	public void testJavaNumerify(String value, String matchPattern) {
		final String result = alpha.numerify(value);
		assertTrue(result.matches(matchPattern));
	}

	@DataProvider(name = "letterifyJavaDP")
	public Object[][] letterifyDP() {
		return new Object[][] {
				{"???123", "\\w{3}\\d{3}"},
				{"???123???", "\\w{3}\\d{3}\\w{3}"},
				{"123???", "\\d{3}\\w{3}"},
				{"1?2?3?", "\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}"},
				{"123", "\\d{3}"},
				{"154,??$$%123", "\\d{3}\\W{1}\\w{2}\\W{3}\\d{3}"}
		};
	}

	@Test(dataProvider = "letterifyJavaDP")
	public void testJavaLetterify(String value, String matchPattern)  {
		final String result = alpha.letterify(value);
		assertTrue(result.matches(matchPattern));
	}

	@DataProvider(name = "botifyJavaDP")
	public Object[][] botifyDP() {
		return new Object[][] {
				{"???123###", "\\w{3}\\d{6}"},
				{"???123???###", "\\w{3}\\d{3}\\w{3}\\d{3}"},
				{"123???", "\\d{3}\\w{3}"},
				{"1?2?3?", "\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}"},
				{"123", "\\d{3}"},
				{"154,??$$%123", "\\d{3}\\W{1}\\w{2}\\W{3}\\d{3}"}
		};
	}

	@Test(dataProvider = "botifyJavaDP")
	public void testJavaBotify(String value, String matchPattern)  {
		String result = alpha.botify(value);
		assertTrue(result.matches(matchPattern));
	}

	@Test
	public void testJavaDefaultInteger() {
		int integer = alpha.getInteger();
		assertTrue(integer >= 0 && integer <= 1000);
	}

	@Test
	public void testJavaDefaultDouble() {
		Double result = alpha.getDouble();
		assertTrue(result >= 0 && result <= 1000);
		assertTrue(result instanceof Double);
	}

	@Test
	public void testJavaDefaultFloat() {
		Float floatValue = alpha.getFloat();
		assertTrue(floatValue > 0 && floatValue < 1000);
		assertTrue(floatValue instanceof Float);
	}

	@Test
	public void testJavaDefaultBoolean() {
		Boolean booleanValue = alpha.getBoolean();
		assertTrue(booleanValue == true || booleanValue == false);
		assertTrue(booleanValue instanceof Boolean);
	}

	@Test
	public void testJavaDefaultGausian() {
		Double gausian = alpha.getGausian();
		assertTrue(gausian < 1000);
		assertTrue(gausian instanceof Double);
	}

	@Test
	public void testJavaDefaultString() {
		String string = alpha.getString();
		assertTrue(string.length() == 30);
		assertTrue(string instanceof String);
	}

	@Test
	public void testJavaCustomString() {
		String extendedString = alpha.getString(50);
		assertTrue(extendedString.length() == 50);
		assertTrue(extendedString instanceof String );
	}

	@DataProvider(name = "charSets")
	public Object[][] charSets() {
		return new Object[][] {
				{"aaaa", 10},
				{"1234567890", 100},
				{"0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", 500},
				{"!@#$%^&*()_+{}\"|:?><", 30}
		};
	}

	@Test(dataProvider = "charSets")
	public void testJavaCustomStringWithSpecificCharSet(String charSet, int max)  {
		String string = alpha.getString(charSet, max);
		assert(string.length() == max);
		for (int i = 0; i < string.length(); i++) {
			final char c = string.charAt(i);
			assertTrue(charSet.indexOf(c) >= 0);
		}
	}
	
	@Test
	public void testJavaDefaultStringList() {
		final List<String> stringsJavaList = alpha.getStringsAsJavaList();
		assert(stringsJavaList.size() == 10);
		for (String string : stringsJavaList){
			assert(string.length() >= 5 && string.length() <= 100);
		}
	}

	@Test
	public void testJavaCustomStringList() {
		final List<String> stringsJavaList = alpha.getStringsAsJavaList(10, 10, 20);
		assert(stringsJavaList.size() == 20);
		for (String string : stringsJavaList){
			assert(string.length() >= 10 && string.length() <= 10);
		}
	}

	@Test
	public void testJavaCustomNumberType() {
		final int integer = alpha.getInteger(100);
		final Double aDouble = alpha.getDouble(100);
		final Float aFloat = alpha.getFloat(100);
		assertTrue(integer <= 100);
		assertTrue(aDouble <= 100);
		assertTrue(aDouble instanceof Double);
		assertTrue(aFloat <= 100);
		assertTrue(aFloat instanceof Float);
	}
	
	@Test
	public void testJavaNumbersRandomRange() {
		final int integer = alpha.getInteger(100, 300);
		final Double aDouble = alpha.getDouble(100, 300);
		final Float aFloat = alpha.getFloat(100, 300);
		assertTrue(integer >= 100 && integer <= 300);
		assertTrue(aDouble >= 100 && aDouble <= 300);
		assertTrue(aDouble instanceof Double);
		assertTrue(aFloat >= 100 && aFloat <= 300);
		assertTrue(aFloat instanceof Float);
	}
	
	@Test
	public void testJavaIntRangeWithStep() {
		final List<Object> integerRangeAsJavaList = alpha.getIntegerRangeAsJavaList(1, 10, 1);
		ArrayList<Integer> expectedList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		assertEquals(expectedList, integerRangeAsJavaList);
	}

	@Test
	public void testJavaDoubleRangeWithStep() {
		final List<Object> doubleRangeAsJavaList = alpha.getDoubleRangeAsJavaList(1, 10, 1);
		ArrayList<Double> expectedList = new ArrayList<>(Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0, 10.0));
		assertEquals(expectedList, doubleRangeAsJavaList);
	}

	@Test
	public void testJavaFloatRangeWithStep() {
		final List<Object> floatRangeAsJavaList = alpha.getFloatRangeAsJavaList(1, 10, 1);
		ArrayList<Float> expectedList = new ArrayList<>(Arrays.asList(1.0f,2.0f,3.0f,4.0f,5.0f,6.0f,7.0f,8.0f,9.0f, 10.0f));
		assertEquals(expectedList, floatRangeAsJavaList);
	}

	@Test
	public void testJavaHash()  {
		String hash = alpha.hash();
		assertTrue(hash.length() == 40);
		String customLengthHash = alpha.hash(10);
		assertTrue(customLengthHash.length() == 10);
	}

	@Test
	public void testJavaGuid()  {
		String guid = alpha.guid();
		assertTrue(guid.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}"));
	}
	
}
