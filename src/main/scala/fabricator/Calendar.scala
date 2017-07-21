package fabricator

import fabricator.entities.{DateRange, RandomDate, RelativeDate}
import org.joda.time.{DateTime, DateTimeZone, IllegalFieldValueException}

import scala.util.Random

object Calendar {

  def apply(): Calendar = new Calendar(UtilityService(), new Random(), Alphanumeric())

  def apply(locale: String) = new Calendar(UtilityService(locale), new Random(), Alphanumeric())
}

class Calendar(private val utility: UtilityService,
               private val random: Random,
               private val alpha: Alphanumeric) {

  def ampm: String = {
    val randomVal = alpha.randomInt(0, 10)
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
        dayValue = alpha.randomInt(min, max)
        new DateTime(year, month, dayValue, 0, 0)
        result = dayValue.toString
      } catch {
        case _: IllegalFieldValueException => this.day(year, month)
      }
    }
    result
  }

  def dayOfWeek: String = utility.getValueFromArray("day_of_week")

  def time24h: String = hour24h + ":" + minute

  def time12h: String = hour12h + ":" + minute

  def randomDate: RandomDate = new RandomDate(this)

  def second: String = alpha.randomInt(0, 59).toString

  def minute: String = alpha.randomInt(0, 59).toString

  def hour24h : String = alpha.randomInt(0, 24).toString

  def hour12h: String = alpha.randomInt(0, 12).toString

  def month: String = month(asNumber = true)

  def month(asNumber: Boolean): String = {
    if (asNumber) alpha.randomInt(1, 12).toString
    else utility.getValueFromArray("month")
  }

  def year: String = alpha.randomInt(1970, 2015).toString

  def century: String = utility.getValueFromArray("centuries")

  def datesRange: DateRange = new DateRange

  def relativeDate: RelativeDate = new RelativeDate

  def relativeDate(initialDate: DateTime): RelativeDate = new RelativeDate(initialDate)

  def relativeDate(timeZone: DateTimeZone): RelativeDate = new RelativeDate(timeZone)

}
