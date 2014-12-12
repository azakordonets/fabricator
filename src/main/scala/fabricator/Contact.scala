package fabricator

import com.github.nscala_time.time.Imports._

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
    firstName() + "_" + lastName() +  alpha.numerify("###") + "@" + utility.getValueFromArray("free_email")
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
   * @return
   */
  def bsn() : String = {
    val firstFour: String = alpha.integer(1000, 9999).toString
    val secondFour = firstFour.reverse
    return firstFour + secondFour + "0"
  }

  def religion(): String = {
    utility.getValueFromArray("religion")
  }

}
