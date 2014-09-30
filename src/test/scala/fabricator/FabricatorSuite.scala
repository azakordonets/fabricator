package fabricator

import org.testng.annotations._
import com.github.nscala_time.time.Imports._
import org.scalatest.testng.TestNGSuite
import com.typesafe.scalalogging.slf4j.LazyLogging

/**
  * Created by Andrew Zakordonets on 16/05/14.
 */
class FabricatorSuite extends TestNGSuite with LazyLogging{

  val fabr = new Fabricator
  val util = new Utility("en")
  val alpha = fabr.alphaNumeric()
  val contact = fabr.contact()
  val calendar = fabr.calendar()
  var wordsFaker = fabr.words()
  
  @Test
  def testFirstName() = {
    val name = contact.firstName()
    logger.info("Checking first name value "+name)
    assert(name.nonEmpty)
  }

  @Test
  def testLastName() = {
    val name = contact.lastName()
    logger.info("Checking last name value "+name)
    assert(name.nonEmpty)
  }

  @Test
  def testEmail() = {
    logger.info("Checking email "+contact.eMail())
    assert(contact.eMail().nonEmpty)
  }

  @DataProvider(name = "birthdayDP")
  def birthdayDP():Array[Array[Any]] = {
    Array(Array(0),
      Array(10),
      Array(25),
      Array(50),
      Array(100),
      Array(1000)
    )
  }

  @Test(dataProvider = "birthdayDP")
  def testBirthDay(age: Int) = {
    logger.info("Checking when is the birthday of an "+age+" years old")
    val birthDate = contact.birthday(age)
    val day = DateTime.now.getDayOfMonth()
    val month = DateTime.now.getMonthOfYear()
    assert(birthDate.getDayOfMonth == day)
    assert(birthDate.getMonthOfYear == month)
    assert(DateTime.now.getYear - age == birthDate.getYear)
  }

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
    logger.info("Checking numerify "+result)
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
  def testLetterify(value: String, matchPatter: String) = {
    val result = alpha.letterify(value)
    logger.info("Checking letterify "+result)
    assert(result.matches(matchPatter))
  }


  @Test
  def testDefaultNumbers() {
    val number = alpha.number()
    logger.info("Checking default number function. Should return random number below 1000 : ")
    assert( 0 to 1000 contains number)
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

    def calculate(numberValue: Any): Any = numberValue match{
      case numberValue: Int =>  alpha.number(numberValue)
      case numberValue: Double => alpha.double(numberValue)
      case numberValue: Float => alpha.float(numberValue)
    }
    val result = calculate(value)
    logger.info("Checking custom number with "+numberType+" type function. Should return with specific type and below specified value : ")
    expectResult(result.getClass)(numberType)
    assert(util.less(result, value))
  }

  @DataProvider(name = "numbersRandomRange")
  def numbersRandomRange():Array[Array[Any]] = {
    Array(Array(100, 150),
      Array(100.10, 200.01),
      Array(100.10f, 250.10f)
    )
  }

  @Test(dataProvider = "numbersRandomRange")
  def testNumbersRandomRange(min: Any, max: Any)  {

    def calculate(minValue: Any, maxValue: Any): Any = (minValue, maxValue) match{
      case (min:Int, max:Int) => alpha.number(min, max)
      case (min:Double, max:Double) => alpha.double(min, max)
      case (min:Float, max:Float) => alpha.float(min, max)
    }
    val actualNumber = calculate(min, max)
    logger.info("Checking custom number with  type function. Should return with specific type and below specified value : ")
    expectResult(actualNumber.getClass)(min.getClass)
    assert(util.less(actualNumber, max))
    assert(util.less(min, actualNumber))
  }

  @Test
  def testDefaultDateGetter() = {
    logger.info("Checking default date value "+calendar.date())
    expectResult(calendar.date())(DateTime.now.toString("dd-mm-yyyy"))
  }

  @DataProvider(name = "dateFormats")
  def dateFormats() = {
    Array(Array("dd:mm:yyyy"),
          Array("mm:dd:yyyy"),
          Array("dd:mm:yyyy"),
          Array("dd:MM:yyyy"),
          Array("dd:MM:YYYY"),
          Array("dd/MM/YYYY"),
          Array("dd/MM/YY"),
          Array("dd-MM-yyyy"),
          Array("dd.MM.yyyy"),
          Array("dd.M.yyyy"),
          Array("dd-MM-yyyy HH"),
          Array("dd-MM-yyyy HH:mm"),
          Array("dd-MM-yyyy HH:mm:ss"),
          Array("dd-MM-yyyy H:m:s"),
          Array("dd-MM-yyyy H:m:s a")
    )
  }

  @Test(dataProvider = "dateFormats")
  def testDateGetterWithDifferentFormats(format: String) = {
    logger.info("Checking date value with "+format+" format :"+calendar.date(format))
    expectResult(calendar.date(format))(DateTime.now.toString(format))
  }

  @DataProvider(name = "datesVariations")
  def datesVariations() = {
    Array(Array("1","6","1800","00","00", "dd-mm-yyy hh:mm a", "01-00-1800 12:00 AM"),
          Array("12","1","2012","02","10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
          Array("1","2","2014","14","10", "dd-mm-yyy hh:mm a", "01-10-2014 02:10 PM"),
          Array("10","3","2014","14","10", "dd/mm/yyy hh:mm", "10/10/2014 02:10"),
          Array("20","4","2014","14","10", "dd.mm.yyy hh-mm", "20.10.2014 02-10"),
          Array("20","4","2014","14","10", "dd.mm.yyy hh-mm-ss", "20.10.2014 02-10-00"),
          Array("13","5","2014","14","10", "ddmmyyy hh:mm", "13102014 02:10"),
          Array("16","6","2014","14","10", "dd-mm-yyy hh", "16-10-2014 02"),
          Array("18","7","2014","14","10", "dd-mm-yyy", "18-10-2014"),
          Array("20","8","2014","14","10", "dd-mm-yy hh:mm", "20-10-14 02:10"),
          Array("30","9","2014","14","10", "dd-MM-yyy hh:mm", "30-09-2014 02:10"),
          Array("18","10","2014","14","10", "dd-mm-yyyy hh:mm", "18-10-2014 02:10"),
          Array("18","11","2014","14","10", "dd-mm-y hh:mm", "18-10-2014 02:10"),
          Array("02","12","2014","14","10", "d-mm-yyy hh:mm", "2-10-2014 02:10"),
          Array("5","6","2014","14","10", "d-m-yyyy hh:mm", "5-10-2014 02:10"),
          Array("14","6","2014","14","10", "d-m-y h:m", "14-10-2014 2:10"),
          Array("14","6","2014","14","10", "dd-mmm-yyyy h:m", "14-010-2014 2:10"),
          Array("12","6","2014","14","10", "dd MMM yyyy h:m", "12 Jun 2014 2:10"),
          Array("12","6","2014","14","10", "dd MMMM yyyy h:m", "12 June 2014 2:10")
    )
  }

//  @Test(dataProvider = "datesVariations")
//  def testDatesVariations(day:String, month:String, year:String, hour:String, minute:String, format:String, expectedResult: String) {
//    val date = calendar.date(day.toInt, month.toInt, year.toInt, hour.toInt, minute.toInt, format)
//    logger.info("Checking date value with "+format+" format :"+date)
//    expectResult(date)(expectedResult)
//  }

  @DataProvider(name = "datesNegativeVariations")
  def datesNegativeVariations() = {
    Array(
      Array("31","6","1800","00","00", "dd-mm-yyy hh:mm a", "01-00-1800 12:00 AM"),
      Array("0","1","2012","02","10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("-1","1","2012","02","10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1","-1","2012","02","10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1","1","2012","-02","10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1","1","2012","02","-10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1","1","2012","02","10", "ffffffff", "12-10-2012 02:10"),
      Array("","","","","", "", "12-10-2012 02:10")
    )
  }

//  @Test(dataProvider = "datesNegativeVariations")
//  def datesNegativeVariationsTest(day:String, month:String, year:String, hour:String, minute:String, format:String, expectedResult: String) {
//    intercept[Exception]{
//        val date = calendar.date(day.toInt, month.toInt, year.toInt, hour.toInt, minute.toInt, format)
//        logger.info("Checking date value with "+format+" format :"+date)
//    }
//  }

  @DataProvider(name = "wordsCountDP")
  def wordsCountDP() = {
    Array(Array("10"),
          Array("10"),
          Array("100"),
          Array("1000"),
          Array("10000"),
          Array("100000")
    )
  }

  @Test(dataProvider = "wordsCountDP")
  def testWords(count: String) = {
    logger.info("Getting words array generated with length = "+count)
    expectResult(wordsFaker.words(count.toInt).length)(count.toInt)
  }

  @Test
  def testSentenceDefault() = {
    var sentence = wordsFaker.sentence()
    logger.info("Testing sentence generation. Creating sentence with 10 words lenght: \n" + sentence)
    expectResult(sentence.split(" ").length)(10)
  }

  @Test
  def testSentenceCustomLength() = {
    var sentence = wordsFaker.sentence(20)
    logger.info("Testing sentence generation. Creating sentence with 10 words lenght: \n" + sentence)
    expectResult(sentence.split(" ").length)(20)
  }

  @Test
  def testTextDefaultValue() = {
    val paragraph = wordsFaker.paragraph()
    logger.info("Testing sentence generation. Creating text with 10 words lenght: \n" + paragraph)
    expectResult(paragraph.length)(100)
  }

  @Test(dataProvider = "wordsCountDP")
  def testTextCustomValue(length: String  ) = {
    val paragraph = wordsFaker.paragraph(length.toInt)
    logger.info("Testing sentence generation. Creating paragraph with chars lenght: "+length.toInt + "\n" +paragraph)
    expectResult(paragraph.length())(length.toInt)
  }



}
