package fabricator

import scala.util.Random

object Words {

  def apply(): Words = new Words(UtilityService(), new Random())

  def apply(locale: String): Words = new Words(UtilityService(locale), new Random())

}

class Words(private val utility: UtilityService, private val random: Random) {

  protected val wordsList = utility.getListFromJson("words")

  def word: String = {
    utility.getValueFromArray("word")
  }

  def paragraph: String = paragraph(100)

  def paragraph(charsLength: Int): String = {
    val wordsSequence = sentence(charsLength)
    val builder = new StringBuilder
    var counter = 0
    while (counter < charsLength) {
      builder.append(wordsSequence(counter))
      counter += 1
    }
    builder.toString()
  }

  def sentence(wordQuantity: Int): String = words(wordQuantity).mkString(" ") + ". "


  def words(): Array[String] = words(10)

  def words(quantity: Int): Array[String] = {
    val resultArray = new Array[String](quantity)
    for (count <- quantity - 1 to 0 by -1) {
      for (wordsUnit <- wordsList) {
        resultArray(count) = wordsUnit(random.nextInt(wordsUnit.length - 1))
      }
    }
    resultArray
  }

  def sentence: String = sentence(10)


}
