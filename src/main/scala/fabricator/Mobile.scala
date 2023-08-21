package fabricator

import scala.util.Random

case class Mobile(private val random: Random = new Random(), private val alpha: Alphanumeric = Alphanumeric()) {

  /**
   * Android GCM Registration ID
   * @return
   */
  def androidGsmId: String = "APA91" + alpha.randomString(178)

  def applePushToken: String = alpha.randomString("abcdef1234567890", 64)

  def wp8_anid2: String = new String(java.util.Base64.getEncoder().encode(alpha.randomString.getBytes("UTF-8")))

  def wp7_anid: String =
    "A=" + alpha.randomGuid.replace("-", "").toUpperCase + "&E=" + alpha.randomHash(3) + "&W=" + alpha.randomInt(0, 9)

  def blackBerryPin: String = alpha.randomHash(8)

}
