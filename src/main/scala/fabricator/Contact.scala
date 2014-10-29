package fabricator

import com.github.nscala_time.time.Imports._

import scala.util.Random


/**
  * Created by Andrew Zakordonets on 02/06/14.
 */
class Contact( private val utility:UtilityService,
               private val alpha: Alphanumeric,
               private val random: Random)  {


  def this () = {
    this( new UtilityService(), new Alphanumeric, new Random());

  }



  def firstName():String =  {
    utility.getValueFromArray("first_name")
  }

  def lastName():String = {
    utility.getValueFromArray("last_name")
  }

  def fullName(prefix:Boolean = false ):String = {
    if (prefix)  prefix + " "+ firstName() + " " + lastName()
    else firstName() + " " + lastName()
  }

  def birthday(age: Int): String = {
    DateTimeFormat.forPattern("dd-MM-yyyy").print(DateTime.now - age.years)
  }

  def birthday(age: Int, format: String ): String = {
    DateTimeFormat.forPattern(format).print(DateTime.now - age.years)
  }

  def eMail():String = {
    firstName() + "_" + lastName() +  alpha.numerify("###") + "@" + utility.getValueFromArray("free_email")
  }

  def phoneNumber() = {
    alpha.numerify(utility.getValueFromArray("phone_formats"))
  }

  def streetName() = {
    utility.getValueFromArray("street_suffix")
  }

  def houseNumber() = {
    alpha.numerify(utility.getValueFromArray("house_number"))
  }

  def appartmentNumber() = {
    alpha.numerify(utility.getValueFromArray("app_number"))
  }

  def postcode() = {
    alpha.botify(utility.getValueFromArray("postcode"))
  }

  def state() = {
    utility.getValueFromArray("state")
  }

  def stateShortCode() = {
    utility.getValueFromArray("state_abbr")
  }

  def company() = {
    utility.getValueFromArray("company_suffix")
  }

  def bsn(): String = {
    val bsn: Array[Int]  = new Array[Int](9);
    bsn(0) = random.nextInt(9) + 1;
    for (i <- 1 to 8) {
      bsn(i) = random.nextInt(10);
    }
    bsn(8) = 0;
    if (bsn(0) + bsn(1) + bsn(2) == 0) {
      bsn(1) = 1;
    }
    var sum = 0;
    for ( i <- 1 to 8) {
      sum += bsn(i) * (9 - i);
    }
    bsn(8) = sum % 11;
    if (bsn(8) > 9) {
      if (bsn(7) > 0) {
        bsn(7) -= 1;
        bsn(8) = 8;
      } else {
        bsn(7) += 1;
        bsn(8) = 1;
      }
    }
    var result: String = ""
    for (i <- 1 to 8 ) {
      result += bsn(i);
    }
    return result;

  }

}
