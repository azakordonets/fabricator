package fabricator


class Fabricator(lang: String = "en",
                 private val utility: UtilityService,
                      alphaFaker: Alphanumeric = new Alphanumeric,
                      contactFaker: Contact = new Contact,
                      calendarFaker: Calendar = new Calendar,
                      wordsFaker: Words = new Words,
                      internetFaker: Internet = new Internet,
                      financeFaker: Finance = new Finance) {

  def this () {
    this("en",
      new UtilityService(),
      new Alphanumeric,
      new Contact,
      new Calendar,
      new Words,
      new Internet,
      new Finance)
  }

  def this (lang: String) {
    this(lang,
      new UtilityService(lang),
      new Alphanumeric,
      new Contact,
      new Calendar,
      new Words,
      new Internet,
      new Finance)
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

  def internet() : Internet = {
    internetFaker
  }

  def finance() = {
    financeFaker
  }

}
