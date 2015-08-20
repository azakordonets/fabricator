package fabricator

import java.awt.font.{FontRenderContext, TextLayout}
import java.awt.image.BufferedImage
import java.awt.{Color, Font, Graphics2D, Rectangle}

import fabricator.entities.CsvFileBuilder
import fabricator.enums.{FileType, MimeType}

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

  def csvBuilder : CsvFileBuilder = new CsvFileBuilder(alpha, calendar, contact, finance, internet,
                                                          location, mobile, user_agent, words)

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

}
