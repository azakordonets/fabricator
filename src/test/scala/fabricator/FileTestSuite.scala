

package fabricator

import java.io.File

import com.github.tototoshi.csv.CSVReader
import fabricator.enums.{CsvValueCode, FileType, MimeType}
import org.testng.annotations.{AfterTest, DataProvider, Test}

class FileTestSuite extends BaseTestSuite {

  protected val csvFilePath: String = "generatedFiles/result.csv"
  protected var fileObject: File = null
  // File extensions
  private lazy val audioExtensionList = util.getArrayFromJson("audio_file_extensions")
  private lazy val imageExtensionList = util.getArrayFromJson("image_file_extensions")
  private lazy val textExtensionList = util.getArrayFromJson("text_file_extensions")
  private lazy val docExtensionList = util.getArrayFromJson("document_file_extensions")
  private lazy val videoExtensionList = util.getArrayFromJson("video_file_extensions")
  //Mime types
  private lazy val applicationMimeTypes = util.getArrayFromJson("application_mime_types")
  private lazy val audioMimeTypes =  util.getArrayFromJson("audio_mime_types")
  private lazy val imageMimeTypes =  util.getArrayFromJson("image_mime_types")
  private lazy val messageMimeTypes =  util.getArrayFromJson("message_mime_types")
  private lazy val modelMimeTypes =  util.getArrayFromJson("model_mime_types")
  private lazy val multipartMimeTypes =  util.getArrayFromJson("multipart_mime_types")
  private lazy val textMimeTypes =  util.getArrayFromJson("text_mime_types")
  private lazy val videoMimeTypes =  util.getArrayFromJson("video_mime_types")




  @Test
  def testCustomConstructor()  {
    val customFile = fabricator.FileGenerator("us")
    assert(Option(customFile).isDefined)
  }

  @Test
  def testImage() = {
    file.image(200, 300, csvFilePath)
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileOnADrive.exists())
    logger.info("Checking image file")
  }

  @Test
  def testCsv() = {
    file.csvBuilder.build()
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    logger.info("Checking csv file")
  }

  @Test
  def testCustomCsv() = {
    val codes = Array(CsvValueCode.FIRST_NAME,
                      CsvValueCode.LAST_NAME,
                      CsvValueCode.BIRTHDAY,
                      CsvValueCode.EMAIL,
                      CsvValueCode.PHONE,
                      CsvValueCode.ADDRESS,
                      CsvValueCode.BSN,
                      CsvValueCode.WEIGHT,
                      CsvValueCode.HEIGHT)
    file.csvBuilder
      .withCodes(codes)
      .withNumberOfRows(10)
      .saveTo(csvFilePath)
      .build()
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    val line = CSVReader.open(fileOnADrive).all()
    val firstNameList: Array[String] = util.getArrayFromJson("first_name")
    assert(firstNameList.contains(line(1).head))
  }

  @Test
  def testCsvWithCustomDelimiter() = {
    // creating file
    val codes = Array(CsvValueCode.OCCUPATION,
                      CsvValueCode.VISA,
                      CsvValueCode.MASTER,
                      CsvValueCode.IBAN,
                      CsvValueCode.BIC,
                      CsvValueCode.URL,
                      CsvValueCode.IP,
                      CsvValueCode.MACADDRESS,
                      CsvValueCode.UUID,
                      CsvValueCode.COLOR,
                      CsvValueCode.TWITTER,
                      CsvValueCode.HASHTAG,
                      CsvValueCode.FACEBOOK,
                      CsvValueCode.GOOGLE_ANALYTICS,
                      CsvValueCode.ALTITUDE,
                      CsvValueCode.DEPTH,
                      CsvValueCode.LATITUDE,
                      CsvValueCode.LONGITUDE,
                      CsvValueCode.COORDINATES,
                      CsvValueCode.GEOHASH,
                      CsvValueCode.APPLE_TOKEN,
                      CsvValueCode.ANDROID,
                      CsvValueCode.POSTCODE,
                      CsvValueCode.WINDOWS7TOKEN,
                      CsvValueCode.WINDOWS8TOKEN,
                      CsvValueCode.WORD,
                      CsvValueCode.SENTENCE,
                      CsvValueCode.INTEGER,
                      CsvValueCode.INTEGER,
                      CsvValueCode.DOUBLE,
                      CsvValueCode.HASH,
                      CsvValueCode.GUID,
                      CsvValueCode.TIME,
                      CsvValueCode.DATE,
                      CsvValueCode.NAME)
    val numberOfRows = 10
    file.csvBuilder.withCodes(codes)
      .withNumberOfRows(10)
      .withDelimiter('|')
      .saveTo(csvFilePath)
      .build()

    // check that file exists
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    // read file and confirm that correct data is present
    val reader = CSVReader.open(fileOnADrive)
    val lines = reader.all()
    val numberOfRowsInFile = lines.length
    assertResult(numberOfRows + 1)(numberOfRowsInFile)
    // assert that inserted data is correct
    val occupationList: Array[String] = util.getArrayFromJson("occupation")
    assert(occupationList.contains(lines(1).head.split("\\|").head))
    assertResult(codes.length)(lines.head.head.split("\\|").size)
  }

  @Test
  def testCsvWithCustomSequence() = {
    val values = Array(alpha.randomInt, alpha.randomDouble, calendar.ampm, null)
    val numberOfRows = 10
    file.csvBuilder
                  .withNumberOfRows(numberOfRows)
                  .withCustomCodes(values)
                  .saveTo(csvFilePath)
                  .build()
    // check that file exists
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    // read file and confirm that correct data is present
    val reader = CSVReader.open(fileOnADrive)
    val lines = reader.all()
    val numberOfRowsInFile = lines.length
    assertResult(numberOfRows + 1)(numberOfRowsInFile)
    // assert that inserted data is correct
    val expectedTitles = Array("Integer number", "Double number", "String", "")
    val titles = lines.head
    for (title <- expectedTitles) assert(titles.contains(title))
  }

  @Test
  def testCsvWithTitle() = {
    val titles = Array("first_column", "second_column", "third_column", "fourth_column")
    val values = Array(alpha.randomInt, CsvValueCode.ANDROID, "10", null)
    val numberOfRows = 10
    file.csvBuilder
                    .withNumberOfRows(numberOfRows)
                    .withCustomCodes(values)
                    .withTitles(titles)
                    .build()
    // check that file exists
    val fileOnADrive: File = new File(csvFilePath)
    fileObject = fileOnADrive
    assert(fileObject.exists())
    // read file and confirm that correct data is present
    val reader = CSVReader.open(fileOnADrive)
    val lines = reader.all()
    val actualTitles = lines.head
    val numberOfRowsInFile = lines.length
    for (title <- actualTitles) assert(titles.contains(title))
    assertResult(numberOfRows + 1)(numberOfRowsInFile)

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
    file.image(width, height, path)
  }

  @DataProvider
  def fileTypeDP(): Array[Array[Any]] = {
    Array(Array(FileType.AUDIO, audioExtensionList),
      Array(FileType.IMAGE, imageExtensionList),
      Array(FileType.TEXT, textExtensionList),
      Array(FileType.DOCUMENT, docExtensionList),
      Array(FileType.VIDEO, videoExtensionList)
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
    assert(name.length > 0)
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

  @DataProvider
  def mimeTypeDP(): Array[Array[Any]] = {
    Array(Array(MimeType.APPLICATION, applicationMimeTypes),
      Array(MimeType.AUDIO, audioMimeTypes),
      Array(MimeType.IMAGE, imageMimeTypes),
      Array(MimeType.MESSAGE, messageMimeTypes),
      Array(MimeType.MODEL, modelMimeTypes),
      Array(MimeType.MULTIPART, multipartMimeTypes),
      Array(MimeType.TEXT, textMimeTypes),
      Array(MimeType.VIDEO, videoMimeTypes)
    )
  }

  @Test(dataProvider = "mimeTypeDP")
  def testMimeExtension(mimeType: MimeType, expectedList:Array[String]) = {
    val extension = file.mime_type(mimeType)
    assert(expectedList.contains(extension))
  }

  @Test
  def testRandomMimeExtension() = {
    val mimeType = file.mime_type
    assert(applicationMimeTypes.contains(mimeType) ||
      audioMimeTypes.contains(mimeType) ||
      imageMimeTypes.contains(mimeType) ||
      messageMimeTypes.contains(mimeType) ||
      modelMimeTypes.contains(mimeType) ||
      multipartMimeTypes.contains(mimeType) ||
      textMimeTypes.contains(mimeType) ||
      videoMimeTypes.contains(mimeType)
    )
  }

  @AfterTest
  def tearDown() = {
    logger.info("Deleting file")
    if (fileObject != null) fileObject.delete()
  }


}
