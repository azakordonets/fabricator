package fabricator.j.tests;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class JavaInternetTestSuite extends JavaBaseTest {

    private static final Pattern URL_PATTERN = Pattern.compile("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$");
    private static final Pattern PARAMS_PATTERN = Pattern.compile("getEntity\\?");

    @Test
    public void testUrl() {
        final HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("hello", "test");
        stringStringHashMap.put("hello2", "test2");
        stringStringHashMap.put("hello3", "test 3");
        final String url = internet.urlBuilder().host("test.ru").paramsInJava(stringStringHashMap).toString();
        assertTrue(URL_PATTERN.matcher(url).matches());
        String params = url.split("/")[3];
        List<String> paramsList = Arrays.asList(PARAMS_PATTERN.matcher(params).replaceAll("").split("&"));
        assertTrue(paramsList.contains("hello=test"));
        assertTrue(paramsList.contains("hello2=test2"));
        assertTrue(paramsList.contains("hello3=test+3"));
    }
}
