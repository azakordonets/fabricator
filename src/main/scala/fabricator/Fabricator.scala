package fabricator

import scala.io.Source
import play.api.libs.json._
import scala.util.Random


class Fabricator(lang: String = "en") extends Utility(lang){

  protected lazy val alpha = new Alphanumeric
  protected lazy val contact = new Contact
  protected lazy val dateEntity = new Date
  protected lazy val word = new Words



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

  def number(): Any = {
    alpha.getNumber()
  }

  def number(max: Any):Any = {
    alpha.getNumber(max)
  }

  def number(min:Any, max: Any): Any = {
    alpha.getAnyNumber(min, max)
  }

  def date(): String = {
    dateEntity.getDate()
  }

  def date(format: String): String = {
    dateEntity.getDate(format)
  }

  def date(day:Int, month:Int, year:Int, hour:Int, minute:Int, format: String) = {
    dateEntity.getDetailedDate(day, month, year, hour, minute, format)
  }

  def words(count: Int) = {
    word.getWords(count)
  }

  def sentence(): String = {
    word.getSentence(10)
  }

  def sentence(words: Int): String = {
    word.getSentence(words)
  }

  def text(length: Int = 60): String = {
    word.getText(length)
  }

}
