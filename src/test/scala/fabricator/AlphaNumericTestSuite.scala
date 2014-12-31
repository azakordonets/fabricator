package fabricator

import org.testng.annotations.{DataProvider, Test}

class AlphaNumericTestSuite extends BaseTestSuite {

  @DataProvider(name = "numerifyDP")
  def numerifyDP = {
    Array(Array("###ABC", "\\d{3}\\w{3}"),
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

  @DataProvider(name = "letterifyDP")
  def letterifyDP = {
    Array(Array("???123", "\\w{3}\\d{3}"),
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

  @DataProvider(name = "botifyDP")
  def botifyDP = {
    Array(Array("???123###", "\\w{3}\\d{6}"),
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
  def testDefaultInteger() {
    val integer = alpha.integer()
    if (debugEnabled) logger.debug("Checking default integer function. Should return random integer below 1000 : " + integer)
    assert(0 to 1000 contains integer)
    assert(integer.isInstanceOf[Int])
  }

  @Test
  def testDefaultDouble() {
    val double = alpha.double()
    if (debugEnabled) logger.debug("Checking default double function. Should return random double below 1000 : " + double)
    assert(double > 0 && double < 1000)
    assert(double.isInstanceOf[Double])
  }

  @Test
  def testDefaultFloat() {
    val float = alpha.float()
    if (debugEnabled) logger.debug("Checking default float function. Should return random float below 1000 : " + float)
    assert(float > 0 && float < 1000)
    assert(float.isInstanceOf[Float])
  }

  @Test
  def testDefaultBoolean() {
    val boolean = alpha.boolean()
    if (debugEnabled) logger.debug("Checking default boolean function. Should return random boolean below 1000 : " + boolean)
    assert(boolean == true || boolean == false)
    assert(boolean.isInstanceOf[Boolean])
  }

  @Test
  def testDefaultGausian() {
    val gausian = alpha.gausian()
    if (debugEnabled) logger.debug("Checking default gausian function. Should return random gausian below 1000 : " + gausian)
    assert(gausian < 1000)
    assert(gausian.isInstanceOf[Double])
  }

  @Test
  def testDefaultString() {
    val string = alpha.string()
    if (debugEnabled) logger.debug("Checking default string function. Should return random string below 30 : " + string)
    assert(string.length == 30)
    assert(string.isInstanceOf[String])
  }

  @Test
  def testCustomString() {
    val extendedString = alpha.string(50)
    if (debugEnabled) logger.debug("Checking default extendedString function. Should return random extendedString below 50 : " + extendedString)
    assert(extendedString.length == 50)
    assert(extendedString.isInstanceOf[String])
  }

  @DataProvider(name = "charSets")
  def charSets() = {
    Array(Array("aaaa", 10),
      Array("1234567890", 100),
      Array("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", 500),
      Array("!@#$%^&*()_+{}\"|:?><", 30)
    )
  }

  @Test(dataProvider = "charSets")
  def testCustomStringWithSpecificCharSet(charSet: String, max: Int) = {
    val string = alpha.string(charSet, max)
    if (debugEnabled) logger.debug("Checking default extendedString function. Should return random extendedString below " + max + " : " + string)
    assert(string.length == max)
    for (symbol <- string) assert(charSet.contains(symbol))
  }


  @DataProvider(name = "numbersCustomTypes")
  def numbersCustomTypes() = {
    Array(Array(100, classOf[Integer]),
      Array(100.10, classOf[java.lang.Double]),
      Array(100.10f, classOf[java.lang.Float])
    )
  }

  @Test(dataProvider = "numbersCustomTypes")
  def testCustomNumberType(value: Any, numberType: Any) {

    def calculate(numberValue: Any): Any = numberValue match {
      case numberValue: Int => alpha.integer(numberValue)
      case numberValue: Double => alpha.double(numberValue)
      case numberValue: Float => alpha.float(numberValue)
    }
    val result = calculate(value)
    if (debugEnabled) logger.debug("Checking custom number with " + numberType + " type function. Should return with specific type and below specified value : ")
    assertResult(result.getClass)(numberType)
    assert(util.isLess(result, value))
  }

  @DataProvider(name = "numbersRandomRange")
  def numbersRandomRange(): Array[Array[Any]] = {
    Array(Array(100, 150),
      Array(100.10, 200.01),
      Array(100.10f, 250.10f)
    )
  }

  @Test(dataProvider = "numbersRandomRange")
  def testNumbersRandomRange(min: Any, max: Any) {

    def calculate(minValue: Any, maxValue: Any): Any = (minValue, maxValue) match {
      case (min: Int, max: Int) => alpha.integer(min, max)
      case (min: Double, max: Double) => alpha.double(min, max)
      case (min: Float, max: Float) => alpha.float(min, max)
    }
    val actualNumber = calculate(min, max)
    if (debugEnabled) logger.debug("Checking random number in range: " + actualNumber)
    assertResult(actualNumber.getClass)(min.getClass)
    assert(util.isLess(actualNumber, max))
    assert(util.isLessOrEqual(min, actualNumber))
  }

  @Test
  def testHash() = {
    val hash = alpha.hash()
    if (debugEnabled) logger.debug("Checking random hash number with default length:  " + hash)
    assert(hash.length() == 40)
    val customLengthHash = alpha.hash(10)
    if (debugEnabled) logger.debug("Checking random hash number with length = 10:  " + customLengthHash)
    assert(customLengthHash.length == 10)
  }

  @Test
  def testGuid() = {
    val guid = alpha.guid()
    if (debugEnabled) logger.debug("Checking random guid number :  " + guid)
    assert(guid.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}"))
  }


}
