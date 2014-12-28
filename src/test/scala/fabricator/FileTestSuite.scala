package fabricator

import java.io.File

import org.testng.annotations.{AfterTest, Test}

class FileTestSuite extends BaseTestSuite {

  protected var fileObject: File = null

  @Test
  def testImage() = {
    val path: String = "test-output/drawing.png"
    val result = file.image(200,300, path)
    val fileOnADrive: File = new File(path)
    fileObject = fileOnADrive
    assert(fileOnADrive.exists())
    logger.info("Checking image file")
  }

  @Test
  def testCsv() = {
    val result = file.csv()
    val fileOnADrive: File = new File("test-output/result.csv")
    fileObject = fileOnADrive
    assert(fileObject.exists())
    logger.info("Checking csv file")
  }

  @AfterTest
  def tearDown() = {
   logger.info("Deleting file")
    if (fileObject != null) fileObject.delete()
  }




}
