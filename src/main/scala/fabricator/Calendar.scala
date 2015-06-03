package fabricator

import org.joda.time.{DateTime, IllegalFieldValueException}

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

  def month: String = month(asNumber = true)

  def month(asNumber: Boolean): String = {
    if (asNumber) alpha.getInteger(1, 12).toString
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

  def datesRange: DateRange = new DateRange

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
