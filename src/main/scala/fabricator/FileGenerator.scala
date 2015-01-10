package fabricator

import java.awt._
import java.awt.font.{FontRenderContext, TextLayout}
import java.awt.image.BufferedImage
import java.io.File

import com.github.tototoshi.csv._

import scala.util.Random


object FileGenerator {

  def apply(): FileGenerator = {
    new FileGenerator( Alphanumeric(), new Random(),
      Contact(), Words(), Calendar(), Finance(),
      Internet(), Location(), Mobile())
  }

  def apply(locale: String): FileGenerator = {
    new FileGenerator(Alphanumeric(), new Random(),
      Contact(locale), Words(locale), Calendar(locale), Finance(locale),
      Internet(locale), Location(locale), Mobile())
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
                    private val mobile: Mobile) {

  def image(width: Int, height: Int, path: String) = {
    if (width > 2560 || height > 2560) throw new IllegalArgumentException("Image cannot be more then 2560x2560")
    val label: String = "" + width + "x" + height
    val font: Font = new Font("Arial", Font.PLAIN, 32)
    val frc: FontRenderContext = new FontRenderContext(null, true, true)
    val layout: TextLayout = new TextLayout(label, font, frc)
    val rectangle: Rectangle = layout.getPixelBounds(null, 0, 0)
    val bufferedImage: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics2D: Graphics2D = bufferedImage.getGraphics().asInstanceOf[Graphics2D]
    //filing background with black color
    graphics2D.setColor(Color.black)
    graphics2D.fillRect(0, 0, width, height)
    //writing with white color width and height of the image
    graphics2D.setColor(Color.white)
    layout.draw(graphics2D, width / 2 - rectangle.getWidth().toInt / 2, height / 2)
    //done with drawing
    graphics2D.dispose()
    // write image to a file
    javax.imageio.ImageIO.write(bufferedImage, "png", new java.io.File(path))

  }


  def csv(): Unit = {
    val values = Array("first_name", "last_name", "birthday", "email", "phone", "address", "bsn", "weight", "height")
    // create temporary generatedFiles dir
    val dir: File = new File("generatedFiles")
    dir.mkdir()
    // save it in this folder by default result.
    csvFromCodes(values, 100, "generatedFiles/result.csv", ',')
  }


  def csv(seq: Seq[Any], rows: Int, path: String): Unit = {
    csv(seq, rows, path, ',')
  }

  def csv(seq: Seq[Any], rows: Int, path: String, customDelimiter: Char): Unit = {
    val expectedFile = new File(path)
    if (!expectedFile.exists()) expectedFile.createNewFile()
    implicit object MyFormat extends DefaultCSVFormat {
      override val delimiter = customDelimiter
    }
    val writer = CSVWriter.open(expectedFile)
    for (i <- 0 to rows - 1) {
      writer.writeRow(seq)
    }
    writer.close()
  }

  def csvFromCodes(codes: Array[String], rows: Int, path: String): Unit = {
    csvFromCodes(codes, rows, path, ',')
  }

  def csvFromCodes(codes: Array[String], rows: Int, path: String, customDelimiter: Char): Unit = {
    val expectedFile = new File(path)
    implicit object MyFormat extends DefaultCSVFormat {
      override val delimiter = customDelimiter
    }
    val writer = CSVWriter.open(expectedFile)
    for (i <- 0 to rows - 1) {
      val generatedMap = codes.map(x => generateValue(x))
      writer.writeRow(generatedMap)
    }
    writer.close()
  }


  private def generateValue(code: String): String = {
    code match {
      case "integer" => alpha.getInteger.toString
      case "double" => alpha.getDouble.toString
      case "hash" => alpha.hash()
      case "guid" => alpha.guid()
      case "time" => calendar.time(true)
      case "date" => calendar.date()
      case "name" => contact.fullName(false)
      case "first_name" => contact.firstName
      case "last_name" => contact.lastName
      case "birthday" => contact.birthday(alpha.getInteger(21, 80))
      case "email" => contact.eMail
      case "phone" => contact.phoneNumber
      case "address" => contact.streetName + " " + contact.houseNumber + ", " + contact.apartmentNumber
      case "postcode" => contact.postcode
      case "bsn" => contact.bsn
      case "height" => contact.height(false)
      case "weight" => contact.weight()
      case "occupation" => contact.occupation()
      case "visa" => finance.visacreditCard()
      case "master" => finance.mastercreditCard()
      case "iban" => finance.iban()
      case "bic" => finance.bic()
      case "url" => internet.url()
      case "ip" => internet.ip()
      case "macaddress" => internet.macAddress()
      case "uuid" => internet.UUID()
      case "color" => internet.color()
      case "twitter" => internet.twitter()
      case "hashtag" => internet.hashtag()
      case "facebook" => internet.facebookId()
      case "google_analytics" => internet.googleAnalyticsTrackCode()
      case "altitude" => location.altitude()
      case "depth" => location.depth()
      case "latitude" => location.latitude()
      case "longitude" => location.longitude()
      case "coordinates" => location.coordinates()
      case "geohash" => location.geohash()
      case "apple_token" => mobile.applePushToken()
      case "android" => mobile.androidGsmId()
      case "windows7Token" => mobile.wp7_anid()
      case "windows8Token" => mobile.wp8_anid2()
      case "word" => words.word()
      case "sentence" => words.sentence(10)
      case _ => throw new IllegalArgumentException(code + " is an incorrect code value")
    }
  }


}
