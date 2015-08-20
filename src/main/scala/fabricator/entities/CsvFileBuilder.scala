package fabricator.entities

import java.io.File

import com.github.tototoshi.csv.{CSVWriter, DefaultCSVFormat}
import fabricator._
import fabricator.enums.CsvValueCode
import fabricator.enums.CsvValueCode._

class CsvFileBuilder(alpha: Alphanumeric,
                      calendar: Calendar,
                      contact: Contact,
                      finance: Finance,
                      internet: Internet,
                      location: Location,
                      mobile: Mobile,
                      userAgent: UserAgent,
                      words: Words) {

  private var filePath: String = "generatedFiles/result.csv"

  private var titles: Array[String] = null

  private var customTitles: Array[String] = null

  private var codes: Array[CsvValueCode] = Array(FIRST_NAME,
                                               LAST_NAME,
                                               BIRTHDAY,
                                               EMAIL,
                                               PHONE,
                                               ADDRESS,
                                               BSN,
                                               WEIGHT,
                                               HEIGHT)

  private var customValuesArray: Array[Any] = null

  private var numberOfRows: Int = 10

  private var customDelimiter: Char = ','

  private var appendCsvFile: Boolean = false

  def saveTo(filePath: String): this.type = {
    this.filePath = filePath
    this
  }

  def withCodes(codes: Array[CsvValueCode]): this.type = {
    this.codes = codes
    this
  }

  def withCustomCodes(values: Array[Any]): this.type = {
    this.customValuesArray = values
    this
  }

  def withTitles(titles: Array[String]): this.type = {
    this.titles = titles
    this
  }

  def withDelimiter(delimiter: Char): this.type = {
    this.customDelimiter = delimiter
    this
  }

  def withNumberOfRows(rows: Int): this.type = {
    this.numberOfRows = rows
    this
  }

  def addToExistingFile(): this.type = {
    this.appendCsvFile = true
    this
  }

  def build() = {
    val expectedFile: File = createFile
    implicit object MyFormat extends DefaultCSVFormat {
      override val delimiter = customDelimiter
    }
    val writer = CSVWriter.open(expectedFile)
    writeTitlesIntoFile(writer)
    for (i <- 0 to numberOfRows - 1) {
      if (null == customValuesArray) {
        val generatedMap = codes.map(code => generateValue(code))
        writer.writeRow(generatedMap)
      } else {
        val generatedMap = customValuesArray.map(value => generateValue(value))
        writer.writeRow(generatedMap)
      }
    }
    writer.close()
  }


  def writeTitlesIntoFile(writer: CSVWriter): Unit = {
    if (null != customValuesArray && null == titles) {
      generateCustomTitles()
      writer.writeRow(customTitles)
    } else if (null == titles) {
      titles = for (i <- codes) yield i.getTitle
      writer.writeRow(titles)
    } else if (titles != null){
      writer.writeRow(titles)
    }
  }

  def createFile: File = {
    val expectedFile = new File(this.filePath)
    val expectedDirectoryPath = new File(this.filePath).getParent
    val expectedDirectory = new File(expectedDirectoryPath)
    if (!expectedDirectory.exists) expectedDirectory.mkdirs()
    if (!expectedFile.exists) expectedFile.createNewFile
    expectedFile
  }

  private def generateValue(code: Any): String = {
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
      case CsvValueCode.USER_AGENT_CHROME => userAgent.chrome
      case CsvValueCode.USER_AGENT_FIREFOX => userAgent.firefox
      case CsvValueCode.USER_AGENT_IE => userAgent.internet_explorer
      case CsvValueCode.USER_AGENT_OPERA => userAgent.opera
      case CsvValueCode.WORD => words.word
      case CsvValueCode.SENTENCE => words.sentence(10)
      case null => ""
      case _ => code.toString
    }
  }

  private def generateCustomTitles() = {
    customTitles = for (value <- customValuesArray) yield value match {
      case code: CsvValueCode => code.getTitle
      case number: Integer => "Integer number"
      case number: Double => "Double number"
      case number: Float => "Float number"
      case number: String => "String"
      case number: Boolean => "Boolean"
      case number: Long => "Long number"
      case null => ""
      case _ => value.toString
    }
  }
}
