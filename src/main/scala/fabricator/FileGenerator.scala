package fabricator

import java.awt._
import java.awt.font.{FontRenderContext, TextLayout}
import java.awt.image.BufferedImage
import java.io.File

import com.github.tototoshi.csv._
import fabricator.enums.{CsvValueCode, FileType, MimeType}

import scala.util.Random


object FileGenerator {

  def apply(): FileGenerator = {
    new FileGenerator( Alphanumeric(), new Random(),
      Contact(), Words(), Calendar(), Finance(),
      Internet(), Location(), Mobile(), UserAgent(), UtilityService())
  }

  def apply(locale: String): FileGenerator = {
    new FileGenerator(Alphanumeric(), new Random(),
      Contact(locale), Words(locale), Calendar(locale), Finance(locale),
      Internet(locale), Location(locale), Mobile(), UserAgent(), UtilityService())
  }

}

class FileGenerator(private val alpha: Alphanumeric,
                    private val random: Random,
                    private val contact: Contact,
                    private val words: Words,
                    private val calendar: Calendar,
                    private val finance: Finance,
                    private val internet: Internet,
                    private val location: Location,
                    private val mobile: Mobile,
                   private val user_agent: UserAgent,
                     private val utility: UtilityService) {

  def image(width: Int, height: Int, path: String) = {
    if (width > 2560 || height > 2560) throw new IllegalArgumentException("Image cannot be more then 2560x2560")
    val label: String = "" + width + "x" + height
    val font: Font = new Font("Arial", Font.PLAIN, 32)
    val frc: FontRenderContext = new FontRenderContext(null, true, true)
    val layout: TextLayout = new TextLayout(label, font, frc)
    val rectangle: Rectangle = layout.getPixelBounds(null, 0, 0)
    val bufferedImage: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics2D: Graphics2D = bufferedImage.getGraphics.asInstanceOf[Graphics2D]
    //filing background with black color
    graphics2D.setColor(Color.black)
    graphics2D.fillRect(0, 0, width, height)
    //writing with white color width and height of the image
    graphics2D.setColor(Color.white)
    layout.draw(graphics2D, width / 2 - rectangle.getWidth.toInt / 2, height / 2)
    //done with drawing
    graphics2D.dispose()
    // write image to a file
    javax.imageio.ImageIO.write(bufferedImage, "png", new java.io.File(path))

  }


  def csv(): Unit = {
    val values = Array(CsvValueCode.FIRST_NAME,
                        CsvValueCode.LAST_NAME,
                        CsvValueCode.BIRTHDAY,
                        CsvValueCode.EMAIL,
                        CsvValueCode.PHONE,
                        CsvValueCode.ADDRESS,
                        CsvValueCode.BSN,
                        CsvValueCode.WEIGHT,
                        CsvValueCode.HEIGHT)
    // create temporary generatedFiles dir
    val dir: File = new File("generatedFiles")
    dir.mkdir
    // save it in this folder by default result.
    csvFromCodes(values, 100, "generatedFiles/result.csv", ',')
  }


  def csv(seq: Seq[Any], rows: Int, path: String): Unit = csv(seq, rows, path, ',')

  def csv(seq: Seq[Any], rows: Int, path: String, customDelimiter: Char): Unit = {
    val expectedFile = new File(path)
    if (!expectedFile.exists) expectedFile.createNewFile
    implicit object MyFormat extends DefaultCSVFormat {
      override val delimiter = customDelimiter
    }
    val writer = CSVWriter.open(expectedFile)
    for (i <- 0 to rows - 1) {
      writer.writeRow(seq)
    }
    writer.close()
  }

  def csvFromCodes(codes: Array[CsvValueCode], rows: Int, path: String): Unit = csvFromCodes(codes, rows, path, ',')

  def csvFromCodes(codes: Array[CsvValueCode], rows: Int, path: String, customDelimiter: Char): Unit = {
    writeCsvFileData(generateTitles(codes, customDelimiter), codes, rows, path, customDelimiter)
  }

  private def writeCsvFileData(titles: Array[String], values: Seq[CsvValueCode], rows: Int, path: String, delimeter: Char) = {
    val expectedFile = new File(path)
    if (!expectedFile.exists) expectedFile.createNewFile
    implicit object MyFormat extends DefaultCSVFormat {
      override val delimiter = delimeter
    }
    val writer = CSVWriter.open(expectedFile)
    writer.writeRow(titles)
    for (i <- 0 to rows - 1) {
      val generatedMap = values.map(x => generateValue(x))
      writer.writeRow(generatedMap)
    }
    writer.close()
  }

  def generateTitles(codes: Array[CsvValueCode], delimeter: Char): Array[String] = {
    val titles = new Array[String](codes.length)
    for (i <- codes.indices) {
      titles(i) = codes(i).getTitle
    }
    titles
  }

  def fileName: String = fileName(FileType.getRandom)

  def fileName(fileType: FileType): String = {
    val fileExt = fileExtension(fileType)
    val fileName = words.word
    fileName + "." + fileExt
  }

  def fileExtension: String = fileExtension(FileType.getRandom)

  def fileExtension(fileType: FileType): String = {
    fileType match {
      case FileType.AUDIO => utility.getValueFromArray("audio_file_extensions")
      case FileType.IMAGE => utility.getValueFromArray("image_file_extensions")
      case FileType.TEXT => utility.getValueFromArray("text_file_extensions")
      case FileType.DOCUMENT => utility.getValueFromArray("document_file_extensions")
      case FileType.VIDEO => utility.getValueFromArray("video_file_extensions")
    }
  }

  def mime_type: String = {
    mime_type(MimeType.getRandom)
  }

  def mime_type(mimeType: MimeType): String = {
    mimeType match {
      case MimeType.APPLICATION => utility.getValueFromArray("application_mime_types")
      case MimeType.AUDIO => utility.getValueFromArray("audio_mime_types")
      case MimeType.IMAGE => utility.getValueFromArray("image_mime_types")
      case MimeType.MESSAGE => utility.getValueFromArray("message_mime_types")
      case MimeType.MODEL => utility.getValueFromArray("model_mime_types")
      case MimeType.MULTIPART => utility.getValueFromArray("multipart_mime_types")
      case MimeType.TEXT => utility.getValueFromArray("text_mime_types")
      case MimeType.VIDEO => utility.getValueFromArray("video_mime_types")
    }
  }


  private def generateValue(code: CsvValueCode): String = {
    code match {
      case CsvValueCode.INTEGER => alpha.randomInt.toString
      case CsvValueCode.DOUBLE => alpha.randomDouble.toString
      case CsvValueCode.HASH => alpha.randomHash
      case CsvValueCode.GUID => alpha.randomGuid
      case CsvValueCode.TIME => calendar.time24h
      case CsvValueCode.DATE => calendar.randomDate.asString
      case CsvValueCode.NAME => contact.fullName(setPrefix = false, setSuffix = false)
      case CsvValueCode.FIRST_NAME => contact.firstName
      case CsvValueCode.LAST_NAME => contact.lastName
      case CsvValueCode.BIRTHDAY => calendar.randomDate.inYear(alpha.randomInt(1900, 2000)).asString
      case CsvValueCode.EMAIL => contact.eMail
      case CsvValueCode.PHONE => contact.phoneNumber
      case CsvValueCode.ADDRESS => contact.address
      case CsvValueCode.POSTCODE => contact.postcode
      case CsvValueCode.BSN => contact.bsn
      case CsvValueCode.HEIGHT => contact.height(cm = false)
      case CsvValueCode.WEIGHT => contact.weight(metric = true)
      case CsvValueCode.OCCUPATION => contact.occupation
      case CsvValueCode.VISA => finance.visacreditCard
      case CsvValueCode.MASTER => finance.mastercreditCard
      case CsvValueCode.IBAN => finance.iban
      case CsvValueCode.BIC => finance.bic
      case CsvValueCode.SSN => finance.ssn
      case CsvValueCode.URL => internet.urlBuilder.toString()
      case CsvValueCode.IP => internet.ip
      case CsvValueCode.MACADDRESS => internet.macAddress
      case CsvValueCode.UUID => internet.UUID
      case CsvValueCode.COLOR => internet.color
      case CsvValueCode.TWITTER => internet.twitter
      case CsvValueCode.HASHTAG => internet.hashtag
      case CsvValueCode.FACEBOOK => internet.facebookId
      case CsvValueCode.GOOGLE_ANALYTICS => internet.googleAnalyticsTrackCode
      case CsvValueCode.ALTITUDE => location.altitude
      case CsvValueCode.DEPTH => location.depth
      case CsvValueCode.LATITUDE => location.latitude
      case CsvValueCode.LONGITUDE => location.longitude
      case CsvValueCode.COORDINATES => location.coordinates
      case CsvValueCode.GEOHASH => location.geohash
      case CsvValueCode.APPLE_TOKEN => mobile.applePushToken
      case CsvValueCode.ANDROID => mobile.androidGsmId
      case CsvValueCode.WINDOWS7TOKEN => mobile.wp7_anid
      case CsvValueCode.WINDOWS8TOKEN => mobile.wp8_anid2
      case CsvValueCode.USER_AGENT => user_agent.chrome
      case CsvValueCode.WORD => words.word
      case CsvValueCode.SENTENCE => words.sentence(10)
    }
  }

}
