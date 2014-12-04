package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class FinanceTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val finance = fabr.finance()

  @Test
  def testIban() = {
    var iban = finance.iban()
    logger.info("Testing random IBAN number : " + iban)
    val check: IBANCheckDigit = new IBANCheckDigit()
    assert(check.isValid(iban))
  }

  @Test
  def testBic() = {
    val bic = finance.bic()
    logger.info("Testing random BIC number : " + bic)
    assert(bic.matches("^([a-zA-Z]){4}([a-zA-Z]){2}([0-9a-zA-Z]){2}([0-9a-zA-Z]{3})?$"))
  }

  @Test
  def testMasterCard() = {
    val creditCard = finance.mastercreditCard()
    val creditCards = finance.mastercreditCards(10)
    logger.info("Testing random masterCard : " + creditCard)
    for ((card, index) <- creditCards.view.zipWithIndex) logger.info("Master Credit card #"+ index +" is " +card)
  }

  @Test
  def testVisaCard() = {
    val visa16CreditCard = finance.visacreditCard()
    val visa15CreditCard = finance.visacreditCard(15)
    val visa16Cards = finance.visacreditCards(10)
    val visa15Cards = finance.visacreditCards(10, 15)
    logger.info("Testing random 16 length Visa Card : " + visa16CreditCard)
    logger.info("Testing random 15 length Visa Card : " + visa15CreditCard)
    for ((card, index) <- visa16Cards.view.zipWithIndex)  logger.info("Visa Credit card #"+ index +" is " +card)
    for ((card, index) <- visa15Cards.view.zipWithIndex) logger.info("Visa Credit card #"+ index +" is " +card)
  }

  @Test
  def testAmericanExpress() = {
    val amexCreditCard = finance.americanExpresscreditCard()
    val amexCrediCards = finance.americanExpresscreditCards(10)
    logger.info("Testing random american express card: "+amexCreditCard)
    for ((card, index) <- amexCrediCards.view.zipWithIndex)  logger.info("american express card #"+ index +" is " +card)
  }

  @Test
  def testDiscover() = {
    val discoverCreditCard = finance.discoverCreditCard()
    val discoverCrediCards = finance.discoverCreditCards(10)
    logger.info("Testing random discover card: "+discoverCreditCard)
    for ((card, index) <- discoverCrediCards.view.zipWithIndex)  logger.info("Discover Credit card #"+ index +" is " +card)
  }

  @Test
  def testDiners() = {
    val dinersCreditCard = finance.dinersCreditCard()
    val dinersCrediCards = finance.dinersCreditCards(10)
    logger.info("Testing random diners card: "+dinersCreditCard)
    for ((card, index) <- dinersCrediCards.view.zipWithIndex)  logger.info("Diners Credit card #"+ index +" is " +card)
  }

  @Test
  def testJcb() = {
    val jcbCreditCard = finance.jcbCreditCard()
    val jcbCrediCards = finance.jcbCreditCards(10)
    logger.info("Testing random jcb card: "+jcbCreditCard)
    for ((card, index) <- jcbCrediCards.view.zipWithIndex)  logger.info("JCB Credit card #"+ index +" is " +card)
  }

  @Test
  def testVoyager() = {
    val voyagerCreditCard = finance.voyagerCreditCard()
    val voyagerCrediCards = finance.voyagerCreditCards(10)
    logger.info("Testing random voyager card: "+voyagerCreditCard)
    for ((card, index) <- voyagerCrediCards.view.zipWithIndex)  logger.info("Voyager Credit card #"+ index +" is " +card)
  }

}
