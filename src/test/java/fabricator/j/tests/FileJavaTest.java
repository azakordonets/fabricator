package fabricator.j.tests;

import fabricator.Fabricator;
import fabricator.FileGenerator;
import fabricator.enums.CsvValueCode;
import org.testng.annotations.AfterMethod;
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
  private static final String csvFilePath = "generatedFiles/result.csv";

  @Test
  public void testJavaCustomConstructor() {
    final FileGenerator us = Fabricator.file("us");
    assertTrue(us != null);
  }

  @Test
  public void testJavaImage() {
    file.image(200, 300, csvFilePath);
    fileObject = new File(csvFilePath);
    assertTrue(fileObject.exists());
  }

  @Test
  public void testJavaCsv() {
    file.csvBuilder().build();
    fileObject = new File(csvFilePath);
    assertTrue(fileObject.exists());
  }

  @Test
  public void testJavaCustomCsv() throws FileNotFoundException {
    final CsvValueCode[] codes = new CsvValueCode[] {CsvValueCode.FIRST_NAME, CsvValueCode.LAST_NAME};
    file.csvBuilder().withCodes(codes).build();
    fileObject = new File(csvFilePath);
    assert (fileObject.exists());
    final ArrayList<String> expectedFirstNamesList = new ArrayList<>(Arrays.asList(util.getArrayFromJson("first_name")));
    final ArrayList<String> expectedLastNamesList = new ArrayList<>(Arrays.asList(util.getArrayFromJson("last_name")));
    Scanner scanner = new Scanner(fileObject);
    String title = scanner.nextLine();
    assertEquals(String.format("Title is %s", title), "First Name,Last Name", title);
    verifyFirstLastName(expectedFirstNamesList, expectedLastNamesList, scanner);
  }

  private static void verifyFirstLastName(ArrayList<String> expectedFirstNamesList, ArrayList<String> expectedLastNamesList, Scanner scanner) {
    while (scanner.hasNext()) {
      String value = scanner.nextLine();
      String actualFirstName = value.split(",")[0];
      String actualLastName = value.split(",")[1];
      assertTrue(String.format("Value %s is not in first name list", actualFirstName),
          expectedFirstNamesList.contains(actualFirstName));
      assertTrue(String.format("Value %s is not in first name list", actualLastName),
          expectedLastNamesList.contains(actualLastName));
    }
  }

  @Test
  public void testJavaCustomCsvWithCustomDelimiter() throws FileNotFoundException {
    final CsvValueCode[] codes = new CsvValueCode[] {CsvValueCode.FIRST_NAME, CsvValueCode.LAST_NAME};
    char delimiter = ',';
    file.csvBuilder().withCodes(codes).withDelimiter(delimiter).build();
    fileObject = new File(csvFilePath);
    assertTrue(fileObject.exists());
    final ArrayList<String> expectedFirstNamesList = new ArrayList<>(Arrays.asList(util.getArrayFromJson("first_name")));
    final ArrayList<String> expectedLastNamesList = new ArrayList<>(Arrays.asList(util.getArrayFromJson("last_name")));
    Scanner scanner = new Scanner(fileObject);
    scanner.useDelimiter(String.valueOf(delimiter));
    scanner.nextLine();
    verifyFirstLastName(expectedFirstNamesList, expectedLastNamesList, scanner);
  }

  @AfterMethod
  public void tearDown() {
    if (fileObject.exists()) {
      assertTrue("Couldn't delete file object", fileObject.delete());
    }
  }

}
