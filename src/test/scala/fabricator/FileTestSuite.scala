

package fabricator

import java.io.File

import com.github.tototoshi.csv.CSVReader
import fabricator.enums.FileType
import org.testng.annotations.{AfterTest, DataProvider, Test}

class FileTestSuite extends BaseTestSuite {

  protected val csvFilePath: String = "generatedFiles/result.csv"
  protected var fileObject: File = null
  private val audioExtensionList = util.getArrayFromJson("audio_file_extensions")
  private val imageExtensionList = util.getArrayFromJson("image_file_extensions")
  private val textExtensionList = util.getArrayFromJson("text_file_extensions")
  private val docExtensionList = util.getArrayFromJson("document_file_extensions")
  private val videoExtensionList = util.getArrayFromJson("video_file_extensions")


  @Test
  def testCustomConstructor()  {
    val customFile = fabricator.FileGenerator("us")
    assert(customFile != null)
  }

  @Test
  def testImage() = {
    val path: String = "generatedFiles/drawing.png"
    val result = file.image(200, 300, path)
    val fileOnADrive: File = new File(path)
    fileObject = fileOnADrive
    assert(fileOnADrive.exists())
    logger.info("Checking image file")
  }

  @Test
  def testCsv() = {
    val result = file.csv()
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    logger.info("Checking csv file")
  }

  @Test
  def testCustomCsv() = {
    val codes = Array("first_name", "last_name", "birthday", "email", "phone", "address", "bsn", "weight", "height")
    val result = file.csvFromCodes(codes, 10, csvFilePath)
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    val line = CSVReader.open(fileOnADrive).readNext()
    val firstNameList: Array[String] = util.getArrayFromJson("first_name")
    assert(firstNameList.contains(line.get.head))
  }

  @Test
  def testCsvWithCustomDelimiter() = {
    // creating file
    val codes = Array("occupation", "visa", "master", "iban", "bic", "url", "ip", "macaddress", "uuid", "color", "twitter", "hashtag", "facebook",
      "google_analytics", "altitude", "depth", "latitude", "longitude", "coordinates", "geohash", "apple_token", "android", "postcode",
      "windows7Token", "windows8Token", "word", "sentence", "integer", "integer", "double", "hash", "guid", "time", "date", "name")
    val numberOfRows = 10
    val result = file.csvFromCodes(codes, 10, csvFilePath)

    // check that file exists
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    // read file and confirm that correct data is present
    val reader = CSVReader.open(fileOnADrive)
    val lines = reader.all()
    val numberOfRowsInFile = lines.length
    assertResult(numberOfRows)(numberOfRowsInFile)
    // asser that inserted data is correct
    val line = reader.readNext()
    val occupationList: Array[String] = util.getArrayFromJson("occupation")
    assert(occupationList.contains(lines.head.head))
    assertResult(codes.length)(lines.head.size)
  }

  @Test
  def testCsvWithCustomSequence() = {
    val values = Seq(alpha.getInteger, alpha.getDouble, calendar.ampm, null)
    val numberOfRows = 10
    val result = file.csv(values, numberOfRows, csvFilePath)
    // check that file exists
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    // read file and confirm that correct data is present
    val reader = CSVReader.open(fileOnADrive)
    val lines = reader.all()
    val numberOfRowsInFile = lines.length
    assertResult(numberOfRows)(numberOfRowsInFile)
    // asser that inserted data is correct
    val line = reader.readNext()
    assert(lines.head.head.toInt <= 1000)
    assertResult(values.length)(lines.head.size)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testCsvFileWithException() = {
    val codes = Array("wrongInput")
    val result = file.csvFromCodes(codes, 1, csvFilePath)
  }

  @DataProvider
  def sizeDP(): Array[Array[Any]] = {
    Array(Array(100, 3000, "path"),
      Array(10000, 300, "path"),
      Array(10000, 3000, "path")
    )
  }


  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]), dataProvider = "sizeDP")
  def testImageFileWithExceptionForSize(width: Int, height: Int, path: String) = {
    val result = file.image(width, height, path)
  }

  @DataProvider
  def fileTypeDP(): Array[Array[Any]] = {
    Array(Array(FileType.AUDIO, util.getArrayFromJson("audio_file_extensions")),
      Array(FileType.IMAGE, util.getArrayFromJson("image_file_extensions")),
      Array(FileType.TEXT, util.getArrayFromJson("text_file_extensions")),
      Array(FileType.DOCUMENT, util.getArrayFromJson("document_file_extensions")),
      Array(FileType.VIDEO, util.getArrayFromJson("video_file_extensions"))
    )
  }

  @Test(dataProvider = "fileTypeDP")
  def testFileExtension(fileType: FileType, expectedList:Array[String]) = {
    val extension = file.fileExtension(fileType)
    assert(expectedList.contains(extension))
  }

  @Test(dataProvider = "fileTypeDP")
  def testFileName(fileType: FileType, expectedTypeArray: Array[String]) = {
    val fileName = file.fileName(fileType)
    val name = fileName.split("\\.")(0)
    val fileExtension = fileName.split("\\.")(1)
    assert(expectedTypeArray.contains(fileExtension))
    assert(fileName.length > 0)
  }

  @Test
  def testRandomFileExtension() = {
    val fileExtension = file.fileExtension
    assert(audioExtensionList.contains(fileExtension) ||
      imageExtensionList.contains(fileExtension) ||
      textExtensionList.contains(fileExtension) ||
      docExtensionList.contains(fileExtension) ||
      videoExtensionList.contains(fileExtension)
    )
  }

  @Test
  def testFileNameWithRandomExtension() = {
    val fileName = file.fileName
    val name = fileName.split("\\.")(0)
    val fileExtension = fileName.split("\\.")(1)
    assert(name.length > 0)
    assert(audioExtensionList.contains(fileExtension) ||
      imageExtensionList.contains(fileExtension) ||
      textExtensionList.contains(fileExtension) ||
      docExtensionList.contains(fileExtension) ||
      videoExtensionList.contains(fileExtension)
    )

  }

  @AfterTest
  def tearDown() = {
    logger.info("Deleting file")
    if (fileObject != null) fileObject.delete()
  }


}
