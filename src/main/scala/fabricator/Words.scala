package fabricator

import scala.collection.mutable
import scala.util.Random
object Words {

  def apply(): Words = new Words(UtilityService(), new Random())

  def apply(locale: String): Words = new Words(UtilityService(locale), new Random())

}

class Words(private val utility: UtilityService,
            private val random: Random) {

  protected val wordsList:Array[String] = utility.getWordsArray

  def word: String = {
    val wordsArray: Array[String] = utility.getWordsArray
    wordsArray(random.nextInt(wordsArray.length - 1))
  }

  def paragraph: String = paragraph(100)

  def paragraph(charsLength: Int): String = {
    val wordsSequence = sentence(charsLength)
    val builder = new StringBuilder
    (0 until charsLength).foreach {
      counter => builder.append(wordsSequence(counter))
    }
    builder.toString()
  }

  def sentence(wordQuantity: Int): String = words(wordQuantity).mkString(" ") + ". "


  def words(): Array[String] = words(10)

  def words(quantity: Int): Array[String] = {
    if (quantity >= 10000) throw new Exception("Maximum allowed quantity is limited to 100,000 words ")
    val resultSet: mutable.Set[String] = mutable.Set()
    while (resultSet.size != quantity) {
      resultSet.add(wordsList(random.nextInt(wordsList.length - 1)))
    }
    resultSet.toArray
  }

  def sentence: String = sentence(10)


}
