package fabricator.entities

import fabricator.enums.DateFormat
import org.joda.time.{DateTime, DateTimeZone}

class RelativeDate {
  
  private var timeZone: DateTimeZone = null

  private var date: DateTime = null
  
  def apply() = {
    this.timeZone = DateTimeZone.getDefault
    this.date = DateTime.now(timeZone)
  }
  
  def apply(initialDate: DateTime) = {
    this.date = date
    this.timeZone = DateTimeZone.getDefault
  }

  def apply(timeZone: DateTimeZone) = {
    this.timeZone = timeZone
    this.date = DateTime.now(timeZone)
  }

  def apply(initialDate: DateTime, timeZone: DateTimeZone) = {
    this.date = initialDate
    this.timeZone = timeZone
  }

  def tomorrow() : this.type = {
    date = date.plusDays(1)
    this
  }

  def yesterday(): this.type = {
    date = date.minusDays(1)
    this
  }

  def years(years: Int): this.type = {
    if (years > 0 ) {
      date = date.plusYears(years)
    } else {
      date = date.minusYears(years)
    }
    this
  }

  def months(months: Int): this.type = {
    if (months > 0 ) {
      date = date.plusMonths(months)
    } else {
      date = date.minusMonths(months)
    }
    this
  }

  def weeks(weeks: Int): this.type = {
    if (weeks > 0 ) {
      date = date.plusWeeks(weeks)
    } else {
      date = date.minusWeeks(weeks)
    }
    this
  }

  def days(days: Int): this.type = {
    if (days > 0 ) {
      date = date.plusDays(days)
    } else {
      date = date.minusDays(days)
    }
    this
  }

  def hours(hours: Int): this.type = {
    if (hours > 0 ) {
      date = date.plusHours(hours)
    } else {
      date = date.minusHours(hours)
    }
    this
  }

  def minutes(minutes: Int): this.type = {
    if (minutes > 0 ) {
      date = date.plusMinutes(minutes)
    } else {
      date = date.minusMinutes(minutes)
    }
    this
  }

  def seconds(seconds: Int): this.type = {
    if (seconds > 0 ) {
      date = date.plusSeconds(seconds)
    } else {
      date = date.minusMinutes(seconds)
    }
    this
  }

  def asString(): String = {
    asString(DateFormat.dd_MM_yyyy)
  }

  def asString(format: DateFormat): String = {
    date.toString(format.getFormat)
  }

  def asDate(): DateTime = {
    date
  }

}
