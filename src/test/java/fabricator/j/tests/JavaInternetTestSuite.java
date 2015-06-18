package fabricator.j.tests;

import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class JavaInternetTestSuite extends JavaBaseTest {

    @Test
    public void testUrl() {
        final HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("hello", "test");
        stringStringHashMap.put("hello2", "test2");
        stringStringHashMap.put("hello3", "test 3");
        final String url = internet.urlBuilder().host("test.ru").paramsinJava(stringStringHashMap).toString();
        assertEquals("http://test.ru/getEntity?hello=test&hello2=test2&hello3=test+3", url);
    }
}
