package fabricator

import scala.io.Source
import play.api.libs.json._
import scala.util.Random


class Fabricator(lang: String = "en") {

  protected lazy val alpha = new Alphanumeric
  protected lazy val contact = new Contact
  protected val valuesJson = Json.parse(Source.fromFile("src/main/resources/"+lang+".json").mkString)
  protected val rand = new Random()



  protected def getValueFromArray(key: String): String = {
    val array = (valuesJson \\ key)(0).asOpt[Array[String]].get
    val random_index = rand.nextInt(array.length);
    array(random_index)
  }

  def firstName(): String = {
    contact.fName()
  }

  def lastName(): String = {
    contact.lName()
  }

  def name(): String = {
    contact.fName() ++ " " ++ contact.lName()
  }

  def email(): String = {
    contact.eMail()
  }

  def numerify(string: String): String = {
    alpha.intoNumbers(string)
  }

  def letterify(string:String): String = {
    alpha.intoLetters(string)
  }

  def botify(string: String): String = {
    letterify(numerify(string))
  }

}
