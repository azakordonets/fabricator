package fabricator

import com.github.nscala_time.time.Imports._
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.{DataProvider, Test}

class CalendarTestSuite extends TestNGSuite with LazyLogging{

  val fabr = new Fabricator
  val util = new UtilityService()
  val calendar = fabr.calendar()

  @Test
  def testDefaultDateGetter() = {
    logger.info("Checking default date value " + calendar.date())
    assertResult(calendar.date())(DateTime.now.toString("dd-MM-yyyy"))
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
    logger.info("Checking date value with " + format + " format :" + calendar.date(format))
    assertResult(calendar.date(format))(DateTime.now.toString(format))
  }

  @DataProvider(name = "datesVariations")
  def datesVariations() = {
    Array(Array("1", "6", "1800", "00", "00", "dd-mm-yyy hh:mm a", "01-00-1800 12:00 AM"),
      Array("12", "1", "2012", "02", "10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1", "2", "2014", "14", "10", "dd-mm-yyy hh:mm a", "01-10-2014 02:10 PM"),
      Array("10", "3", "2014", "14", "10", "dd/mm/yyy hh:mm", "10/10/2014 02:10"),
      Array("20", "4", "2014", "14", "10", "dd.mm.yyy hh-mm", "20.10.2014 02-10"),
      Array("20", "4", "2014", "14", "10", "dd.mm.yyy hh-mm-ss", "20.10.2014 02-10-00"),
      Array("13", "5", "2014", "14", "10", "ddmmyyy hh:mm", "13102014 02:10"),
      Array("16", "6", "2014", "14", "10", "dd-mm-yyy hh", "16-10-2014 02"),
      Array("18", "7", "2014", "14", "10", "dd-mm-yyy", "18-10-2014"),
      Array("20", "8", "2014", "14", "10", "dd-mm-yy hh:mm", "20-10-14 02:10"),
      Array("30", "9", "2014", "14", "10", "dd-MM-yyy hh:mm", "30-09-2014 02:10"),
      Array("18", "10", "2014", "14", "10", "dd-mm-yyyy hh:mm", "18-10-2014 02:10"),
      Array("18", "11", "2014", "14", "10", "dd-mm-y hh:mm", "18-10-2014 02:10"),
      Array("02", "12", "2014", "14", "10", "d-mm-yyy hh:mm", "2-10-2014 02:10"),
      Array("5", "6", "2014", "14", "10", "d-m-yyyy hh:mm", "5-10-2014 02:10"),
      Array("14", "6", "2014", "14", "10", "d-m-y h:m", "14-10-2014 2:10"),
      Array("14", "6", "2014", "14", "10", "dd-mmm-yyyy h:m", "14-010-2014 2:10"),
      Array("12", "6", "2014", "14", "10", "dd MMM yyyy h:m", "12 Jun 2014 2:10"),
      Array("12", "6", "2014", "14", "10", "dd MMMM yyyy h:m", "12 June 2014 2:10")
    )
  }

  //  @Test(dataProvider = "datesVariations")
  //  def testDatesVariations(day:String, month:String, year:String, hour:String, minute:String, format:String, expectedResult: String) {
  //    val date = calendar.date(day.toInt, month.toInt, year.toInt, hour.toInt, minute.toInt, format)
  //    logger.info("Checking date value with "+format+" format :"+date)
  //    assertResult(date)(expectedResult)
  //  }

  @DataProvider(name = "datesNegativeVariations")
  def datesNegativeVariations() = {
    Array(
      Array("31", "6", "1800", "00", "00", "dd-mm-yyy hh:mm a", "01-00-1800 12:00 AM"),
      Array("0", "1", "2012", "02", "10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("-1", "1", "2012", "02", "10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1", "-1", "2012", "02", "10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1", "1", "2012", "-02", "10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1", "1", "2012", "02", "-10", "dd-mm-yyy hh:mm", "12-10-2012 02:10"),
      Array("1", "1", "2012", "02", "10", "ffffffff", "12-10-2012 02:10"),
      Array("", "", "", "", "", "", "12-10-2012 02:10")
    )
  }

  //  @Test(dataProvider = "datesNegativeVariations")
  //  def datesNegativeVariationsTest(day:String, month:String, year:String, hour:String, minute:String, format:String, expectedResult: String) {
  //    intercept[Exception]{
  //        val date = calendar.date(day.toInt, month.toInt, year.toInt, hour.toInt, minute.toInt, format)
  //        logger.info("Checking date value with "+format+" format :"+date)
  //    }
  //  }

}
