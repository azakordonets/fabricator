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
class Finance(private val utility: UtilityService,
              private val random: Random,
              private val alpha: Alphanumeric) {

  def this() {
    this(new UtilityService(), new Random(), new Alphanumeric())
  }

  def iban(): String = {
    new Iban.Builder().
      countryCode(CountryCode.valueOf(utility.getValueFromArray("countryCode"))).
      bankCode(utility.getValueFromArray("bic").substring(0, 4)).
      branchCode(alpha.numerify(utility.getValueFromArray("branchCode"))).
      accountNumber(alpha.numerify(utility.getValueFromArray("accountNumber"))).build().toString

  }

  def bic(): String = {
    alpha.botify(utility.getValueFromArray("bic").toUpperCase)
  }

  def mastercreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.masterCardPrefixList, 16, 1)(0)
  }

  def mastercreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.masterCardPrefixList, 16, howMany)
  }

  def visacreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.visaPrefixList, 16, 1)(0)
  }

  def visacreditCard(cardNumberLength: Int): String = {
    CreditCard.createCreditCardNumber(CreditCard.visaPrefixList, cardNumberLength, 1)(0)
  }

  def visacreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.visaPrefixList, 16, 1)
  }

  def visacreditCards(howMany: Int, cardNumberLength: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.visaPrefixList, cardNumberLength, 1)
  }

  def americanExpresscreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.amexPrefixList, 16, 1)(0)
  }

  def americanExpresscreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.amexPrefixList, 16, howMany)
  }

  def discoverCreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.discoverPrefixList, 16, 1)(0)
  }

  def discoverCreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.discoverPrefixList, 16, howMany)
  }

  def dinersCreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.dinersPrefixList, 16, 1)(0)
  }

  def dinersCreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.dinersPrefixList, 16, howMany)
  }

  def jcbCreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.jcbPrefixList, 16, 1)(0)
  }

  def jcbCreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.jcbPrefixList, 16, howMany)
  }

  def voyagerCreditCard(): String = {
    CreditCard.createCreditCardNumber(CreditCard.jcbPrefixList, 15, 1)(0)
  }

  def voyagerCreditCards(howMany: Int): Array[String] = {
    CreditCard.createCreditCardNumber(CreditCard.jcbPrefixList, 15, howMany)
  }

  def pinCode(): String = {
    alpha.numerify("####")
  }


}
