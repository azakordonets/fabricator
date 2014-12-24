package fabricator

import org.joda.time.{IllegalFieldValueException, DateTime}

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
    while(result.eq("")){
      try{
        dayValue = alpha.integer(1, 31)
        new DateTime(year, month, dayValue, 0, 0)
        result = dayValue.toString
      }catch {
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
    new DateTime(year().toInt, month().toInt, 1, hour().toInt, minute().toInt, second().toInt).plusDays(alpha.integer(1,31))
  }

  def date(year: Int, month: Int, day: Int, hour: Int, minute: Int): String = {
    var date = ""
    try{
      while(date.equals("")) {
          date = new DateTime(year, month, day, hour, minute).toString("dd-MM-yyyy hh:mm")
      }
    }catch {
      case e: IllegalFieldValueException => return this.date(year, month, day-1, hour, minute)
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
    (years, months, weeks, days, hours, minutes) match {
      case (0 , 0, 0, 0, 0, 0)  => date.toString(format)
      case (years , 0, 0, 0, 0, 0) if years > 0 => date.plusYears(years).toString(format)
      case (years , 0, 0, 0, 0, 0) if years < 0=> date.minusYears(Math.abs(years)).toString(format)
      case (0 , months, 0, 0, 0, 0) if months > 0 => date.plusMonths(months).toString(format)
      case (0 , months, 0, 0, 0, 0) if months < 0=> date.minusMonths(Math.abs(months)).toString(format)
      case (0 , 0, weeks, 0, 0, 0) if weeks > 0 => date.plusWeeks(weeks).toString(format)
      case (0 , 0, weeks, 0, 0, 0) if weeks < 0=> date.minusWeeks(Math.abs(weeks)).toString(format)
      case (0 , 0, 0, days, 0, 0) if days > 0 => date.plusDays(days).toString(format)
      case (0 , 0, 0, days, 0, 0) if days < 0=> date.minusDays(Math.abs(days)).toString(format)
      case (0 , 0, 0, 0, hours, 0) if hours > 0 => date.plusHours(hours).toString(format)
      case (0 , 0, 0, 0, hours, 0) if hours < 0=> date.minusHours(Math.abs(hours)).toString(format)
      case (0 , 0, 0, 0, 0, minutes) if minutes > 0 => date.plusMinutes(minutes).toString(format)
      case (0 , 0, 0, 0, 0, minutes) if minutes < 0=> date.minusMinutes(Math.abs(minutes)).toString(format)
      case (years , months, 0, 0, 0, 0) if years > 0 && months > 0 => date.plusYears(years).plusMonths(months).toString(format)
      case (years , months, 0, 0, 0, 0) if years < 0 && months < 0 => date.minusYears(Math.abs(years)).minusMonths(Math.abs(months)).toString(format)
      case (years , months, weeks, 0, 0, 0) if years > 0 && months > 0 && weeks > 0 => date.plusYears(years).plusMonths(months).plusWeeks(weeks).toString(format)
      case (years , months, weeks, 0, 0, 0) if years < 0 && months < 0 && weeks < 0 => date.minusYears(Math.abs(years)).minusMonths(Math.abs(months)).minusWeeks(Math.abs(weeks)).toString(format)
      case (years , months, weeks, days, 0, 0) if years > 0 && months > 0 && weeks > 0 && days > 0=> date.plusYears(years).plusMonths(months).plusWeeks(weeks).plusDays(days).toString(format)
      case (years , months, weeks, days, 0, 0) if years < 0 && months < 0 && weeks < 0 && days < 0 => date.minusYears(Math.abs(years)).minusMonths(Math.abs(months)).minusWeeks(Math.abs(weeks)).minusDays(Math.abs(days)).toString(format)
      case (years , months, weeks, days, hours, 0) if years > 0 && months > 0 && weeks > 0 && days > 0 && hours > 0 => date.plusYears(years).plusMonths(months).plusWeeks(weeks).plusDays(days).plusHours(hours).toString(format)
      case (years , months, weeks, days, hours, 0) if years < 0 && months < 0 && weeks < 0 && days < 0 && hours < 0 => date.minusYears(Math.abs(years)).minusMonths(Math.abs(months)).minusWeeks(Math.abs(weeks)).minusDays(Math.abs(days)).minusHours(Math.abs(hours)).toString(format)
      case (years , months, weeks, days, hours, minutes) if years > 0 && months > 0 && weeks > 0 && days > 0 && hours > 0 && minutes > 0 => date.plusYears(years).plusMonths(months).plusWeeks(weeks).plusDays(days).plusHours(hours).plusMinutes(minutes).toString(format)
      case (years , months, weeks, days, hours, minutes) if years < 0 && months < 0 && weeks < 0 && days < 0 && hours < 0 && minutes < 0=> date.minusYears(Math.abs(years)).minusMonths(Math.abs(months)).minusWeeks(Math.abs(weeks)).minusDays(Math.abs(days)).minusHours(Math.abs(hours)).minusMinutes(Math.abs(minutes)).toString(format)
      case _ => throw new IllegalArgumentException ("Invalid format of values. Values can be either 0, or > 0, or < 0. You're not allowed to enter +3 days - 2 hours. ")
    }

  }



}
