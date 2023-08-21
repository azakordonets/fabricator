package fabricator

import org.testng.annotations.{DataProvider, Test}

class AlphaNumericTestSuite extends BaseTestSuite {

  @DataProvider(name = "numerifyDP")
  def numerifyDP = {
    Array(
      Array("###ABC", "\\d{3}\\w{3}"),
      Array("###ABC###", "\\d{3}\\w{3}\\d{3}"),
      Array("ABC###", "\\w{3}\\d{3}"),
      Array("A#B#C#", "\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}"),
      Array("ABC", "\\w{3}"),
      Array("154,#$$%ABC", "\\d{3}\\W{1}\\d{1}\\W{3}\\w{3}")
    )
  }

  @Test(dataProvider = "numerifyDP")
  def testNumerify(value: String, matchPattern: String) = {
    val result = alpha.numerify(value)
    if (debugEnabled) logger.debug("Checking numerify with pattern: " + value + " : " + result)
    assert(result.matches(matchPattern))
  }

  @Test
  def testNumerifyList() = {
    val list = alpha.numerifyList("###ABC", 10)
    assertResult(10)(list.size)
    list.foreach(x => assert(x.matches("\\d{3}\\w{3}")))
  }

  @DataProvider(name = "letterifyDP")
  def letterifyDP = {
    Array(
      Array("???123", "\\w{3}\\d{3}"),
      Array("???123???", "\\w{3}\\d{3}\\w{3}"),
      Array("123???", "\\d{3}\\w{3}"),
      Array("1?2?3?", "\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}"),
      Array("123", "\\d{3}"),
      Array("154,??$$%123", "\\d{3}\\W{1}\\w{2}\\W{3}\\d{3}")
    )
  }

  @Test(dataProvider = "letterifyDP")
  def testLetterify(value: String, matchPattern: String) = {
    val result = alpha.letterify(value)
    if (debugEnabled) logger.debug("Checking letterify with pattern: " + value + " : " + result)
    assert(result.matches(matchPattern))
  }

  @Test
  def testLetterifyList() = {
    val list = alpha.letterifyList("???123", 10)
    assertResult(10)(list.size)
    list.foreach(x => assert(x.matches("\\w{3}\\d{3}")))
  }

  @DataProvider(name = "botifyDP")
  def botifyDP = {
    Array(
      Array("???123###", "\\w{3}\\d{6}"),
      Array("???123???###", "\\w{3}\\d{3}\\w{3}\\d{3}"),
      Array("123???", "\\d{3}\\w{3}"),
      Array("1?2?3?", "\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}"),
      Array("123", "\\d{3}"),
      Array("154,??$$%123", "\\d{3}\\W{1}\\w{2}\\W{3}\\d{3}")
    )
  }

  @Test(dataProvider = "botifyDP")
  def testBotify(value: String, matchPattern: String) = {
    val result = alpha.botify(value)
    if (debugEnabled) logger.debug("Checking botify with pattern: " + value + " : " + result)
    assert(result.matches(matchPattern))
  }

  @Test
  def testBotifyList() = {
    val list = alpha.botifyList("???123###", 10)
    assertResult(10)(list.size)
    list.foreach(x => assert(x.matches("\\w{3}\\d{6}")))
  }

  @Test
  def testDefaultInteger() = {
    val integer = alpha.randomInt
    if (debugEnabled)
      logger.debug("Checking default integer function. Should return random integer below 1000 : " + integer)
    assert(0 to 1000 contains integer)
    assert(integer.isInstanceOf[Int])
  }

  @Test
  def testDefaultLong() = {
    val longNumber = alpha.randomLong
    if (debugEnabled)
      logger.debug("Checking default longNumber function. Should return random longNumber below 1000 : " + longNumber)
    assert(longNumber.isInstanceOf[Long])
  }

  @Test
  def testDefaultDouble() = {
    val double = alpha.randomDouble
    if (debugEnabled)
      logger.debug("Checking default double function. Should return random double below 1000 : " + double)
    assert(double > 0 && double < 1000)
    assert(double.isInstanceOf[Double])
  }

  @Test
  def testDefaultFloat() = {
    val float = alpha.randomFloat
    if (debugEnabled) logger.debug("Checking default float function. Should return random float below 1000 : " + float)
    assert(float > 0 && float < 1000)
    assert(float.isInstanceOf[Float])
  }

  @Test
  def testDefaultGausian() = {
    val gausian = alpha.randomGausian
    if (debugEnabled)
      logger.debug("Checking default gausian function. Should return random gausian below 1000 : " + gausian)
    assert(gausian < 1000)
    assert(gausian.isInstanceOf[Double])
  }

  @Test
  def testDefaultBoolean() = {
    var trueCount  = 0
    var falseCount = 0
    for (i <- 0 to 100) {
      val boolean = alpha.randomBoolean
      if (boolean) trueCount = trueCount + 1
      else falseCount = falseCount + 1
    }
    assert(trueCount > 0 && falseCount > 0)
  }

  @Test
  def testDefaultString() = {
    val string = alpha.randomString
    if (debugEnabled) logger.debug("Checking default string function. Should return random string below 30 : " + string)
    assert(string.length == 30)
    assert(string.isInstanceOf[String])
  }

  @Test
  def testCustomString() = {
    val extendedString = alpha.randomString(50)
    if (debugEnabled)
      logger.debug(
        "Checking default extendedString function. Should return random extendedString below 50 : " + extendedString
      )
    assert(extendedString.length == 50)
    assert(extendedString.isInstanceOf[String])
  }

  @Test
  def testDefaultStringsList() = {
    val strings = alpha.randomStrings
    assertResult(100)(strings.length)
    strings.foreach(string => assert(string.length >= 5 && string.length <= 100))
  }

  @Test
  def testCustomStringsList() = {
    val strings = alpha.randomStrings(10, 10, 20)
    assertResult(20)(strings.length)
    strings.foreach(string => assert(string.length >= 10 && string.length <= 10))
  }

  @DataProvider(name = "charSets")
  def charSets() = {
    Array(
      Array("aaaa", 10),
      Array("1234567890", 100),
      Array("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", 500),
      Array("!@#$%^&*()_+{}\"|:?><", 30)
    )
  }

  @Test(dataProvider = "charSets")
  def testCustomStringWithSpecificCharSet(charSet: String, max: Int) = {
    val string = alpha.randomString(charSet, max)
    if (debugEnabled)
      logger.debug(
        "Checking default extendedString function. Should return random extendedString below " + max + " : " + string
      )
    assert(string.length == max)
    for (symbol <- string) assert(charSet.contains(symbol))
  }

  @DataProvider(name = "numbersCustomTypes")
  def numbersCustomTypes(): Array[Array[Any]] = {
    Array[Array[Any]](
      Array(100),
      Array(100.10),
      Array(100.10f),
      Array(100000000000L)
    )
  }

  @Test(dataProvider = "numbersCustomTypes")
  def testCustomNumberType(maxValue: Any, numberType: Any) = {

    def calculate(randomNumber: Any): Any = randomNumber match {
      case number: Int    => alpha.randomInt(number)
      case number: Double => alpha.randomDouble(number)
      case number: Float  => alpha.randomFloat(number)
      case number: Long   => alpha.randomLong(number)
    }
    val result = calculate(maxValue)
    if (debugEnabled)
      logger.debug(
        "Checking custom number with " + numberType + " type function. Should return with specific type and below specified value : "
      )
    assertResult(result.getClass)(numberType)
    assert(util.isLess(result, maxValue))
  }

  @DataProvider(name = "numbersRandomRange")
  def numbersRandomRange(): Array[Array[Any]] = {
    Array[Array[Any]](Array(100, 150), Array(100.10d, 200.01d), Array(100.10f, 250.10f), Array(1L, 100L))
  }

  @Test(dataProvider = "numbersRandomRange")
  def testNumbersRandomRange(min: Any, max: Any) = {

    def calculate(minValue: Any, maxValue: Any): Any = (minValue, maxValue) match {
      case (min: Int, max: Int)       => alpha.randomInt(min, max)
      case (min: Double, max: Double) => alpha.randomDouble(min, max)
      case (min: Float, max: Float)   => alpha.randomFloat(min, max)
      case (min: Long, max: Long)     => alpha.randomLong(min, max)
      case (_, _)                     =>
    }
    val actualNumber = calculate(min, max)
    if (debugEnabled) logger.debug("Checking random number in range: " + actualNumber)
    assertResult(actualNumber.getClass)(min.getClass)
    assert(util.isLess(actualNumber, max))
    assert(util.isLessOrEqual(min, actualNumber))
  }

  @DataProvider(name = "integerRangeWithStep")
  def integerRangeWithStep(): Array[Array[Any]] = {
    Array[Array[Any]](
      Array(1, 10, 1, List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
      Array(1, 10, 2, List(1, 3, 5, 7, 9)),
      Array(1, 10, 0, List(1, 3, 5, 7, 9)),
      Array(1, 10, -1, List(1, 3, 5, 7, 9))
    )
  }

  @Test(dataProvider = "integerRangeWithStep")
  def testIntegerRangeWithStep(min: Int, max: Int, step: Int, expectedResult: List[Int]) = {
    if (step <= 0) {
      try
        alpha.randomIntsRange(min, max, step)
      catch {
        case e: IllegalArgumentException => assertResult(e.getMessage)("Step should be more then 0")
      }
    } else {
      val generatedStream = alpha.randomIntsRange(min, max, step)
      assertResult(expectedResult)(generatedStream)
    }

  }

  @DataProvider(name = "doubleRangeWithStep")
  def doubleRangeWithStep(): Array[Array[Any]] = {
    Array[Array[Any]](
      Array(1, 10, 1, List(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)),
      Array(1, 10, 2, List(1.0, 3.0, 5.0, 7.0, 9.0)),
      Array(1, 3, 0.5, List(1.0, 1.5, 2.0, 2.5, 3.0)),
      Array(1, 3, -1, List(1.0, 1.5, 2.0, 2.5, -1.0)),
      Array(1, 3, 0, List(1.0, 1.5, 2.0, 2.5, -1.0))
    )
  }

  @Test(dataProvider = "doubleRangeWithStep")
  def testDoubleRangeWithStep(min: Double, max: Double, step: Double, expectedResult: List[Double]) = {
    if (step <= 0) {
      try
        alpha.randomDoublesRange(min, max, step)
      catch {
        case e: IllegalArgumentException => assertResult(e.getMessage)("Step should be more then 0")
      }
    } else {
      val generatedStream = alpha.randomDoublesRange(min, max, step)
      assertResult(expectedResult)(generatedStream)
    }
  }

  @DataProvider(name = "floatRangeWithStep")
  def floatRangeWithStep(): Array[Array[Any]] = {
    Array[Array[Any]](
      Array(1.0f, 10.0f, 1.0f, List(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f)),
      Array(1.0f, 10.0f, 2.0f, List(1.0f, 3.0f, 5.0f, 7.0f, 9.0f)),
      Array(1.0f, 3.0f, 0.5f, List(1.0f, 1.5f, 2.0f, 2.5f, 3.0f)),
      Array(1.0f, 3.0f, 0.0f, List(1.0f, 1.5f, 2.0f, 2.5f, 3.0f)),
      Array(1.0f, 3.0f, -1.0f, List(1.0f, 1.5f, 2.0f, 2.5f, 3.0f))
    )
  }

  @Test(dataProvider = "floatRangeWithStep")
  def testFloatRangeWithStep(min: Float, max: Float, step: Float, expectedResult: List[Float]) = {
    if (step <= 0) {
      try
        alpha.randomFloatsRange(min, max, step)
      catch {
        case e: IllegalArgumentException => assertResult(e.getMessage)("Step should be more then 0")
      }
    } else {
      val generatedStream = alpha.randomFloatsRange(min, max, step)
      assertResult(expectedResult)(generatedStream)
    }
  }

  @DataProvider(name = "longRangeWithStep")
  def longRangeWithStep(): Array[Array[Any]] = {
    Array[Array[Any]](
      Array(1L, 10L, 1L, List(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)),
      Array(1L, 10L, 2L, List(1L, 3L, 5L, 7L, 9L))
    )
  }

  @Test(dataProvider = "longRangeWithStep")
  def testLongRangeWithStep(min: Long, max: Long, step: Long, expectedResult: List[Long]) = {
    if (step <= 0) {
      try
        alpha.randomLongsRange(min, max, step)
      catch {
        case e: IllegalArgumentException => assertResult(e.getMessage)("Step should be more then 0")
      }
    } else {
      val generatedStream = alpha.randomLongsRange(min, max, step)
      assertResult(expectedResult)(generatedStream)
    }
  }

  @Test
  def testHash() = {
    val hash = alpha.randomHash
    if (debugEnabled) logger.debug("Checking random hash number with default length:  " + hash)
    assert(hash.length() == 40)
    val customLengthHash = alpha.randomHash(10)
    if (debugEnabled) logger.debug("Checking random hash number with length = 10:  " + customLengthHash)
    assert(customLengthHash.length == 10)
  }

  @Test
  def testHashList() = {
    val defaultList = alpha.randomHashList
    assertResult(100)(defaultList.size)
    defaultList.foreach(x => assert(x.length == 40))
    val customList = alpha.randomHashList(20, 40, 30)
    assertResult(30)(customList.size)
    customList.foreach(x => assert(x.length >= 20 && x.length <= 40))
  }

  @Test
  def testGuid() = {
    val guid = alpha.randomGuid
    if (debugEnabled) logger.debug("Checking random guid number :  " + guid)
    assert(guid.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}"))
  }

  @Test
  def testGuidList() = {
    val defaultList = alpha.randomGuidList
    assertResult(100)(defaultList.size)
    val customList = alpha.randomGuidList(8, 30)
    assertResult(30)(customList.size)
  }

}
