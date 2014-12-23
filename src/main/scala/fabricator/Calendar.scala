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

  def day(): String = {
    alpha.integer(1, 31).toString
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
    new DateTime(year().toInt, month().toInt, day().toInt, hour().toInt, minute().toInt, second().toInt).toString(format)
  }

  def dateObject(): DateTime = {
    new DateTime(year().toInt, month().toInt, 1, hour().toInt, minute().toInt, second().toInt).plusDays(alpha.integer(1,31))
  }

  def date(year: Int, month: Int, day: Int, hour: Int, minute: Int): String = {
    var date = ""
    try{
      while(date == "") {
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
    val date = DateTime.now()
//    date.en
    (years, months, weeks, days, hours, minutes) match {
      case (0 , 0, 0, 0, 0, 0) if years > 0 => date.formatted(format)
      case (years , 0, 0, 0, 0, 0) if years > 0 => date.plusYears(years).formatted(format)
      case (years , 0, 0, 0, 0, 0) if years < 0=> date.minusYears(years).formatted(format)
      case (0 , months, 0, 0, 0, 0) if months > 0 => date.plusMonths(months).formatted(format)
      case (0 , months, 0, 0, 0, 0) if months < 0=> date.minusMonths(months).formatted(format)
      case (0 , 0, weeks, 0, 0, 0) if weeks > 0 => date.plusWeeks(weeks).formatted(format)
      case (0 , 0, weeks, 0, 0, 0) if weeks < 0=> date.minusWeeks(weeks).formatted(format)
      case (0 , 0, 0, days, 0, 0) if days > 0 => date.plusDays(days).formatted(format)
      case (0 , 0, 0, days, 0, 0) if days < 0=> date.minusDays(days).formatted(format)
      case (0 , 0, 0, 0, hours, 0) if hours > 0 => date.plusHours(hours).formatted(format)
      case (0 , 0, 0, 0, hours, 0) if hours < 0=> date.minusHours(hours).formatted(format)
      case (0 , 0, 0, 0, 0, minutes) if minutes > 0 => date.plusMinutes(minutes).formatted(format)
      case (0 , 0, 0, 0, 0, minutes) if minutes < 0=> date.minusMinutes(minutes).formatted(format)
      case (years , months, 0, 0, 0, 0) if years > 0 && months > 0 => date.plusYears(years).plusMonths(months).formatted(format)
      case (years , months, 0, 0, 0, 0) if years < 0 && months < 0 => date.minusYears(years).minusMonths(months).formatted(format)
      case (years , months, weeks, 0, 0, 0) if years > 0 && months > 0 && weeks > 0 => date.plusYears(years).plusMonths(months).plusWeeks(weeks).formatted(format)
      case (years , months, weeks, 0, 0, 0) if years < 0 && months < 0 && weeks < 0 => date.minusYears(years).minusMonths(months).minusWeeks(weeks).formatted(format)
      case (years , months, weeks, days, 0, 0) if years > 0 && months > 0 && weeks > 0 && days > 0=> date.plusYears(years).plusMonths(months).plusWeeks(weeks).plusDays(days).formatted(format)
      case (years , months, weeks, days, 0, 0) if years < 0 && months < 0 && weeks < 0 && days < 0 => date.minusYears(years).minusMonths(months).minusWeeks(weeks).minusDays(days).formatted(format)
      case (years , months, weeks, days, hours, 0) if years > 0 && months > 0 && weeks > 0 && days > 0 && hours > 0 => date.plusYears(years).plusMonths(months).plusWeeks(weeks).plusDays(days).plusHours(hours).formatted(format)
      case (years , months, weeks, days, hours, 0) if years < 0 && months < 0 && weeks < 0 && days < 0 && hours < 0 => date.minusYears(years).minusMonths(months).minusWeeks(weeks).minusDays(days).minusHours(hours).formatted(format)
      case (years , months, weeks, days, hours, minutes) if years > 0 && months > 0 && weeks > 0 && days > 0 && hours > 0 && minutes > 0 => date.plusYears(years).plusMonths(months).plusWeeks(weeks).plusDays(days).plusHours(hours).plusMinutes(minutes).formatted(format)
      case (years , months, weeks, days, hours, minutes) if years < 0 && months < 0 && weeks < 0 && days < 0 && hours < 0 && minutes < 0=> date.minusYears(years).minusMonths(months).minusWeeks(weeks).minusDays(days).minusHours(hours).minusMinutes(minutes).formatted(format)
      case _ => throw new Exception ("Invalid format of values. Values can be either 0, or > 0, or < 0. You're not allowed to enter +3 days - 2 hours. ")
    }

  }


  def isLeapYear(year: Int): Boolean = {
    0 == year % 4 && 0 != year % 100 || 0 == year % 400
  }

  def checkYear(year: Int): Boolean = year match {
    case year if ((1900 to 3000).contains(year)) => true
    case _ => false
  }


}
