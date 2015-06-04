package fabricator

import com.github.nscala_time.time.Imports._
import fabricator.enums.{DateFormat, DateRangeType}
import org.testng.annotations.{DataProvider, Test}

class CalendarTestSuite extends BaseTestSuite {

  @Test
  def testCustomConstructor()  {
    val customCalendar = fabricator.Calendar("us")
    assert(customCalendar != null)
  }

  @Test
  def testDefaultDateGetter() = {
    val date = calendar.date
    if (debugEnabled) logger.debug("Checking default date value " + date)
    assert(date.matches("\\d{2}-\\d{2}-\\d{4}"))
  }

  @DataProvider(name = "dateFormats")
  def dateFormats() = {
    Array(Array(DateFormat.dd_mm_yyyy_SEMICOLON, "\\d{2}:\\d{2}:\\d{4}"),
      Array(DateFormat.mm_dd_yyyy_SEMICOLON, "\\d{2}:\\d{2}:\\d{4}"),
      Array(DateFormat.dd_MM_yyyy_SEMICOLON, "\\d{2}:\\d{2}:\\d{4}"),
      Array(DateFormat.dd_MM_YYYYY_SEMICOLON, "\\d{2}:\\d{2}:\\d{4}"),
      Array(DateFormat.dd_MM_YYYY_BACKSLASH, "\\d{2}/\\d{2}/\\d{4}"),
      Array(DateFormat.dd_MM_YY_BACKSLASH, "\\d{2}/\\d{2}/\\d{2}"),
      Array(DateFormat.dd_MM_yyyy, "\\d{2}-\\d{2}-\\d{4}"),
      Array(DateFormat.dd_MM_yyyy_DOT, "\\d{2}\\.\\d{2}\\.\\d{4}"),
      Array(DateFormat.dd_M_yyyy_DOT, "\\d{2}\\.\\d{1,2}\\.\\d{4}"),
      Array(DateFormat.dd_MM_yyyy_HH, "\\d{2}-\\d{2}-\\d{4} \\d{2}"),
      Array(DateFormat.dd_MM_yyyy_HH_mm, "\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}"),
      Array(DateFormat.dd_MM_yyyy_HH_mm_ss, "\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}"),
      Array(DateFormat.dd_MM_yyyy_H_m_s, "\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"),
      Array(DateFormat.dd_MM_yyyy_H_m_s_a, "\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} \\w{2}"),
      Array(DateFormat.dd_MM_yy_HH_mm, "\\d{1,2}-\\d{1,2}-\\d{2} \\d{1,2}:\\d{1,2}"),
      Array(DateFormat.yyMMdd, "\\d{1,6}"),
      Array(DateFormat.yyyyMMdd, "\\d{1,8}"),
      Array(DateFormat.yyyyMMddHHmm, "\\d{1,12}"),
      Array(DateFormat.yyyy_MM_dd, "\\d{4}-\\d{1,2}-\\d{1,2}"),
      Array(DateFormat.MM_yyyy, "\\d{1,2}-\\d{4}"),
      Array(DateFormat.MM_yy, "\\d{1,2}-\\d{1,2}"),
      Array(DateFormat.HH_mm, "\\d{1,2}:\\d{1,2}"),
      Array(DateFormat.dd_MMMM_yyyy_SPACE, "\\d{1,2} \\w{1,10} \\d{4}"),
      Array(DateFormat.d_MMM_SPACE, "\\d{1,2} \\w{1,12}"),
      Array(DateFormat.dd_MM_yy, "\\d{1,2}-\\d{1,2}-\\d{2}"),
      Array(DateFormat.dd_MM, "\\d{1,2}-\\w{1,2}"),
      Array(DateFormat.dd, "\\d{1,2}")
    )
  }

  @Test(dataProvider = "dateFormats")
  def testDateGetterWithDifferentFormats(format: DateFormat, regex: String) = {
    val date = calendar.date(format)
    if (debugEnabled) logger.debug("Checking date value with " + format + " format :" + date)
    assert(date.matches(regex))
  }


  @Test
  def testAmPm() = {
    var amCount = 0
    var pmCount = 0
    for (i <- 1 to 50){
      val ampm = calendar.ampm
      if (ampm.equals("am")) amCount = amCount + 1 else pmCount = pmCount + 1
      if (debugEnabled) logger.debug("Testing random amPm value : " + ampm)
    }
    assert(amCount > 0 && pmCount > 0)
  }

  @Test
  def testSecond() = {
    val second = calendar.second
    if (debugEnabled) logger.debug("Testing random second value: " + second)
    assert(second.toInt >= 0 && second.toInt <= 60)
  }

  @Test
  def testMinute() = {
    val minute = calendar.minute
    if (debugEnabled) logger.debug("Testing random minute value: " + minute)
    assert(minute.toInt >= 0 && minute.toInt < 60)
  }

  @Test
  def testHour() = {
    val hour24 = calendar.hour24h
    val hour12 = calendar.hour12h
    assert(hour24.toInt >= 0 && hour24.toInt < 24)
    assert(hour12.toInt >= 0 && hour12.toInt < 12)
  }

  @Test
  def testTime() = {
    val time24 = calendar.time24h
    val hour24 = time24.split(":")(0).toInt
    val minute24 = time24.split(":")(1).toInt
    assert(hour24 >= 0 && hour24 < 24 && minute24 >= 0 && minute24 < 60)
    val time = calendar.time12h
    val hour = time.split(":")(0).toInt
    val minute = time.split(":")(1).toInt
    assert(hour >= 0 && hour < 12 && minute >= 0 && minute < 60)
  }

  @Test
  def testDefaultDay() = {
    val day = calendar.day.toInt
    assert(day >= 1 && day <= 31)
  }
  
  @Test
  def testDay() = {
    val year = calendar.year
    val month = calendar.month
    val day = calendar.day(year.toInt, month.toInt)
    if (debugEnabled) logger.debug("Testing random day value: " + day)
    assert(day.toInt >= 0 && day.toInt < 31)
  }
  
  @DataProvider(name = "dayExceptionDP")
  def dayExceptionDP():Array[Array[Any]] = {
    Array(Array(0, 12),
      Array(1, 32),
      Array(32, 10)
    )
  }
  
  @Test(dataProvider = "dayExceptionDP")
  def testDayException(min: Int, max: Int) = {
    try {
      val date = calendar.day(2000, 2, min, max)
    } catch  {
      case e: IllegalArgumentException => assertResult("min and max values should be in [1,31] range")(e.getMessage)
    }
  }

  @Test
  def testCustomDay() = {
    val year = calendar.year.toInt
    val month = calendar.month.toInt
    val day = calendar.day(year, month, 10, 20 ).toInt
    if (debugEnabled) logger.debug("Testing random custom day value in range [10,20]: " + day)
    assert(day >= 10 && day <= 20)
  }

  @Test
  def testMonth() = {
    val monthNumber = calendar.month(asNumber = true)
    if (debugEnabled) logger.debug("Checking random month value numeric: " + monthNumber)
    assert(monthNumber.toInt > 0 && monthNumber.toInt < 12)
    val monthLettered = calendar.month(asNumber = false)
    val months = util.getArrayFromJson("month")
    assert(months.contains(monthLettered))
  }

  @Test
  def testYear() = {
    val year = calendar.year.toInt
    if (debugEnabled) logger.debug("Testing random year value: " + year)
    assert(year >= 1970 && year < 2015)
  }

  @DataProvider
  def dateDP():Array[Array[Any]] = {
    Array(Array(2014, 2, 30, 0, 0, "28-02-2014 00:00"),
      Array(1000, 2, 30, 0, 0, "28-02-1000 00:00"),
      Array(1980, 1, 50, 12, 30, "31-01-1980 12:30"),
      Array(2250, 2, 30, 0, 0, "28-02-2250 00:00"))
  }

  @Test(dataProvider = "dateDP")
  def testDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, expectedResult: String) = {
    val date = calendar.date(year, month, day, hour, minute)
    if (debugEnabled) logger.debug("Testing random date: " + date)
    assert(date.equals(expectedResult))
  }

  @DataProvider
  def datesRangeDP():Array[Array[Any]] = {
    Array(Array(2001, 1, 1, 2010, 1, 1,DateRangeType.YEARS, 1, 9),
      Array(2001, 1, 1, 2010, 1, 1,DateRangeType.YEARS, 2, 5),
      Array(2001, 1, 1, 2010, 1, 1,DateRangeType.MONTHS, 1, 108),
      Array(2001, 1, 1, 2010, 1, 1,DateRangeType.MONTHS, 2, 54),
      Array(2001, 1, 1, 2010, 1, 1,DateRangeType.WEEKS, 2, 235),
      Array(2001, 1, 1, 2001, 10, 1,DateRangeType.DAYS, 10, 28),
      Array(2001, 1, 1, 2001, 10, 1,DateRangeType.HOURS, 10, 656),
      Array(2001, 1, 1, 2001, 10, 1,DateRangeType.MINUTES, 10, 39306)
    )
  }
  
  @Test(dataProvider = "datesRangeDP")
  def testDateRangeInYears(startYear: Int, startMonth: Int, startDay: Int, endYear: Int, endMonth: Int, endDay: Int, rangeType: DateRangeType, step: Int, expectedSize: Int ) = {
    val datesRange = calendar.datesRange
                             .startYear(startYear)
                             .startMonth(startMonth)
                             .startDay(startDay)
                             .stepEvery(step, rangeType)
                             .endYear(endYear)
                             .endMonth(endMonth)
                             .endDay(endDay)
                             .asList
    assertResult(expectedSize)(datesRange.length)
  }
  
  @Test
  def testDateRangeInYearsWithException() = {
    try {
      val datesRange = calendar.datesRange
        .startYear(2010)
        .startMonth(1)
        .startDay(1)
        .stepEvery(0, DateRangeType.DAYS)
        .endYear(2011)
        .endMonth(1)
        .endDay(1)
        .asList
    }catch {
      case e: IllegalArgumentException => assertResult("Step should be > 0")(e.getMessage)
    }
  }
  
  @Test
  def testDateWithFormat() = {
    val format = DateFormat.dd_MM_yyyy
    val year = calendar.year.toInt
    val month = calendar.month.toInt
    val day = calendar.day(year, month).toInt
    val hour = calendar.hour12h.toInt
    val minute = calendar.minute.toInt
    val date = calendar.date(year, month, day, hour, minute, format)
    assertResult(new DateTime(year, month, day, hour, minute).toString(format.getFormat))(date)
  }


  @Test
  def testDateObject() = {
    val dateObject = calendar.dateObject
    assert(dateObject.isInstanceOf[DateTime])
    val year = dateObject.year().get()
    val month = dateObject.monthOfYear().get()
    val day = dateObject.dayOfMonth().get()
    val hour = dateObject.hourOfDay().get()
    val minute = dateObject.minuteOfHour().get()
    val second = dateObject.secondOfMinute().get()
    assert(year >= 1970 && year <= 2015)
    assert(month >= 0 && month <= 12)
    assert(day >= 0 && day <= 31)
    assert(hour >= 0 && hour <= 24)
    assert(minute >= 0 && minute <= 60)
    assert(second >= 0 && second <= 60)
  }

  @DataProvider
  def dateWithPeriodDP():Array[Array[Any]] = {
    Array(Array(0, 0, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(1, 0, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusYears(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(-1, 0, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusYears(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 1, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusMonths(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, -1, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusMonths(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 1, 0, 0, 0, DateFormat.dd_MM_yyyy, DateTime.now.plusWeeks(1).toString(DateFormat.dd_MM_yyyy.getFormat)),
      Array(0, 0, -1, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusWeeks(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 0, 1, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusDays(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 0, -1, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusDays(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 0, 0, 1, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusHours(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 0, 0, -1, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusHours(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 0, 0, 0, 1, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusMinutes(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(0, 0, 0, 0, 0, -1, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusMinutes(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(1, 1, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusYears(1).plusMonths(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(-1, -1, 0, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusYears(1).minusMonths(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(3, 3, 3, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusYears(3).plusMonths(3).plusWeeks(3).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(-1, -1, -1, 0, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusYears(1).minusMonths(1).minusWeeks(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(1, 1, 1, 1, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.plusYears(1).plusMonths(1).plusWeeks(1).plusDays(1).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(-5, -5, -5, -5, 0, 0, DateFormat.dd_MM_yyyy_SEMICOLON, DateTime.now.minusYears(5).minusMonths(5).minusWeeks(5).minusDays(5).toString(DateFormat.dd_MM_yyyy_SEMICOLON.getFormat)),
      Array(1, 1, 1, 1, 1, 0, DateFormat.dd_MM_yyyy_HH_SEMICOLON, DateTime.now.plusYears(1).plusMonths(1).plusWeeks(1).plusDays(1).plusHours(1).toString(DateFormat.dd_MM_yyyy_HH_SEMICOLON.getFormat)),
      Array(-1, -1, -1, -1, -1, 0, DateFormat.dd_MM_yyyy_HH_SEMICOLON, DateTime.now.minusYears(1).minusMonths(1).minusWeeks(1).minusDays(1).minusHours(1).toString(DateFormat.dd_MM_yyyy_HH_SEMICOLON.getFormat)),
      Array(10, 10, 10, 10, 10, 10, DateFormat.dd_MM_yyyy_HH_SEMICOLON, DateTime.now.plusYears(10).plusMonths(10).plusWeeks(10).plusDays(10).plusHours(10).plusMinutes(10).toString(DateFormat.dd_MM_yyyy_HH_SEMICOLON.getFormat)),
      Array(-100, -100, -100, -100, -100, -100, DateFormat.dd_MM_yyyy_HH_SEMICOLON, DateTime.now.minusYears(100).minusMonths(100).minusWeeks(100).minusDays(100).minusHours(100).minusMinutes(100).toString(DateFormat.dd_MM_yyyy_HH_SEMICOLON.getFormat))
    )
  }

  @Test(dataProvider = "dateWithPeriodDP")
  def testDateWithPeriod(year: Int, month: Int, week: Int, day: Int, hour: Int, minute: Int, format: DateFormat, expectedDate: String) = {
    val date = calendar.dateRelative(year, month, week, day, hour, minute, format)
    if (debugEnabled) logger.debug("Testing random date with dateWithPeriod method: " + date)
    assertResult(expectedDate)(date)
  }

  @Test
  def testDateWithPeriodWithDefaultFormat() = {
    val date = calendar.dateRelative(0, 0, 0, 0, 0, 0)
    assertResult(DateTime.now.toString("dd-MM-yyyy HH:mm"))(date)
  }

}
