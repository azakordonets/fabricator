package fabricator

import scala.util.Random

class CreditCard {

  val visaPrefixList = Array("4539", "4556", "4916", "4532", "4929", "40240071", "4485", "4716", "4")
  val masterCardPrefixList = Array("51", "52", "53", "54", "55")
  val amexPrefixList = Array("34", "37")
  val discoverPrefixList = Array("6011")
  val dinersPrefixList = Array("300", "301", "302", "303", "36", "38")
  val enroutePrefixList = Array("2014", "2149")
  val jcbPrefixList = Array("35")
  val voyagerPrefixList = Array("8699")

  def createCreditCardNumber(prefixList: Array[String], length: Int, howMany: Int): Array[String] = {
    Array.fill(howMany)(createCreditCardNumber(prefixList, length))
  }

  def createCreditCardNumber(prefixes: Array[String], length: Int): String = {
    completed_number(Random.shuffle(prefixes.toList).head, length)
  }

  /*
   * 'prefix' is the start of the CC number as a string, any number of digits.
   * 'length' is the length of the CC number to generate. Typically 13 or 16
   */
  private def completed_number(prefix: String, length: Int): String = {
    def luhn(str: String): Int = {
      val sum = str.sliding(1, 2).map(value => {
        val doubled = value.toInt * 2
        if (doubled > 9) doubled - 9
        else doubled
      }).sum
      (((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue
    }
    val number = prefix + List.fill(length - prefix.length - 1)(Random.nextInt(10)).mkString
    val checkdigit = luhn(number)
    val cc = number + checkdigit
    isValidCreditCardNumber(cc)
    cc
  }

  private def isValidCreditCardNumber(creditCardNumber: String): Boolean = {
    var isValid = false

    try {
      val reversedNumber = new StringBuffer(creditCardNumber).reverse().toString()
      var mod10Count = 0
      for ( i <- 0 to reversedNumber.length - 1 ) {
        var augend = Integer.parseInt(String.valueOf(reversedNumber.charAt(i)))
        if (((i + 1) % 2) == 0) {
          val productString: String = String.valueOf(augend * 2)
          augend = 0
          for ( j <- 0 to productString.length - 1 ) {
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

    isValid
  }
}
