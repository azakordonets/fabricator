package fabricator

import play.api.libs.json.JsValue

/**
 * Created by Andrew Zakordonets on 05/06/14.
 */
protected class Words extends Fabricator{

  protected val wordsList = getListFromJson("words")

  def getWord(): String = {
    getValueFromArray("word")
  }

  def getWords(quantity: Int = 10): Array[String] = {
    val resultArray = new Array[String](quantity)
    for ( count <- quantity-1 to 0 by -1 ) {
      for (wordsUnit <- wordsList) {
        resultArray(count) = wordsUnit(getRandomInt(wordsUnit.length - 1))
      }
    }
    resultArray
  }

  def getSentence(words: Int = 10): String  = {
    getWords(words).mkString(" ")
  }

  def getText(charsLength: Int):String = {
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
