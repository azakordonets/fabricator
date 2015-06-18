package fabricator.j.tests;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JavaInternetTestSuite extends JavaBaseTest {

    @Test
    public void testUrl() {
        final HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("hello", "test");
        stringStringHashMap.put("hello2", "test2");
        stringStringHashMap.put("hello3", "test 3");
        final String url = internet.urlBuilder().host("test.ru").paramsinJava(stringStringHashMap).toString();
        assertTrue(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"));
        String params = url.split("/")[3];
        List<String> stringList = Arrays.asList(params.split("&"));
        assertTrue(stringList.contains("getEntity?hello=test"));
        assertTrue(stringList.contains("hello2=test2"));
        assertTrue(stringList.contains("hello3=test+3"));
    }
}
