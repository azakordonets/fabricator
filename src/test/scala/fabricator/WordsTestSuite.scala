package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.{Test, DataProvider}

class WordsTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val util = new UtilityService()
  val alpha = fabr.alphaNumeric()
  val contact = fabr.contact()
  val calendar = fabr.calendar()
  var wordsFaker = fabr.words()
  val internet = fabr.internet()
  val finance = fabr.finance()

  @DataProvider(name = "wordsCountDP")
  def wordsCountDP() = {
    Array(Array("10"),
      Array("100"),
      Array("1000"),
      Array("4000"),
      Array("10000"),
      Array("100000")
    )
  }

  @Test(dataProvider = "wordsCountDP")
  def testWords(count: String) = {
    logger.info("Getting words array generated with length = " + count)
    assertResult(wordsFaker.words(count.toInt).length)(count.toInt)
  }

  @Test
  def testSentenceDefault() = {
    var sentence = wordsFaker.sentence()
    logger.info("Testing sentence generation. Creating sentence with 10 words lenght: \n" + sentence)
    assertResult(sentence.split(" ").length)(10)
  }

  @Test
  def testSentenceCustomLength() = {
    var sentence = wordsFaker.sentence(20)
    logger.info("Testing sentence generation. Creating sentence with 10 words lenght: \n" + sentence)
    assertResult(sentence.split(" ").length)(20)
  }

  @Test
  def testTextDefaultValue() = {
    val paragraph = wordsFaker.paragraph()
    logger.info("Testing sentence generation. Creating text with 10 words lenght: \n" + paragraph)
    assertResult(paragraph.length)(100)
  }

  @Test(dataProvider = "wordsCountDP")
  def testTextCustomValue(length: String) = {
    val paragraph = wordsFaker.paragraph(length.toInt)
    logger.info("Testing sentence generation. Creating paragraph with chars lenght: " + length.toInt + "\n" + paragraph)
    assertResult(paragraph.length())(length.toInt)
  }



}
