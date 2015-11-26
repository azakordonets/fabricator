package fabricator

import org.testng.annotations.{DataProvider, Test}

import scala.collection.mutable

class WordsTestSuite extends BaseTestSuite {

  @Test
  def testCustomConstructor()  {
    val customWords = fabricator.Words("us")
    assert(customWords != null)
  }
  
  @DataProvider(name = "wordsCountDP")
  def wordsCountDP():Array[Array[Int]]= {
    Array(Array(10),
      Array(100),
      Array(1000),
      Array(4000),
      Array(9500)
    )
  }

  @Test
  def testDefaultWords() {
    val wordsDefaultArray: Array[String] = words.words()
    if (debugEnabled) logger.debug("Getting words array generated with default length ")
    assert(wordsDefaultArray.length == 10)
    val inputSet: mutable.Set[String] = scala.collection.mutable.Set()
    wordsDefaultArray.indices.foreach{
      index => inputSet.add(wordsDefaultArray(index))
    }
    assertResult(10)(inputSet.size)
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testWordsMaximumAmountException(): Unit = {
    words.words(100001)
  }
  
  @Test(dataProvider = "wordsCountDP")
  def testWords(count: Int) = {
    if (debugEnabled) logger.debug("Getting words array generated with length = " + count)
    assertResult(words.words(count).length)(count)
  }

  @Test
  def testSentenceDefault() = {
    val sentence = words.sentence
    if (debugEnabled) logger.debug("Testing sentence generation. Creating sentence with 10 words length \n" + sentence)
    assertResult(sentence.split(" ").length)(10)
  }

  @Test
  def testSentenceCustomLength() = {
    val sentence = words.sentence(20)
    if (debugEnabled) logger.debug("Testing sentence generation. Creating sentence with 10 words length: \n" + sentence)
    assertResult(sentence.split(" ").length)(20)
  }

  @Test
  def testTextDefaultValue() = {
    val paragraph = words.paragraph
    if (debugEnabled) logger.debug("Testing sentence generation. Creating text with 10 words length: \n" + paragraph)
    assertResult(paragraph.length)(100)
  }

  @Test(dataProvider = "wordsCountDP")
  def testTextCustomValue(length: Int) = {
    val paragraph = words.paragraph(length)
    if (debugEnabled) logger.debug("Testing sentence generation. Creating paragraph with chars length: " + length + "\n" + paragraph)
    assertResult(paragraph.length())(length)
  }


}
