package fabricator

import scala.util.Random

/**
 * Created by Andrew Zakordonets on 05/06/14.
 */
class Words(private val utility: UtilityService, private val random: Random) {

  protected val wordsList = utility.getListFromJson("words")

  def this() {
    this(new UtilityService(), new Random())
  }

  def word(): String = {
    utility.getValueFromArray("word")
  }

  def paragraph(): String = {
    paragraph(100)
  }

  def paragraph(charsLength: Int): String = {
    val wordsSequence = sentence(charsLength)
    var builder = new StringBuilder
    var counter = 0
    while (counter < charsLength) {
      builder.append(wordsSequence(counter))
      counter += 1
    }
    builder.toString()
  }

  def sentence(): String = {
    sentence(10)
  }

  def sentence(wordQuantity: Int): String = {
    words(wordQuantity).mkString(" ") + ". "
  }

  def words(): Array[String] = {
    words(10)
  }

  def words(quantity: Int): Array[String] = {
    val resultArray = new Array[String](quantity)
    for (count <- quantity - 1 to 0 by -1) {
      for (wordsUnit <- wordsList) {
        resultArray(count) = wordsUnit(random.nextInt(wordsUnit.length - 1))
      }
    }
    resultArray
  }

}
