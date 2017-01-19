package fabricator.entities

import fabricator.Calendar
import fabricator.enums.DateFormat
import org.joda.time.{DateTime, IllegalFieldValueException}

class RandomDate(private val cal: Calendar) {

  private var year: Int = cal.year.toInt

  private var month: Int = cal.month(asNumber = true).toInt

  private var day: Int = cal.day(year, month).toInt

  private var hour: Int = cal.hour24h.toInt

  private var minute: Int = cal.minute.toInt

  def inYear(year: Int): this.type = {
    this.year = year
    this.day = cal.day(year, this.month).toInt
    this
  }

  def inMonth(month: Int): this.type = {
    this.month = month
    this.day = cal.day(this.year, month).toInt
    this
  }


  def inDay(day: Int): this.type = {
    this.day = validateDay(day)
    this
  }

  private def isValidDay(year: Int, month: Int, day: Int): Boolean = {
    try {
      new DateTime(year, month, day, 0, 0)
      true
    } catch {
      case e: IllegalFieldValueException => false
    }
  }

  private def validateDay(day: Int): Int = {
    var dayOfMonth = day
    while (!isValidDay(year, month, dayOfMonth)) {
      dayOfMonth = dayOfMonth - 1
    }
    dayOfMonth
  }

  def inHour(hour: Int): this.type = {
    this.hour = hour
    this
  }

  def inMinute(minute: Int): this.type = {
    this.minute = minute
    this
  }

  def inTime(hour: Int, minute: Int): this.type = {
    this.hour = hour
    this.minute = minute
    this
  }

  private def makeDate: DateTime = {
    new DateTime(year, month, day, hour, minute)
  }

  def asDate: DateTime = {
    makeDate
  }

  def asString: String = {
    asString(DateFormat.dd_MM_yyyy)
  }

  def asString(format: DateFormat): String = {
    val date = makeDate
    date.toString(format.getFormat)
  }

}
