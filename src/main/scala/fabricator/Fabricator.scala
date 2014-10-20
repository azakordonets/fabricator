package fabricator


class Fabricator(lang: String = "en",
                 private val utility: UtilityService,
                      alphaFaker: Alphanumeric = new Alphanumeric,
                      contactFaker: Contact = new Contact,
                      calendarFaker: Calendar = new Calendar,
                      wordsFaker: Words = new Words,
                      internetFaker: Internet = new Internet) extends UtilityService(lang) {

  def this () {
    this("en",
      new UtilityService("en"),
      new Alphanumeric,
      new Contact,
      new Calendar,
      new Words,
      new Internet)
  }

  object Fabricator {
    def apply() = new Fabricator("en",
      new UtilityService(lang),
      new Alphanumeric,
      new Contact,
      new Calendar,
      new Words,
      new Internet)
  }

  def contact(): Contact = {
    contactFaker
  }

  def alphaNumeric(): Alphanumeric = {
    alphaFaker
  }

  def calendar(): Calendar = {
    calendarFaker
  }

  def words(): Words = {
    wordsFaker
  }

}
