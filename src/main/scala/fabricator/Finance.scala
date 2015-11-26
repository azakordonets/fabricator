package fabricator

import org.iban4j.{CountryCode, Iban}

import scala.util.Random

//  /**
//   * See the license below. Obviously, this is not a Javascript credit card number
//   * generator. However, The following class is a port of a Javascript credit card
//   * number generator.
//   *
//   * @author robweber
//   *
//   */
//  public class RandomcreditCardNumberGenerator {
//    /*
//     * Javascript credit card number generator Copyright (C) 2006-2012 Graham King
//     *
//     * This program is free software you can redistribute it and/or modify it
//     * under the terms of the GNU General Public License as published by the
//     * Free Software Foundation either version 2 of the License, or (at your
//     * option) any later version.
//     *
//     * This program is distributed in the hope that it will be useful, but
//     * WITHOUT ANY WARRANTY without even the implied warranty of
//     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
//     * Public License for more details.
//     *
//     * You should have received a copy of the GNU General Public License along
//     * with this program if not, write to the Free Software Foundation, Inc.,
//     * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
//     *
//     * www.darkcoding.net
//     */
object Finance {

  def apply(): Finance = new Finance(UtilityService(), new Random(), Alphanumeric())

  def apply(locale: String): Finance = new Finance(UtilityService(locale), new Random(), Alphanumeric())

}

class Finance private(private val utility: UtilityService,
              private val random: Random,
              private val alpha: Alphanumeric) {

  def iban: String = {
    new Iban.Builder().
      countryCode(CountryCode.valueOf(utility.getValueFromArray("countryCode"))).
      bankCode(utility.getValueFromArray("bic").substring(0, 4)).
      branchCode(alpha.numerify(utility.getValueFromArray("branchCode"))).
      accountNumber(alpha.numerify(utility.getValueFromArray("accountNumber"))).build.toString

  }

  def bic: String = alpha.botify(utility.getValueFromArray("bic").toUpperCase)

  def ssn: String = alpha.botify(utility.getValueFromArray("ssn"))

  def masterCard: String = CreditCard.generateNumber(CreditCard.masterCardPrefixList, 16, 1)(0)

  def masterCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.masterCardPrefixList, 16, howMany)

  def visaCard: String = CreditCard.generateNumber(CreditCard.visaPrefixList, 16, 1)(0)

  def visaCard(cardNumberLength: Int): String = CreditCard.generateNumber(CreditCard.visaPrefixList, cardNumberLength, 1)(0)
  
  def visaCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.visaPrefixList, 16, howMany)

  def visaCards(howMany: Int, cardNumberLength: Int): Array[String] = CreditCard.generateNumber(CreditCard.visaPrefixList, cardNumberLength, howMany)

  def americanExpressCard: String = CreditCard.generateNumber(CreditCard.amexPrefixList, 16, 1)(0)

  def americanExpressCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.amexPrefixList, 16, howMany)

  def discoverCard: String = CreditCard.generateNumber(CreditCard.discoverPrefixList, 16, 1)(0)

  def discoverCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.discoverPrefixList, 16, howMany)
  
  def dinersCard: String = CreditCard.generateNumber(CreditCard.dinersPrefixList, 16, 1)(0)

  def dinersCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.dinersPrefixList, 16, howMany)

  def jcbCard: String = CreditCard.generateNumber(CreditCard.jcbPrefixList, 16, 1)(0)

  def jcbCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.jcbPrefixList, 16, howMany)

  def voyagerCard: String = CreditCard.generateNumber(CreditCard.jcbPrefixList, 15, 1)(0)

  def voyagerCards(howMany: Int): Array[String] = CreditCard.generateNumber(CreditCard.jcbPrefixList, 15, howMany)

  def pinCode: String = alpha.numerify("####")

  private object CreditCard {

    val visaPrefixList = Array("4539", "4556", "4916", "4532", "4929", "40240071", "4485", "4716", "4")
    val masterCardPrefixList = Array("51", "52", "53", "54", "55")
    val amexPrefixList = Array("34", "37")
    val discoverPrefixList = Array("6011")
    val dinersPrefixList = Array("300", "301", "302", "303", "36", "38")
    val enroutePrefixList = Array("2014", "2149")
    val jcbPrefixList = Array("35")
    val voyagerPrefixList = Array("8699")

    def generateNumber(prefixList: Array[String], length: Int, howMany: Int): Array[String] = {
      Array.fill(howMany)(generateNumber(prefixList, length))
    }

    def generateNumber(prefixes: Array[String], length: Int): String = {
      completedNumber(Random.shuffle(prefixes.toList).head, length)
    }

    /*
     * 'prefix' is the start of the CC number as a string, any number of digits.
     * 'length' is the length of the CC number to generate. Typically 13 or 16
     */
    private def completedNumber(prefix: String, length: Int): String = {
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
      isValidNumber(cc)
      cc
    }

    private def isValidNumber(cardNumber: String): Boolean = {
      var isValid = false

      try {
        val reversedNumber = cardNumber.reverse
        var mod10Count = 0
        for (i <- 0 to reversedNumber.length - 1) {
          var augend = reversedNumber.charAt(i).toString.toInt
          if (((i + 1) % 2) == 0) {
            val productString = String.valueOf(augend * 2)
            augend = 0
            for (j <- 0 to productString.length - 1) {
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
}
