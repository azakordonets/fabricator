package fabricator.entities

import org.joda.time.{DateTime, DateTimeZone}

import fabricator.enums.DateFormat

class RelativeDate(private val initialDate: DateTime = DateTime.now()) {

  def this(timeZone: DateTimeZone) = {
    this(DateTime.now(timeZone))
  }

  private var date = initialDate

  def tomorrow(): this.type = {
    date = date.plusDays(1)
    this
  }

  def yesterday(): this.type = {
    date = date.minusDays(1)
    this
  }

  def years(years: Int): this.type = {
    years match {
      case `years` if years > 0 => date = date.plusYears(years)
      case `years` if years < 0 => date = date.minusYears(Math.abs(years))
      case _                    => // skip
    }
    this
  }

  def months(months: Int): this.type = {
    months match {
      case `months` if months > 0 => date = date.plusMonths(months)
      case `months` if months < 0 => date = date.minusMonths(Math.abs(months))
      case _                      => // skip
    }
    this
  }

  def weeks(weeks: Int): this.type = {
    weeks match {
      case `weeks` if weeks > 0 => date = date.plusWeeks(weeks)
      case `weeks` if weeks < 0 => date = date.minusWeeks(Math.abs(weeks))
      case _                    => // skip
    }
    this
  }

  def days(days: Int): this.type = {
    days match {
      case `days` if days > 0 => date = date.plusDays(days)
      case `days` if days < 0 => date = date.minusDays(Math.abs(days))
      case _                  => // skip
    }
    this
  }

  def hours(hours: Int): this.type = {
    hours match {
      case `hours` if hours > 0 => date = date.plusHours(hours)
      case `hours` if hours < 0 => date = date.minusHours(Math.abs(hours))
      case _                    => // skip
    }
    this
  }

  def minutes(minutes: Int): this.type = {
    minutes match {
      case `minutes` if minutes > 0 => date = date.plusMinutes(minutes)
      case `minutes` if minutes < 0 => date = date.minusMinutes(Math.abs(minutes))
      case _                        => // skip
    }
    this
  }

  def seconds(seconds: Int): this.type = {
    seconds match {
      case `seconds` if seconds > 0 => date = date.plusSeconds(seconds)
      case `seconds` if seconds < 0 => date = date.minusSeconds(Math.abs(seconds))
      case _                        => // skip
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
