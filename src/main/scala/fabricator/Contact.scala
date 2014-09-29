package fabricator

/**
 * Created by Andrew Zakordonets on 02/06/14.
 */
protected class Contact extends Fabricator{

  def firstName():String =  {
    getValueFromArray("first_name")
  }

  def lastName():String = {
    getValueFromArray("last_name")
  }

  def fullName(prefix:Boolean = false ):String = {
    if (prefix)  prefix + " "+ firstName() + " " + lastName()
    else firstName() + " " + lastName()
  }

  def eMail():String = {
    firstName() + "_" + lastName() +  alphaFaker.numerify("###") + "@" + getValueFromArray("free_email")
  }

  def phoneNumber() = {
    alphaFaker.numerify(getValueFromArray("phone_formats"))
  }

  def streetName() = {
    getValueFromArray("street_suffix")
  }

  def houseNumber() = {
    alphaFaker.numerify(getValueFromArray("house_number"))
  }

  def appartmentNumber() = {
    alphaFaker.numerify(getValueFromArray("app_number"))
  }

  def postcode() = {
    alphaFaker.botify(getValueFromArray("postcode"))
  }

  def state() = {
    getValueFromArray("state")
  }

  def stateShortCode() = {
    getValueFromArray("state_abbr")
  }

  def company() = {
    getValueFromArray("company_suffix")
  }

}
