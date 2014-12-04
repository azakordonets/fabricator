package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.{DataProvider, Test}

class InternetTestSuite extends TestNGSuite with LazyLogging{

  val fabr = new Fabricator
  val util = new UtilityService()
  val alpha = fabr.alphaNumeric()
  val contact = fabr.contact()
  val calendar = fabr.calendar()
  var wordsFaker = fabr.words()
  val internet = fabr.internet()
  val finance = fabr.finance()

  @Test
  def testCustomUrl() = {
    val url = internet.url("http", "test.ru", "getUser", Map("id" -> "123", "ts" -> "09-12-10"))
    logger.info("Testing custom url. Url is " + url)
    assertResult("http://test.ru/getUser?id=123&ts=09-12-10")(url)
  }

  @Test
  def testIp() = {
    val ip = internet.ip()
    logger.info("Testing custom ip. Ip is " + ip)
    assert(ip != "0.0.0.0")
    assert(ip != "255.255.255.255")
    val ipValues = ip.split("\\.")
    for (i <- 0 to ipValues.size - 1) {
      assert(ipValues(i).toInt <= 256)
    }
  }

  @Test
  def testIpV6() = {
    val ipv6 = internet.ipv6()
    logger.info("Testing custom ipv6. Ip is " + ipv6)
    assert(ipv6.matches("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$"))
  }

  @Test
  def testMacAddress() = {
    val macAddress = internet.macAddress()
    val UUID = internet.UUID()
    logger.info("Testing random mac address " + macAddress)
    assert(macAddress.matches("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$"))
  }

  @Test
  def testUUID() = {
    val UUID = internet.UUID()
    logger.info("Testing random UUID " + UUID)
    assert(UUID.matches("[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}"))
  }

  @Test
  def testAppleToken() = {
    val token = internet.appleToken()
    logger.info("Testing random apple push token " + token)
    assert(token.length == 64)
  }

  @DataProvider(name = "colorVariations")
  def colorVariations() = {
    Array(Array("hex",false,  "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"),
      Array("hex", true, "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"),
      Array("shorthex", false, "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"),
      Array("shorthex", true, "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"),
      Array("rgb", true, "rgb\\(\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*\\)$"),
      Array("rgb", false, "rgb\\(\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*\\)$")
    )
  }

  @Test(dataProvider = "colorVariations")
  def testColor(codeType: String, grayscale: Boolean, regexMatch: String) = {
    val color = internet.color(codeType, grayscale)
    logger.info("Testing random "+codeType+" color with greyscale = "+grayscale+". Color code is : "  +color)
    assert(color.matches(regexMatch))
  }

  @Test
  def testTwitter() = {
    val twitter = internet.twitter()
    logger.info("Testing random twitter account name : "+twitter)
    assert(twitter.matches("@[a-zA-Z]+"))
  }

  @Test
  def testHashtag() = {
    val hashtag = internet.hashtag()
    logger.info("Testing random hashtag account name : "+hashtag)
    assert(hashtag.matches("#[a-zA-Z]+"))
  }

  @Test
  def testGoogleAnalyticks() = {
    val gaCode = internet.googleAnalyticsTrackCode()
    logger.info("Testing random google analytics tracking code : "+ gaCode)
    assert(gaCode.matches("UA-\\d{5}-\\d{2}"))
  }

  @Test
  def testFacebookId() = {
    val facebookId = internet.facebookId()
    logger.info("Testing random facebook id : "+ facebookId)
    assert(facebookId.length == 16)
  }




}
