package fabricator

/**
 * Created by Andrew Zakordonets on 02/06/14.
 */
protected class Contact extends Fabricator{

  def fName():String =  {
    getValueFromArray("first_name")
  }

  def lName():String = {
    getValueFromArray("last_name")
  }

  def eMail():String = {
    fName() + "_" + lName() +  alpha.intoNumbers("###") + "@" + getValueFromArray("free_email")
  }

  def phoneNumber() = {
    alpha.intoNumbers(getValueFromArray("phone_formats"))
  }

  def streetName() = {
    getValueFromArray("street_suffix")
  }

  def houseNumber() = {
    alpha.numerify(getValueFromArray("house_number"))
  }

  def appartmentNumber() = {
    alpha.numerify(getValueFromArray("app_number"))
  }

  def postcode() = {
    alpha.botify(getValueFromArray("postcode"))
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
