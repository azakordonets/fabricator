package fabricator.j.tests;

import fabricator.Fabricator;
import fabricator.FileGenerator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static org.testng.AssertJUnit.assertTrue;

public class FileJavaTest extends JavaBaseTest {

    private File fileObject;
    private final String csvFilePath = "generatedFiles/result.csv";

    @Test
    public void testCustomConstructor() {
        final FileGenerator us = Fabricator.file("us");
        assertTrue(us != null);
    }

    @Test
    public void testImage() {
        file.image(200, 300, csvFilePath);
        fileObject = new File(csvFilePath);
        assertTrue(fileObject.exists());
    }

    @Test
    public void testCsv() {
        file.csv();
        fileObject = new File(csvFilePath);
        assertTrue(fileObject.exists());
    }

    @Test
    public void testCustomCsv() throws FileNotFoundException {
        String[] codes = new String[]{"first_name"};
        file.csvFromCodes(codes, 10, csvFilePath);
        fileObject = new File(csvFilePath);
        assert (fileObject.exists());
        final ArrayList<String> first_name = new ArrayList<>(Arrays.asList(util.getArrayFromJson("first_name")));
        Scanner scanner = new Scanner(fileObject);
        while (scanner.hasNext()) {
            final String next = scanner.next();
            if (!next.equals("First") && !next.equals("Name")) {
                assertTrue(String.format("Next value = %s is not present in first_name list", next), first_name.contains(next));
            }
        }
    }

    @Test
    public void testCustomCsvWithCustomDelimeter() throws FileNotFoundException {
        String[] codes = new String[]{"first_name", "last_name"};
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("First Name", "first_name");
        hash.put("Last Name", "last_name");
        file.csvFromCodes(codes, 10, csvFilePath);
        fileObject = new File(csvFilePath);
        assertTrue(fileObject.exists());
        final ArrayList<String> first_name = new ArrayList<>(Arrays.asList(util.getArrayFromJson("first_name")));
        final ArrayList<String> last_name = new ArrayList<>(Arrays.asList(util.getArrayFromJson("last_name")));
        Scanner scanner = new Scanner(fileObject);
        scanner.useDelimiter(",");
        scanner.nextLine();
        while (scanner.hasNext()) {
            String value = scanner.nextLine();
            assertTrue(first_name.contains(value.split(",")[0]));
            assertTrue(last_name.contains(value.split(",")[1]));
        }
    }

    @AfterTest
    public void tearDown() {
        if (fileObject != null) assert(fileObject.delete());
    }

}
