package fabricator

import java.awt.Color
import java.math.BigInteger

import play.libs.Json

import scala.util.Random

/**
 * Created by Andrew Zakordonets on 22/09/14.
 */
class Internet (private val utility: UtilityService,
                private val alpha: Alphanumeric,
                private val random: Random,
                private val contact: Contact,
                 private val word: Words){

  def this() {
    this(new UtilityService(),
         new Alphanumeric(),
         new Random(),
         new Contact(),
         new Words())
  }

  def appleToken(): String = {
    random.alphanumeric.take(64).mkString.toLowerCase
  }

  def url (): String = {
    alpha.botify("????#####??????.")+utility.getValueFromArray("domain_suffix")
  }

  def url(protocol: String, host:String, callName:String, params:Map[String, String]):String = {
    var paramsString: String = ""
    for ((key, value) <- params) {
      paramsString += key + "=" + value + "&"
    }
    protocol + "://" + host + "/" + callName + "?" + paramsString.substring(0, paramsString.size-1)
  }

  def ip(): String = {
    val ip = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256)
    if (ip == "0.0.0.0" || ip == "255.255.255.255") this.ip()
    else ip
  }

  def ipv6() = {
    val alphabet = "abcdefABCDEF0123456789"
    (1 to 8).map(i => "" + (1 to 4).map(y=>"" +alphabet.charAt(Random.nextInt(alphabet.length))).mkString).mkString(":")
  }

  def macAddress(): String = {
    Iterator.continually(Array.fill[String](6)(f"${random.nextInt(255)}%02x").mkString(":")).toSeq.head.toUpperCase
  }

  def UUID(): String = {
    java.util.UUID.randomUUID.toString
  }

  def color(): String = {
    color("hex", false)
  }

  def color(format: String, grayscale: Boolean) = {
    val color = if (grayscale) {
      val redValue = random.nextInt(255)
      new Color(redValue, redValue, redValue)
    } else {
      new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }
    format match {
      case "hex" => "#" + Integer.toHexString(color.getRGB() & 0x00ffffff)
      case "shorthex"  if grayscale => "#000"
      case "shorthex" => ("#" + Integer.toHexString(color.getRGB() & 0x00ffffff)).substring(0,4)
      case "rgb" => "rgb(" + color.getRed + "," + color.getBlue + "," + color.getBlue + ")"
      case _ => throw new Exception("Incorrect type is specified. Possible options : hex, shorthex, rgb")
    }
  }

  def twitter(): String = {
    "@"+contact.firstName()+contact.lastName()
  }

  def hashtag(): String = {
    "#"+word.words(3).mkString
  }

  def googleAnalyticsTrackCode(): String = {
    "UA-" + random.nextInt(100000) + "-" + alpha.integer(10,100)
  }

  def facebookId(): String = {
    (1 to 16).map(i => Random.nextInt(10)).mkString
  }


}

