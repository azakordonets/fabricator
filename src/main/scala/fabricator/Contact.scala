package fabricator

import com.github.nscala_time.time.Imports._


/**
  * Created by Andrew Zakordonets on 02/06/14.
 */
class Contact( private val utility:UtilityService,
               private val alpha: Alphanumeric)  {


  def this () = {
    this( new UtilityService(), new Alphanumeric);

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

  def appartmentNumber() = {
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

}
