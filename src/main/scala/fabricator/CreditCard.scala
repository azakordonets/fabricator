package fabricator

import scala.collection.immutable.VectorBuilder
import scala.collection.mutable.Stack

class CreditCard {
  val visaPrefixList = Array("4539", "4556", "4916", "4532", "4929", "40240071", "4485", "4716", "4")

  val masterCardPrefixList = Array("51", "52", "53", "54", "55")

  val amexPrefixList = Array("34", "37")

  val discoverPrefixList = Array("6011")

  val dinersPrefixList = Array("300", "301", "302", "303", "36", "38")

  val enroutePrefixList = Array("2014", "2149")

  val jcbPrefixList = Array("35")

  val voyagerPrefixList = Array("8699")

  private def strrev(str: String): String = {
    if (str == null)
      return ""
    var revstr = ""
    for (i <- str.length -1  to 0 by -1) {
      revstr += str.charAt(i)
    }
    return revstr
  }


  /*
   * 'prefix' is the start of the CC number as a string, any number of digits.
   * 'length' is the length of the CC number to generate. Typically 13 or 16
   */
  private def completed_number(prefix: String, length: Int): String = {

    var ccnumber = prefix

    // generate digits

    while (ccnumber.length() < (length - 1)) {
      ccnumber = ccnumber + (Math.floor(Math.random() * 10).toInt)
    }

    // reverse number and convert to int

    var reversedCCnumberString = strrev(ccnumber)

    var builder: VectorBuilder[Int] = new VectorBuilder[Int]
    for (i <- 0 to reversedCCnumberString.length-1) {
      if (reversedCCnumberString.charAt(i) != 46) {
        builder.+=(reversedCCnumberString.charAt(i).asDigit)
      }
    }
    var reversedCCnumberList: Vector[Int] = builder.result()


    // calculate sum

    var sum = 0
    var pos = 0

    val reversedCCnumber: Array[Int] = (reversedCCnumberList map (_.toInt)).toArray
    while (pos < length - 1) {

      var odd = reversedCCnumber(pos) * 2
      if (odd > 9) {
        odd -= 9
      }

      sum = sum + odd

      if (pos != (length - 2)) {
        sum = sum + reversedCCnumber(pos + 1)
      }
      pos += 2
    }

    // calculate check digit

    var checkdigit = ((((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue())
    ccnumber += checkdigit
    if (isValidCreditCardNumber(ccnumber)) return ccnumber
    else throw new Exception ("Credit card number "+ccnumber + "is not valid")

  }

  def createCreditCardNumber(prefixList: Array[String], length: Int, howMany: Int): Array[String] = {

    val result: Stack[String] = Stack()
    for (i <- 0 to howMany) {
      var randomArrayIndex: Int = Math.floor(Math.random() * prefixList.length).toInt
      var ccnumber: String = prefixList(randomArrayIndex)
      result.push(completed_number(ccnumber, length))
    }

    return result.toArray
  }

  private def isValidCreditCardNumber(creditCardNumber: String): Boolean = {
    var isValid = false

    try {
      var reversedNumber = new StringBuffer(creditCardNumber).reverse().toString()
      var mod10Count = 0
      for (i <- 0 to reversedNumber.length-1) {
        var augend = Integer.parseInt(String.valueOf(reversedNumber.charAt(i)))
        if (((i + 1) % 2) == 0) {
          var productString: String = String.valueOf(augend * 2)
          augend = 0
          for (j <- 0 to productString.length -1) {
            augend += Integer.parseInt(String.valueOf(productString.charAt(j)))
          }
        }

        mod10Count += augend
      }

      if ((mod10Count % 10) == 0) {
        isValid = true
      }
    } catch {
      case e: NumberFormatException =>
    }

    return isValid
  }


}
