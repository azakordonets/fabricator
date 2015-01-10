package fabricator

object Fabricator {

  def alphaNumeric(): Alphanumeric = {
    Alphanumeric()
  }

  def calendar(): Calendar = {
    Calendar()
  }

  def calendar(locale: String): Calendar = {
    Calendar(locale)
  }

  def contact(): Contact = {
    Contact()
  }

  def contact(locale: String): Contact = {
    Contact(locale)
  }

  def file(): FileGenerator = {
    FileGenerator()
  }

  def file(locale: String): FileGenerator = {
    FileGenerator(locale)
  }

  def finance(): Finance = {
    Finance()
  }

  def finance(locale: String): Finance = {
    Finance(locale)
  }

  def internet(): Internet = {
    Internet()
  }

  def internet(locale: String): Internet = {
    Internet(locale)
  }

  def location(): Location = {
    Location()
  }

  def location(locale: String): Location = {
    Location(locale)
  }

  def mobile(): Mobile = {
    Mobile()
  }

  def words(): Words = {
    Words()
  }

  def words(locale: String): Words = {
    Words(locale)

  }

}