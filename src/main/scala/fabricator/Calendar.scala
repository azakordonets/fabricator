package fabricator

import org.joda.time.{DateTime, IllegalFieldValueException}
import play.api.libs.json.{JsNull, Json, JsValue}
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.util.Random

object Calendar {

  def apply(): Calendar = new Calendar(UtilityService(), new Random(), Alphanumeric())

  def apply(locale: String) = new Calendar(UtilityService(locale), new Random(), Alphanumeric())
}

class Calendar(private val utility: UtilityService,
               private val random: Random,
               private val alpha: Alphanumeric) {

  def ampm: String = {
    val randomVal = alpha.getInteger(0, 10)
    if (randomVal < 5) "am" else "pm"
  }

  def day: String = day(year.toInt, month.toInt, 1, 31)
  def day(year: Int, month: Int): String = day(year, month, 1, 31)
  
  def day(year: Int, month: Int, min: Int, max: Int): String = {
    if ((min <= 0 || max > 31) || (min >= 31 || max <= 0)) throw new IllegalArgumentException("min and max values should be in [1,31] range")
    var result = ""
    var dayValue = 0
    while (result.eq("")) {
      try {
        dayValue = alpha.getInteger(min, max)
        new DateTime(year, month, dayValue, 0, 0)
        result = dayValue.toString
      } catch {
        case e: IllegalFieldValueException => this.day(year, month)
      }
    }
    result
  }
  
  def daysRange(year: Int, month: Int, min: Int, max: Int, step: Int): List[String] = {
    if ((min <= 0 || max > 31) || (min >= 31 || max <= 0)) throw new IllegalArgumentException("min and max values should be in [1,31] range")
    var day = min
    var resultList = ListBuffer[Int]()
    while (day <= max) {
      if (isValidDay(year, month, day)) {
        resultList.+=(day)
        day = day +  step
      }else day = day +  step
      
    }
    resultList.map(day => day.toString).toList
  }
  
  def daysRangeAsJavaList(year: Int, month: Int, min: Int, max: Int, step: Int) = daysRange(year, month, min, max, step).asJava
  
  private def isValidDay(year: Int, month: Int, day: Int) : Boolean = {
    try {
      new DateTime(year, month, day, 0, 0)
      true
    }catch {
      case e: IllegalFieldValueException => false
    }
  }

  def time24h: String = hour24h + ":" + minute

  def time12h: String = hour12h + ":" + minute

  def date: String = date("dd-MM-yyyy")

  def date(format: String): String = {
    val randomYear = year.toInt
    val randomMonth = month.toInt
    new DateTime(randomYear, randomMonth, day(randomYear, randomMonth).toInt, hour24h.toInt, minute.toInt, second.toInt).toString(format)
  }

  def dateObject: DateTime = new DateTime(year.toInt, month.toInt, 1, hour24h.toInt, minute.toInt, second.toInt).plusDays(alpha.getInteger(1, 31))

  def second: String = alpha.getInteger(0, 59).toString

  def minute: String = alpha.getInteger(0, 59).toString

  def hour24h : String = alpha.getInteger(0, 24).toString

  def hour12h: String = alpha.getInteger(0, 12).toString

  def month: String = month(numberFormat = true)

  def month(numberFormat: Boolean): String = {
    if (numberFormat) alpha.getInteger(1, 12).toString
    else utility.getValueFromArray("month")
  }

  def year: String = alpha.getInteger(1970, 2015).toString

  def date(year: Int, month: Int, day: Int, hour: Int, minute: Int): String = {
    var date = ""
    try {
      while (date.equals("")) {
        date = new DateTime(year, month, day, hour, minute).toString("dd-MM-yyyy hh:mm")
      }
    } catch {
      case e: IllegalFieldValueException => return this.date(year, month, day - 1, hour, minute)
    }
    date
  }

  def datesRange(startYear: Int, startMonth: Int, startDay: Int, endYear: Int, endMonth: Int, endDay: Int, stepType: String, step: Int): List[String] = {
    datesRange(startYear, startMonth, startDay, endYear, endMonth, endDay, stepType, step, "dd-MM-yyyy")
  }

  def datesRangeJavaList(startYear: Int, startMonth: Int, startDay: Int, endYear: Int, endMonth: Int, endDay: Int, stepType: String, step: Int) = {
    datesRange(startYear, startMonth, startDay, endYear, endMonth, endDay, stepType, step, "dd-MM-yyyy").asJava
  }
  
  def datesRange(startYear: Int, startMonth: Int, startDay: Int, endYear: Int, endMonth: Int, endDay: Int, stepType: String, step: Int, format: String): List[String] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be > 0")
    var startDate = new DateTime(startYear, startMonth, startDay, 0, 0)
    val endDate = new DateTime(endYear, endMonth, endDay, 0, 0)
    var rangeList = ListBuffer[String]()
    rangeList += startDate.toString(format)
    while(startDate.compareTo(endDate) == -1) {
      stepType match {
        case "year" => startDate = startDate.plusYears(step); if (startDate.compareTo(endDate) == -1) rangeList += startDate.toString(format)
        case "month" => startDate = startDate.plusMonths(step);  if (startDate.compareTo(endDate) == -1) rangeList += startDate.toString(format)
        case "day" => startDate = startDate.plusDays(step);   if (startDate.compareTo(endDate) == -1) rangeList += startDate.toString(format)
        case _ => throw new IllegalArgumentException(stepType + " stepType is not supported. Only year, month, dat are supported")
      }  
    }
    rangeList.toList
  }
  
  def datesRange(config: JsValue): List[String] = {
    // checking start section
    var start: JsValue = getJsValue(config, "start")
    if (start == JsNull)  {
      val date = new DateTime()
      val year = date.getYear
      val month = date.getMonthOfYear
      val day = date.getDayOfMonth
      val hour = date.getHourOfDay
      val minute = date.getMinuteOfHour
      start = Json.parse(s"""{"year": $year, "month": $month , "day" : $day, "hour": $hour, "minute": $minute}""".stripMargin).asOpt[JsValue].get
    }
    // checking end section, step and format. end section and step are mandatory
    val end: JsValue = getJsValue(config, "end")
    val format = if ((config \ "format").asOpt[String] == None) "dd-MM-yyyy hh:mm" else (config \ "format").asOpt[String].get
    val step:JsValue = getJsValue(config, "step")

    if (end == JsNull) throw new IllegalArgumentException("End section is not specified")
    if (step == JsNull) throw new IllegalArgumentException("Step section is not specified")
    // internal methods for reading json
    def getValue(json: JsValue, key: String): Int = 
      if ((json \ key).asOpt[Int] == None) 1
      else (json \ key).asOpt[Int].get

    def getStepValue(json: JsValue, key: String): Int = {
      if ((json \ key).asOpt[Int] == None) 0
      else (json \ key).asOpt[Int].get
    }
    // setting step values and making sure that at least of them is > 0
    
    val yearStep = getStepValue(step, "year")
    val monthStep = getStepValue(step, "month")
    val dayStep = getStepValue(step, "day")
    val hourStep = getStepValue(step, "hour")
    val minuteStep = getStepValue(step, "minute")
    if (yearStep == 0 && monthStep == 0 && dayStep == 0 && hourStep == 0 && minuteStep == 0 ) throw new IllegalArgumentException("At least one step parameter should be > 0 ")
    // setting start and end dates basing on params we have
    var startDate = new DateTime(getValue(start, "year"),
                                 getValue(start, "month"),
                                 getValue(start, "day"),
                                 getValue(start, "hour"),
                                 getValue(start, "minute"))
    val endDate =   new DateTime(getValue(end, "year"),
                                 getValue(end, "month"),
                                 getValue(end, "day"),
                                 getValue(end, "hour"),
                                 getValue(end, "minute"))
    var datesList = new ListBuffer[String]()
    datesList += startDate.toString(format)
    // while start date is < end date, generate next date with step and put it into the resulting list
    while(startDate.compareTo(endDate) == -1) {
      startDate = startDate.plusYears(yearStep).plusMonths(monthStep).plusDays(dayStep).plusHours(hourStep).plusMinutes(minuteStep)
      datesList += startDate.toString(format)
    }
    datesList.toList
  }

  private def getJsValue(config: JsValue, key: String): JsValue = {
    if ((config \ key).asOpt[JsValue] == None) JsNull else (config \ key).asOpt[JsValue].get
  }

  def datesRange(config: String): List[String] = { val jsonConfig: JsValue = Json.parse(config); datesRange(jsonConfig)}

  def datesRangeJavaList(config: String) = datesRange(config).asJava

  def date(year: Int, month: Int, day: Int, hour: Int, minute: Int, defFormat: String): String = {
    new DateTime(year, month, day, hour, minute).toString(defFormat)
  }

  def dateRelative(years: Int, months: Int, weeks: Int, days: Int, hours: Int, minutes: Int): String = {
    dateRelative(years, months, weeks, days, hours, minutes, "dd-MM-yyyy hh:mm")
  }

  def dateRelative(years: Int, months: Int, weeks: Int, days: Int, hours: Int, minutes: Int, format: String): String = {
    dateRelative(DateTime.now, years, months, weeks, days, hours, minutes, format)
  }

  def dateRelative(date: DateTime, years: Int, months: Int, weeks: Int, days: Int, hours: Int, minutes: Int, format: String): String = {
    var finalDate = date
    if (years > 0) {
      finalDate = finalDate.plusYears(years)
    }
    if (years < 0) {
      finalDate = finalDate.minusYears(Math.abs(years))
    }
    if (months > 0) {
      finalDate = finalDate.plusMonths(months)
    }
    if (months < 0) {
      finalDate = finalDate.minusMonths(Math.abs(months))
    }
    if (weeks > 0) {
      finalDate = finalDate.plusWeeks(weeks)
    }
    if (weeks < 0) {
      finalDate = finalDate.minusWeeks(Math.abs(weeks))
    }
    if (days > 0) {
      finalDate = finalDate.plusDays(days)
    }
    if (days < 0) {
      finalDate = finalDate.minusDays(Math.abs(days))
    }
    if (hours > 0) {
      finalDate = finalDate.plusHours(hours)
    }
    if (hours < 0) {
      finalDate = finalDate.minusHours(Math.abs(hours))
    }
    if (minutes > 0) {
      finalDate = finalDate.plusMinutes(minutes)
    }
    if (minutes < 0) {
      finalDate = finalDate.minusMinutes(Math.abs(minutes))
    }
    finalDate.toString(format)
  }


}
