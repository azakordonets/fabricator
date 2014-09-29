package fabricator


class Fabricator(lang: String = "en") extends Utility(lang){

  protected lazy val alphaFaker = new Alphanumeric
  protected lazy val contactFaker = new Contact
  protected lazy val calendarFaker = new Calendar
  protected lazy val wordsFaker = new Words
  protected lazy val internetFaker = new Internet


  def contact() : Contact = {
    contactFaker
  }

  def alphaNumeric() : Alphanumeric = {
    alphaFaker
  }

  def calendar() : Calendar = {
    calendarFaker
  }
  def words(): Words = {
    wordsFaker
  }

}
