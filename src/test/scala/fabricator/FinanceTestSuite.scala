package fabricator

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit
import org.testng.annotations.{DataProvider, Test}

class FinanceTestSuite extends BaseTestSuite {

  @DataProvider(name = "languageDp")
  def languageDp(): Array[Array[Any]] = {
    Array(Array("nl"),
      Array("de")
    )
  }

  @Test(dataProvider = "languageDp")
  def testCustomConstructor(lang: String)  {
    val customFinance = fabricator.Finance(lang)
    assert(customFinance != null)
  }

  @Test
  def testIban() = {
    val iban = finance.iban
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
    val creditCard = finance.masterCard
    assert(creditCard.length == 16)
    val creditCards = finance.masterCards(10)
    assert(creditCards.length == 10)
    for (creditCard <- creditCards) assert(creditCard.length == 16)
    if (debugEnabled) logger.debug("Testing random masterCard : " + creditCard)
    for ((card, index) <- creditCards.view.zipWithIndex) if (debugEnabled) logger.debug("Master Credit card #" + index + " is " + card)
  }

  @Test
  def testVisaCard() = {
    val visa16CreditCard = finance.visaCard
    assert(visa16CreditCard.length == 16)
    val visa15CreditCard = finance.visaCard(15)
    assert(visa15CreditCard.length == 15)
    val visa16Cards = finance.visaCards(10)
    assert(visa16Cards.length == 10)
    for (visa <- visa16Cards) assert(visa.length() == 16)
    val visa15Cards = finance.visaCards(10, 15)
    assert(visa15Cards.length == 10)
    for (visa <- visa15Cards) assert(visa.length() == 15)
    if (debugEnabled) logger.debug("Testing random 16 length Visa Card : " + visa16CreditCard)
    if (debugEnabled) logger.debug("Testing random 15 length Visa Card : " + visa15CreditCard)
    for ((card, index) <- visa16Cards.view.zipWithIndex) if (debugEnabled) logger.debug("Visa Credit card #" + index + " is " + card)
    for ((card, index) <- visa15Cards.view.zipWithIndex) if (debugEnabled) logger.debug("Visa Credit card #" + index + " is " + card)
  }

  @Test
  def testAmericanExpress() = {
    val amexCreditCard = finance.americanExpressCard
    assert(amexCreditCard.length == 16)
    val amexCrediCards = finance.americanExpressCards(10)
    assert(amexCrediCards.length == 10)
    for (amex <- amexCrediCards) assert(amex.length == 16)
    if (debugEnabled) logger.debug("Testing random american express card: " + amexCreditCard)
    for ((card, index) <- amexCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("american express card #" + index + " is " + card)
  }

  @Test
  def testDiscover() = {
    val discoverCreditCard = finance.discoverCard
    assert(discoverCreditCard.length == 16)
    val discoverCrediCards = finance.discoverCards(10)
    assert(discoverCrediCards.length == 10)
    for (discover <- discoverCrediCards) assert(discover.length == 16)
    if (debugEnabled) logger.debug("Testing random discover card: " + discoverCreditCard)
    for ((card, index) <- discoverCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("Discover Credit card #" + index + " is " + card)
  }

  @Test
  def testDiners() = {
    val dinersCreditCard = finance.dinersCard
    assert(dinersCreditCard.length == 16)
    val dinersCrediCards = finance.dinersCards(10)
    assert(dinersCrediCards.length == 10)
    for (diners <- dinersCrediCards) assert(diners.length == 16)
    if (debugEnabled) logger.debug("Testing random diners card: " + dinersCreditCard)
    for ((card, index) <- dinersCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("Diners Credit card #" + index + " is " + card)
  }

  @Test
  def testJcb() = {
    val jcbCreditCard = finance.jcbCard
    assert(jcbCreditCard.length == 16)
    val jcbCrediCards = finance.jcbCards(10)
    assert(jcbCrediCards.length == 10)
    for (jcb <- jcbCrediCards) assert(jcb.length == 16)
    if (debugEnabled) logger.debug("Testing random jcb card: " + jcbCreditCard)
    for ((card, index) <- jcbCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("JCB Credit card #" + index + " is " + card)
  }

  @Test
  def testVoyager() = {
    val voyagerCreditCard = finance.voyagerCard
    assert(voyagerCreditCard.length == 15)
    val voyagerCrediCards = finance.voyagerCards(10)
    assert(voyagerCrediCards.length == 10)
    for (voyager <- voyagerCrediCards) assert(voyager.length == 15)
    if (debugEnabled) logger.debug("Testing random voyager card: " + voyagerCreditCard)
    for ((card, index) <- voyagerCrediCards.view.zipWithIndex) if (debugEnabled) logger.debug("Voyager Credit card #" + index + " is " + card)
  }

  @Test
  def testPin() = {
    val pin = finance.pinCode
    assert(pin.length == 4)
    assert(pin.toInt >= 0 && pin.toInt <= 9999)
  }

  @Test
  def testSsn() = {
    for (i <- 1 to 10) {
      val ssn = finance.ssn
      assert(ssn.matches("\\d{3}-\\d{2}-\\d{4}"))
    }
  }

}
