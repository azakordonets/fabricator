package fabricator

import scala.util.Random

case class Mobile(private val random: Random = new Random(),
             private val alpha: Alphanumeric = Alphanumeric()) {

  /**
   * Android GCM Registration ID
   * @return
   */
  def androidGsmId: String = "APA91" + alpha.getString(178)

  def applePushToken: String = alpha.getString("abcdef1234567890", 64)

  def wp8_anid2: String = new sun.misc.BASE64Encoder().encode(alpha.getString.getBytes("UTF-8"))

  def wp7_anid: String = "A=" + alpha.guid.replace("-", "").toUpperCase + "&E=" + alpha.hash(3) + "&W=" + alpha.getInteger(0, 9)

  def blackBerryPin: String = alpha.hash(8)

}
