package fabricator

import org.testng.annotations.{DataProvider, Test}

class WordsTestSuite extends BaseTestSuite {

  @Test
  def testCustomConstructor()  {
    val customWords = fabricator.Words("us")
    assert(customWords != null)
  }
  
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

  @Test
  def testDefaultWords() {
    val wordsDefaultSet = words.words
    if (debugEnabled) logger.debug("Getting words array generated with default length ")
    assert(wordsDefaultSet.length == 10)
  }
  
  @Test(dataProvider = "wordsCountDP")
  def testWords(count: String) = {
    if (debugEnabled) logger.debug("Getting words array generated with length = " + count)
    assertResult(words.words(count.toInt).length)(count.toInt)
  }

  @Test
  def testSentenceDefault() = {
    var sentence = words.sentence
    if (debugEnabled) logger.debug("Testing sentence generation. Creating sentence with 10 words lenght: \n" + sentence)
    assertResult(sentence.split(" ").length)(10)
  }

  @Test
  def testSentenceCustomLength() = {
    var sentence = words.sentence(20)
    if (debugEnabled) logger.debug("Testing sentence generation. Creating sentence with 10 words lenght: \n" + sentence)
    assertResult(sentence.split(" ").length)(20)
  }

  @Test
  def testTextDefaultValue() = {
    val paragraph = words.paragraph
    if (debugEnabled) logger.debug("Testing sentence generation. Creating text with 10 words lenght: \n" + paragraph)
    assertResult(paragraph.length)(100)
  }

  @Test(dataProvider = "wordsCountDP")
  def testTextCustomValue(length: String) = {
    val paragraph = words.paragraph(length.toInt)
    if (debugEnabled) logger.debug("Testing sentence generation. Creating paragraph with chars lenght: " + length.toInt + "\n" + paragraph)
    assertResult(paragraph.length())(length.toInt)
  }


}
