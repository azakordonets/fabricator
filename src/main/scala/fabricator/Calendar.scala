package fabricator

import com.github.nscala_time.time.Imports._

/**
 * Created by Andrew Zakordonets on 03/06/14.
 */
protected class Calendar extends Fabricator{

  def date(format:String = "dd-mm-yyyy"): String = {
    DateTime.now.toString(format)
  }

//  def date(dayOfTheYear:Int = 0, monthOfTheYear:Int = 0, yearVal:Int = 0, hour:Int = 0, minute:Int = 0, defFormat: String = "dd-mm-yyyy"): String = {
//    new DateTime(yearVal, monthOfTheYear, dayOfTheYear, hour, minute).toString(defFormat)
//  }

  def checkDay() = {

  }

  def leapYear(year: Int):Boolean = {
    0 == year % 4 && 0 != year % 100 || 0 == year % 400
  }

  def checkYear(year: Int):Boolean = year match{
    case year if ((1900 to 3000).contains(year))  => true
    case _ => false
  }


}
