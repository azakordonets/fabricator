package fabricator

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit
import org.testng.annotations.Test

class FinanceTestSuite extends BaseTestSuite {

  @Test
  def testCustomConstructor()  {
    val customFinance = fabricator.Finance("us")
    assert(customFinance != null)
  }

  @Test
  def testIban() = {
    var iban = finance.iban
    if (debugEnabled) logger.debug("Testing random IBAN number : " + iban)
    val check: IBANCheckDigit = new IBANCheckDigit()
    assert(check.isValid(iban))
  }

  @Test
  def testBic() = {
    val bic = finance.bic
    if (debugEnabled) logger.debug("Testing random BIC number : " + bic)
    assert(bic.matches("^([a-zA-Z]){4}([0-9-a-zA-Z]){2}([0-9a-zA-Z]){2}([0-9a-zA-Z]{3})?$"))
  }

  @Test
  def testMasterCard() = {
    val creditCard = finance.mastercreditCard
    val creditCards = finance.mastercreditCards(10)
    if (debugEnabled) logger.debug("Testing random masterCard : " + creditCard)
    for ((card, index) <- creditCards.view.zipWithIndex) if (debugEnabled) logger.debug("Master Credit card #" + index + " is " + card)
  }

  @Test
  def testVisaCard() = {
    val visa16CreditCard = finance.visacreditCard
    val visa15CreditCard = finance.visacreditCard(15)
    val visa16Cards = finance.visacreditCards(10)
    val visa15Cards = finance.visacreditCards(10, 15)
    if (debugEnabled) logger.debug("Testing random 16 length Visa Card : " + visa16CreditCard)
    if (debugEnabled) logger.debug("Testing random 15 length Visa Card : " + visa15CreditCard)
    for ((card, index) <- visa16Cards.view.zipWithIndex) if (debugEnabled) logger.debug("Visa Credit card #" + index + " is " + card)
    for ((card, index) <- visa15Cards.view.zipWithIndex) if (debugEnabled) logger.debug("Visa Credit card #" + index + " is " + card)
  }

  @Test
  def testAmericanExpress() = {
    val amexCreditCard = finance.americanExpresscreditCard
    val amexCrediCards = finance.americanExpresscreditCards(10)
    if (debugEnabled) logger.debug("Testing random american express card: " + amexCreditCard)
    for ((card, index) <- amexCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("american express card #" + index + " is " + card)
  }

  @Test
  def testDiscover() = {
    val discoverCreditCard = finance.discoverCreditCard
    val discoverCrediCards = finance.discoverCreditCards(10)
    if (debugEnabled) logger.debug("Testing random discover card: " + discoverCreditCard)
    for ((card, index) <- discoverCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("Discover Credit card #" + index + " is " + card)
  }

  @Test
  def testDiners() = {
    val dinersCreditCard = finance.dinersCreditCard
    val dinersCrediCards = finance.dinersCreditCards(10)
    if (debugEnabled) logger.debug("Testing random diners card: " + dinersCreditCard)
    for ((card, index) <- dinersCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("Diners Credit card #" + index + " is " + card)
  }

  @Test
  def testJcb() = {
    val jcbCreditCard = finance.jcbCreditCard
    val jcbCrediCards = finance.jcbCreditCards(10)
    if (debugEnabled) logger.debug("Testing random jcb card: " + jcbCreditCard)
    for ((card, index) <- jcbCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("JCB Credit card #" + index + " is " + card)
  }

  @Test
  def testVoyager() = {
    val voyagerCreditCard = finance.voyagerCreditCard
    val voyagerCrediCards = finance.voyagerCreditCards(10)
    if (debugEnabled) logger.debug("Testing random voyager card: " + voyagerCreditCard)
    for ((card, index) <- voyagerCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("Voyager Credit card #" + index + " is " + card)
  }

  @Test
  def testPin() = {
    val pin = finance.pinCode
    assert(pin.length == 4)
    assert(pin.toInt >= 0 && pin.toInt <= 9999)
  }

}
