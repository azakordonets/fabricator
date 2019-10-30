package fabricator

import com.github.nscala_time.time.Imports._
import fabricator.enums.DateFormat
import org.joda.time.format.DateTimeFormatter

import scala.util.Random

object Contact {

  def apply(): Contact = {
    new Contact(UtilityService(), Alphanumeric(), new Random())
  }

  def apply(locale: String): Contact = {
    new Contact(UtilityService(locale), Alphanumeric(), new Random())
  }

}

class Contact(private val utility: UtilityService,
              private val alpha: Alphanumeric,
              private val random: Random) {

  def fullName(setPrefix: Boolean, setSuffix: Boolean): String = {
    (setPrefix, setSuffix) match {
      case (`setPrefix` , `setSuffix`) if setPrefix && !setSuffix =>  String.format("%s %s %s", prefix, firstName, lastName)
      case (`setPrefix` , `setSuffix`) if setPrefix && setSuffix => String.format("%s %s %s %s", prefix, firstName, lastName, suffix)
      case (`setPrefix` , `setSuffix`) if setPrefix && setSuffix => String.format("%s %s %s", firstName, lastName, suffix)
      case _ => firstName + " " + lastName
    }
  }

  def birthday(age: Int): String = DateTimeFormat.forPattern(DateFormat.dd_MM_yyyy.getFormat).print(DateTime.now - age.years)

  def birthday(age: Int, format: DateFormat): String = DateTimeFormat.forPattern(format.getFormat).print(DateTime.now - age.years)

  def eMail: String = {
    (firstName.toLowerCase + "_" + lastName.toLowerCase + alpha.numerify("###")).replace(" ", "") + "@" + utility
      .getValueFromArray("free_email")
      .toLowerCase
  }

  def prefix: String = utility.getValueFromArray("prefix")
  
  def suffix: String = utility.getValueFromArray("suffix")
  
  def firstName: String = utility.getValueFromArray("first_name")
  
  def lastName: String = utility.getValueFromArray("last_name")

  def phoneNumber: String = alpha.numerify(utility.getValueFromArray("phone_formats"))

  def address: String = streetName + " " + houseNumber + ", " + apartmentNumber

  def streetName: String = utility.getValueFromArray("street_suffix")

  def houseNumber: String = alpha.numerify(utility.getValueFromArray("house_number"))

  def apartmentNumber: String = alpha.numerify(utility.getValueFromArray("app_number"))

  def postcode: String = alpha.botify(utility.getValueFromArray("postcode")).toUpperCase
  
  def country: String = utility.getValueFromArray("country")
  
  def city: String = utility.getValueFromArray("city_prefix") + utility.getValueFromArray("city_suffix")

  def state: String = utility.getValueFromArray("state")

  def stateShortCode: String = utility.getValueFromArray("state_abbr")

  def company: String = utility.getValueFromArray("company_suffix")

  /**
   * BSN number is generated in accordance with http://nl.wikipedia.org/wiki/Burgerservicenummer article
   * @
   */
  def bsn: String = {
    val firstFour: String = alpha.randomInt(1000, 9999).toString
    val secondFour = firstFour.reverse
    firstFour + secondFour + "0"
  }

  def religion: String = utility.getValueFromArray("religion")

  def zodiac: String = utility.getValueFromArray("zodiac")

  def zodiac(birthday: String): String = {
    try {
      val formatter: DateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")
      val date = formatter.parseDateTime(birthday)
      date.getDayOfYear match {
        case it if 1 until 20 contains it => "Capricorn"
        case it if 20 until 50 contains it => "Aquarius"
        case it if 50 until 80 contains it => "Pisces"
        case it if 80 until 110 contains it => "Aries"
        case it if 110 until 141 contains it => "Taurus"
        case it if 141 until 172 contains it => "Gemini"
        case it if 172 until 204 contains it => "Cancer"
        case it if 204 until 235 contains it => "Leo"
        case it if 235 until 266 contains it => "Virgo"
        case it if 266 until 296 contains it => "Libra"
        case it if 296 until 326 contains it => "Scorpio"
        case it if 326 until 356 contains it => "Sagittarius"
        case it if 356 to 365 contains it => "Capricorn"
      }
    } catch {
      case _: IllegalArgumentException => throw new IllegalArgumentException("Format of the date should be dd-MM-yyyy")
    }
  }

  def height(cm: Boolean): String = {
    if (cm) alpha.randomDouble(1.50, 2.20).toString.substring(0,4) + " cm" else alpha.randomInt(150, 220).toString + " m"
  }

  def weight(metric: Boolean): String = if (metric) alpha.randomInt(50, 110).toString + " kg" else alpha.randomInt(30, 90).toString + " lbs"

  def bloodType: String = utility.getValueFromArray("blood_type")

  def occupation: String = utility.getValueFromArray("occupation")

}
