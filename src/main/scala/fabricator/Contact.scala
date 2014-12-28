package fabricator

import java.lang.IllegalArgumentException

import com.github.nscala_time.time.Imports._
import org.joda.time.format.DateTimeFormatter

import scala.util.Random


/**
  * Created by Andrew Zakordonets on 02/06/14.
 */
class Contact( private val utility:UtilityService,
               private val alpha: Alphanumeric,
               private val random: Random)  {


  def this () = {
    this( new UtilityService(), new Alphanumeric, new Random());

  }



  def firstName():String =  {
    utility.getValueFromArray("first_name")
  }

  def lastName():String = {
    utility.getValueFromArray("last_name")
  }

  def fullName(prefix:Boolean = false ):String = {
    if (prefix)  prefix + " "+ firstName() + " " + lastName()
    else firstName() + " " + lastName()
  }

  def birthday(age: Int): String = {
    DateTimeFormat.forPattern("dd-MM-yyyy").print(DateTime.now - age.years)
  }

  def birthday(age: Int, format: String ): String = {
    DateTimeFormat.forPattern(format).print(DateTime.now - age.years)
  }

  def eMail():String = {
    firstName().toLowerCase() + "_" + lastName().toLowerCase() +  alpha.numerify("###") + "@" + utility.getValueFromArray("free_email").toLowerCase()
  }

  def phoneNumber() = {
    alpha.numerify(utility.getValueFromArray("phone_formats"))
  }

  def streetName() = {
    utility.getValueFromArray("street_suffix")
  }

  def houseNumber() = {
    alpha.numerify(utility.getValueFromArray("house_number"))
  }

  def apartmentNumber() = {
    alpha.numerify(utility.getValueFromArray("app_number"))
  }

  def address() = {
    streetName() + " " + houseNumber() + ", " + apartmentNumber()
  }

  def postcode() = {
    alpha.botify(utility.getValueFromArray("postcode"))
  }

  def state() = {
    utility.getValueFromArray("state")
  }

  def stateShortCode() = {
    utility.getValueFromArray("state_abbr")
  }

  def company() = {
    utility.getValueFromArray("company_suffix")
  }

  /**
   * BSN number is generated in accordance with http://nl.wikipedia.org/wiki/Burgerservicenummer article
   * @
   */
  def bsn() : String = {
    val firstFour: String = alpha.integer(1000, 9999).toString
    val secondFour = firstFour.reverse
     firstFour + secondFour + "0"
  }

  def religion(): String = {
    utility.getValueFromArray("religion")
  }

  def zodiac(): String = {
    utility.getValueFromArray("zodiac")
  }

  def zodiac(birthday: String): String = {
    var date: DateTime = null
    try{
      val formatter: DateTimeFormatter  = DateTimeFormat.forPattern("dd-MM-yyyy");
      date  = formatter.parseDateTime(birthday);
    } catch {
      case e: IllegalArgumentException => throw new IllegalArgumentException("Format of the date should be dd-MM-yyyy")
    }
    val day = date.dayOfMonth().get()
    val month = date.monthOfYear().get()
    if((month == 1 && day <= 20) || (month == 12 && day >= 22)) {
       "Capricorn"
    } else if ((month == 1 && day >= 21) || (month == 2 && day <= 18)) {
       "Aquarius"
    } else if((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
       "Pisces"
    } else if((month == 3 && day >= 21) || (month == 4 && day <= 20)) {
       "Aries"
    } else if((month == 4 && day >= 21) || (month == 5 && day <= 20)) {
       "Taurus"
    } else if((month == 5 && day >= 21) || (month == 6 && day <= 20)) {
       "Gemini"
    } else if((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
       "Cancer"
    } else if((month == 7 && day >= 23) || (month == 8 && day <= 23)) {
       "Leo"
    } else if((month == 8 && day >= 24) || (month == 9 && day <= 23)) {
       "Virgo"
    } else if((month == 9 && day >= 24) || (month == 10 && day <= 23)) {
       "Libra"
    } else if((month == 10 && day >= 24) || (month == 11 && day <= 22)) {
       "Scorpio"
    } else if((month == 11 && day >= 23) || (month == 12 && day <= 21)) {
       "Sagittarius"
    } else {
      "uknown"
    }
    
  }

  def height(cm: Boolean): String = {
   if (cm)  alpha.double(1.50, 2.20).toString + " cm" else alpha.integer(150, 220).toString() + " m"
  }

  def weight(): String = {
    alpha.integer(50,110).toString + " kg"
  }

  def bloodType() : String = {
    utility.getValueFromArray("blood_type")
  }

  def occupation(): String = {
    utility.getValueFromArray("occupation")
  }



}
