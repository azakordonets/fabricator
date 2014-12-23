package fabricator

import com.github.nscala_time.time.Imports._
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.{DataProvider, Test}

class CalendarTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val util = new UtilityService()
  val calendar = fabr.calendar()

  @Test
  def testDefaultDateGetter() = {
    val date = calendar.date()
    logger.info("Checking default date value " + date)
    assert(date.matches("\\d{2}-\\d{2}-\\d{4}"))
  }

  @DataProvider(name = "dateFormats")
  def dateFormats() = {
    Array(Array("dd:mm:yyyy", "\\d{2}:\\d{2}:\\d{4}"),
      Array("mm:dd:yyyy", "\\d{2}:\\d{2}:\\d{4}"),
      Array("dd:mm:yyyy", "\\d{2}:\\d{2}:\\d{4}"),
      Array("dd:MM:yyyy", "\\d{2}:\\d{2}:\\d{4}"),
      Array("dd:MM:YYYY", "\\d{2}:\\d{2}:\\d{4}"),
      Array("dd/MM/YYYY", "\\d{2}/\\d{2}/\\d{4}"),
      Array("dd/MM/YY", "\\d{2}/\\d{2}/\\d{2}"),
      Array("dd-MM-yyyy", "\\d{2}-\\d{2}-\\d{4}"),
      Array("dd.MM.yyyy", "\\d{2}\\.\\d{2}\\.\\d{4}"),
      Array("dd.M.yyyy", "\\d{2}\\.\\d{1,2}\\.\\d{4}"),
      Array("dd-MM-yyyy HH", "\\d{2}-\\d{2}-\\d{4} \\d{2}"),
      Array("dd-MM-yyyy HH:mm", "\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}"),
      Array("dd-MM-yyyy HH:mm:ss", "\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}"),
      Array("dd-MM-yyyy H:m:s", "\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"),
      Array("dd-MM-yyyy H:m:s a", "\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} \\w{2}")
    )
  }

  @Test(dataProvider = "dateFormats")
  def testDateGetterWithDifferentFormats(format: String, regex: String) = {
    val date = calendar.date(format)
    logger.info("Checking date value with " + format + " format :" + date)
    assert(date.matches(regex))
  }


  @Test
  def testAmPm() = {
    val ampm = calendar.ampm()
    logger.info("Testing random amPm value : " + ampm)
    assert(ampm.equals("am") || ampm.equals("pm"))
  }

  @Test
  def testSecond() = {
    val second = calendar.second()
    logger.info("Testing random second value: " + second)
    assert(second.toInt > 0 && second.toInt <= 60)
  }

  @Test
  def testMinute() = {
    val minute = calendar.minute()
    logger.info("Testing random minute value: " + minute)
    assert(minute.toInt > 0 && minute.toInt < 60)
  }
  
  @Test
  def testHour() = {
    val hour24 = calendar.hour(true)
    val hour12 = calendar.hour()
    assert(hour24.toInt >= 0 && hour24.toInt < 24)
    assert(hour12.toInt >= 0 && hour12.toInt < 12)
  }
  
  @Test
  def testDay() = {
    val day = calendar.day()
    logger.info("Testing random day value: " + day)
    assert(day.toInt > 0 && day.toInt < 31)
  }

  @DataProvider
  def dateDP() = {
    Array(Array(2014, 02, 30, 00, 00, "28-02-2014 12:00"),
      Array(1000, 02, 30, 00, 00, "28-02-1000 12:00"),
      Array(1980, 01, 50, 12, 30, "31-01-1980 12:30"),
      Array(2250, 02, 30, 00, 00, "28-02-2250 12:00"))
  }

  @Test(dataProvider = "dateDP")
  def testDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, expectedResult: String) = {
    val date = calendar.date(year, month, day, hour, minute)
    logger.info("Testing random date: " + date)
    assert(date.equals(expectedResult))
  }


}
