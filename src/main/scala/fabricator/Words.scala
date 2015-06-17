package fabricator

import scala.collection.mutable.Set
import scala.util.Random
object Words {

  def apply(): Words = new Words(UtilityService())

  def apply(locale: String): Words = new Words(UtilityService(locale))

}

class Words(private val utility: UtilityService) {

  protected val wordsList:Array[String] = utility.getWordsArray()

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
    val resultSet: Set[String] = Set()
    while(resultSet.size != quantity) {
      resultSet.add(wordsList(Random.nextInt(wordsList.length - 1)))
    }
    resultSet.toArray
  }

  def sentence: String = sentence(10)


}
