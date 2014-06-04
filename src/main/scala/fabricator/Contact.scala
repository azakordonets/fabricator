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

}
