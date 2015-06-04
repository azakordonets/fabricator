package fabricator.j.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("ALL")
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
	
	@Test
	public void testNumerifyAsJavaList() {
		final List<String> stringList = alpha.numerifyAsJavaList("###ABC", 10);
		assertEquals(10, stringList.size());
		for (String el: stringList){
			assertTrue(el.matches("\\d{3}\\w{3}"));
		}
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
	
	@Test
	public void testLetterifyAsJavaList() {
		final List<String> stringList = alpha.letterifyAsJavaList("???123", 10);
		assertEquals(10, stringList.size());
		for (String el: stringList){
			assertTrue(el.matches("\\w{3}\\d{3}"));
		}
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
	public void testBotifyAsJavaList() {
		final List<String> stringList = alpha.botifyAsJavaList("???123###", 10);
		assertEquals(10, stringList.size());
		for (String el: stringList){
			assertTrue(el.matches("\\w{3}\\d{6}"));
		}
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
		assertTrue(booleanValue || !booleanValue);
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
		assert(stringsJavaList.size() == 100);
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
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testJavaIntRangeWithStepException() {
		alpha.getIntegerRangeAsJavaList(100, 200, 0);
	}

	@Test
	public void testJavaDoubleRangeWithStep() {
		final List<Object> doubleRangeAsJavaList = alpha.getDoubleRangeAsJavaList(1, 10, 1);
		ArrayList<Double> expectedList = new ArrayList<>(Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0, 10.0));
		assertEquals(expectedList, doubleRangeAsJavaList);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testJavaDoubleRangeWithStepException() {
		alpha.getDoubleRangeAsJavaList(100, 200, 0);
	}

	@Test
	public void testJavaFloatRangeWithStep() {
		final List<Object> floatRangeAsJavaList = alpha.getFloatRangeAsJavaList(1, 10, 1);
		ArrayList<Float> expectedList = new ArrayList<>(Arrays.asList(1.0f,2.0f,3.0f,4.0f,5.0f,6.0f,7.0f,8.0f,9.0f, 10.0f));
		assertEquals(expectedList, floatRangeAsJavaList);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testJavaFloatRangeWithStepException() {
		alpha.getFloatRangeAsJavaList(100, 200, 0);
	}
	
	@Test
	public void testStringsAsJavaList()  {
		// check default method
		final List<String> stringsAsJavaList = alpha.getStringsAsJavaList();
		assertEquals(100, stringsAsJavaList.size());
		for (String el: stringsAsJavaList) {
			assertTrue(el.length() >= 5 && el.length() <=100);
		}
		//check custom method
		final List<String> stringsAsJavaListCustom = alpha.getStringsAsJavaList(30, 40, 20);
		assertEquals(20, stringsAsJavaListCustom.size());
		for (String el: stringsAsJavaListCustom) {
			assertTrue(el.length() >= 5 && el.length() <=100);
		}

	}

	@Test
	public void testJavaHash()  {
		String hash = alpha.hash();
		assertEquals(40, hash.length());
		String customLengthHash = alpha.hash(10);
		assertEquals(10, customLengthHash.length());
	}
	
	@Test
	public void testHashAsDefaultJavaList() {
		final List<String> hashStrings = alpha.hashAsJavaList();
		assertEquals(100, hashStrings.size());
		for (String el: hashStrings) {
			assertEquals(40, el.length());
		}
		final List<String> hashStringsCustom = alpha.hashAsJavaList(10, 60, 10);
		assertEquals(10, hashStringsCustom.size());
		for (String el: hashStringsCustom) {
			assertTrue(el.length()  >= 10 && el.length() <=60);
		}
	}

	@Test
	public void testHashAsCustomJavaList() {
		final List<String> hashStringsCustom = alpha.hashAsJavaList(10, 60, 10);
		assertEquals(10, hashStringsCustom.size());
		for (String el: hashStringsCustom) {
			assertTrue(el.length()  >= 10 && el.length() <=60);
		}
	}

	@Test
	public void testHashAsCustomJavaListWithSameMinMax() {
		final List<String> hashStringsCustom = alpha.hashAsJavaList(10, 10, 20);
		assertEquals(20, hashStringsCustom.size());
		for (String el: hashStringsCustom) {
			assertTrue(el.length() == 10);
		}
	}

	@Test
	public void testJavaGuid()  {
		String guid = alpha.guid();
		assertTrue(guid.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}"));
	}

	@Test
	public void testGuidAsJavaList() {
		final List<String> guidStrings = alpha.guidAsJavaList();
		assertEquals(100, guidStrings.size());
		final List<String> guidStringsCustom = alpha.guidAsJavaList(10, 20);
		assertEquals(20, guidStringsCustom.size());
	}
	
}
