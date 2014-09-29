package fabricator

/**
 * Created by Andrew Zakordonets on 05/06/14.
 */
protected class Words extends Fabricator{

  protected val wordsList = getListFromJson("words")

  def word(): String = {
    getValueFromArray("word")
  }

  def words(quantity: Int = 10): Array[String] = {
    val resultArray = new Array[String](quantity)
    for ( count <- quantity-1 to 0 by -1 ) {
      for (wordsUnit <- wordsList) {
        resultArray(count) = wordsUnit(getRandomInt(wordsUnit.length - 1))
      }
    }
    resultArray
  }

  def sentence(wordQuantity: Int = 10): String  = {
    words(wordQuantity).mkString(" ") + ". "
  }

  def paragraph(): String = {
      paragraph(100)
  }

  def paragraph(charsLength: Int):String = {
    val wordsSequence = sentence(charsLength)
    var builder = new StringBuilder
    var counter = 0
    while (counter < charsLength) {
      builder.append(wordsSequence(counter))
      counter += 1
    }
    builder.toString()
  }

}
