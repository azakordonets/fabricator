package fabricator

import org.joda.time.{DateTime, IllegalFieldValueException}

import scala.util.Random

/**
 * Created by Andrew Zakordonets on 03/06/14.
 */
class Calendar(private val utility: UtilityService,
               private val random: Random,
               private val alpha: Alphanumeric,
               private val creditCard: CreditCard) {

  def this() {
    this(new UtilityService(), new Random(), new Alphanumeric(), new CreditCard)
  }


  def ampm(): String = {
    val randomVal = alpha.integer(0, 10)
    if (randomVal < 5) "am" else "pm"
  }

  def second(): String = {
    alpha.integer(0, 59).toString
  }

  def minute(): String = {
    alpha.integer(0, 59).toString
  }

  def hour(): String = {
    hour(false)
  }

  def hour(twentyfour: Boolean): String = {
    if (twentyfour) alpha.integer(0, 24).toString
    else alpha.integer(1, 12).toString
  }

  def day(year: Int, month: Int): String = {
    var result = ""
    var dayValue = 0
    while (result.eq("")) {
      try {
        dayValue = alpha.integer(1, 31)
        new DateTime(year, month, dayValue, 0, 0)
        result = dayValue.toString
      } catch {
        case e: IllegalFieldValueException => this.day(year, month)
      }
    }
    result
  }

  def month(): String = {
    month(true)
  }

  def month(numberFormat: Boolean): String = {
    if (numberFormat) alpha.integer(1, 12).toString
    else utility.getValueFromArray("month")
  }

  def year(): String = {
    alpha.integer(1970, 2015).toString
  }

  def date(): String = {
    date("dd-MM-yyyy")
  }

  def date(format: String): String = {
    val randomYear = year().toInt
    val randomMonth = month().toInt
    new DateTime(randomYear, randomMonth, day(randomYear, randomMonth).toInt, hour().toInt, minute().toInt, second().toInt).toString(format)
  }

  def dateObject(): DateTime = {
    new DateTime(year().toInt, month().toInt, 1, hour().toInt, minute().toInt, second().toInt).plusDays(alpha.integer(1, 31))
  }

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

  def date(year: Int, month: Int, day: Int, hour: Int, minute: Int, defFormat: String): String = {
    new DateTime(day, month, year, hour, minute).toString(defFormat)
  }

  def dateWithPeriod(years: Int, months: Int, weeks: Int, days: Int, hours: Int, minutes: Int): String = {
    dateWithPeriod(years, months, weeks, days, hours, minutes, "dd-MM-yyyy hh:mm")
  }

  def dateWithPeriod(years: Int, months: Int, weeks: Int, days: Int, hours: Int, minutes: Int, format: String): String = {
    dateWithPeriod(DateTime.now(), years, months, weeks, days, hours, minutes, format)
  }

  def dateWithPeriod(date: DateTime, years: Int, months: Int, weeks: Int, days: Int, hours: Int, minutes: Int, format: String): String = {
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
