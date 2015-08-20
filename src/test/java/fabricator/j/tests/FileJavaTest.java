package fabricator.j.tests;

import fabricator.Fabricator;
import fabricator.FileGenerator;
import fabricator.enums.CsvValueCode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.testng.AssertJUnit.assertEquals;
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
        file.csvBuilder().build();
        fileObject = new File(csvFilePath);
        assertTrue(fileObject.exists());
    }

    @Test
    public void testCustomCsv() throws FileNotFoundException {
        final CsvValueCode[] codes = new CsvValueCode[]{CsvValueCode.FIRST_NAME, CsvValueCode.LAST_NAME};
        file.csvBuilder().withCodes(codes).build();
        fileObject = new File(csvFilePath);
        assert (fileObject.exists());
        final ArrayList<String> first_name = new ArrayList<>(Arrays.asList(util.getArrayFromJson("first_name")));
        final ArrayList<String> last_name = new ArrayList<>(Arrays.asList(util.getArrayFromJson("last_name")));
        Scanner scanner = new Scanner(fileObject);
        String title = scanner.nextLine();
        assertEquals("Title", "First Name,Last Name", title);
        while (scanner.hasNext()) {
            String value = scanner.nextLine();
            assertTrue(first_name.contains(value.split(",")[0]));
            assertTrue(last_name.contains(value.split(",")[1]));
        }
    }

    @Test
    public void testCustomCsvWithCustomDelimiter() throws FileNotFoundException {
        final CsvValueCode[] codes = new CsvValueCode[]{CsvValueCode.FIRST_NAME, CsvValueCode.LAST_NAME};
        file.csvBuilder().withCodes(codes).build();
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
