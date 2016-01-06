package fabricator

import java.awt.Color

import fabricator.entities.UrlBuilder

import scala.util.Random

object Internet {

  def apply(): Internet = {
    new Internet(UtilityService(), Alphanumeric(),
      new Random(), Contact(), Words())
  }

  def apply(locale: String): Internet = {
    new Internet(UtilityService(locale), Alphanumeric(),
      new Random(), Contact(locale), Words(locale))
  }

}

class Internet(private val utility: UtilityService,
               private val alpha: Alphanumeric,
               private val random: Random,
               private val contact: Contact,
               private val word: Words) {

  def appleToken: String = random.alphanumeric.take(64).mkString.toLowerCase

  def urlBuilder: UrlBuilder = new UrlBuilder

  def ip: String = {
    val ip = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256)
    if (ip == "0.0.0.0" || ip == "255.255.255.255") this.ip
    else ip
  }

  def ipv6 = {
    val alphabet = "abcdefABCDEF0123456789"
    (1 to 8).map(i => "" + (1 to 4).map(y => "" + alphabet.charAt(Random.nextInt(alphabet.length))).mkString).mkString(":")
  }

  def macAddress: String = {
    Iterator.continually(Array.fill[String](6)(f"${random.nextInt(255)}%02x").mkString(":")).toSeq.head.toUpperCase
  }

  def UUID: String = java.util.UUID.randomUUID.toString

  def color: String = color("hex", grayscale = false)

  def color(format: String, grayscale: Boolean) = {
    val color = if (grayscale) {
      val redValue = random.nextInt(255)
      new Color(redValue, redValue, redValue)
    } else {
      new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }
    format match {
      case "hex" => "#" + Integer.toHexString(color.getRGB & 0x00ffffff)
      case "shorthex" if grayscale => "#000"
      case "shorthex" => ("#" + Integer.toHexString(color.getRGB & 0x00ffffff)).substring(0, 4)
      case "rgb" => "rgb(" + color.getRed + "," + color.getBlue + "," + color.getBlue + ")"
      case _ => throw new IllegalArgumentException("Incorrect type is specified. Possible options : hex, shorthex, rgb")
    }
  }

  def username: String = {
    contact.firstName.toLowerCase + contact.lastName.capitalize + alpha.randomInt(1960, 2020)
  }

  def twitter: String = "@" + contact.firstName.toLowerCase + contact.lastName + alpha.randomInt(9999)

  def hashtag: String = "#" + word.words(3).mkString.replaceAll("'", "")

  def googleAnalyticsTrackCode: String = "UA-" + alpha.randomInt(10000, 100000) + "-" + alpha.randomInt(10, 100)

  def facebookId: String = (1 to 16).map(i => Random.nextInt(10)).mkString

  /**
   * Generates URL to avatar that is taken from <a href = "http://uifaces.com/authorized"> service
   * All this avatars have been authorized by it's users
   * @return
   */
  def avatar: String = "https://s3.amazonaws.com/uifaces/faces/twitter/" + utility.getValueFromArray("avatar")

}

