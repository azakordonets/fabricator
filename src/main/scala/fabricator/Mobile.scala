package fabricator

import scala.util.Random

class Mobile(private val utility: UtilityService,
             private val random: Random,
             private val alpha: Alphanumeric,
             private val creditCard: CreditCard) {

  def this() {
    this(new UtilityService(), new Random(), new Alphanumeric(), new CreditCard)
  }

  /**
   * Android GCM Registration ID
   * @return
   */
  def androidGsmId(): String = {
    "APA91" + alpha.getString(178)
  }

  /**
   * Apple Push Token
   * @return
   */
  def applePushToken(): String = {
    alpha.string("abcdef1234567890", 64)
  }

  /**
   * Windows Phone 8 ANID2
   * @return
   */
  def wp8_anid2(): String = {
    new sun.misc.BASE64Encoder().encode(alpha.getString().getBytes("UTF-8"))
  }

  //// Windows Phone 7 ANID
  def wp7_anid(): String = {
    "A=" + alpha.guid().replace("-", "").toUpperCase + "&E=" + alpha.hash(3) + "&W=" + alpha.getInteger(0, 9)
  }

  // // BlackBerry Device PIN
  def blackBerryPin(): String = {
    alpha.hash(8)
  }

}
