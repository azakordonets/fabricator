package fabricator

import java.net.URL
import java.nio.charset.Charset

import com.sun.jndi.toolkit.url.Uri
import org.testng.AssertJUnit.assertTrue
import org.testng.annotations.{DataProvider, Test}

import scala.collection.mutable

class InternetTestSuite extends BaseTestSuite {

  @Test
  def testCustomConstructor()  {
    val customInternet = fabricator.Internet("us")
    assert(customInternet != null)
  }

  @Test
  def testDefaultUrl() = {
    val url = internet.urlBuilder.toString()
    val splitArray: Array[String] = url.split("/")
    assert(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"))
    assertResult("http:")(splitArray(0))
    assertResult("getEntity?q=test")(splitArray(3))
  }

  @Test
  def testUrlAsUri() = {
    val url = internet.urlBuilder.host("test.ru").toUri
    val expectedUrl = new Uri("http://test.ru/getEntity?q=test")
    assertResult(expectedUrl.getScheme)(url.getScheme)
    assertResult(expectedUrl.getHost)(url.getHost)
    assertResult(expectedUrl.getPort)(url.getPort)
    assertResult(expectedUrl.getPath)(url.getPath)
    assertResult(expectedUrl.getQuery)(url.getQuery)
  }

  @Test
  def testUrlAsURL() = {
    val url = internet.urlBuilder.host("test.ru").toUrl
    val expectedUrl = new URL("http://test.ru/getEntity?q=test")
    assertResult(expectedUrl)(url)
  }

  @Test
  def testUrlWithCustomProtocol() = {
    val url = internet.urlBuilder.scheme("https").toString()
    val splitArray: Array[String] = url.split("/")
    assert(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"))
    assertResult("https:")(splitArray(0))
    assertResult("getEntity?q=test")(splitArray(3))
  }

  @Test
  def testUlrWithCustomHost() = {
    val url = internet.urlBuilder.host("test.net").toString()
    val splitArray: Array[String] = url.split("/")
    assert(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"))
    assertResult("http:")(splitArray(0))
    assertResult("test.net")(splitArray(2))
    assertResult("getEntity?q=test")(splitArray(3))
  }

  @Test
  def testUrlWithCustomPort() = {
    val url = internet.urlBuilder.host("test.net").port(8080).toString()
    val splitArray: Array[String] = url.split("/")
    assert(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"))
    assertResult("http:")(splitArray(0))
    assertResult("test.net:8080")(splitArray(2))
    assertResult("getEntity?q=test")(splitArray(3))
  }

  @Test
  def testUlrWithCustomPath() = {
    val url = internet.urlBuilder.host("test.net").path("/customPath").toString()
    val splitArray: Array[String] = url.split("/")
    assert(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"))
    assertResult("http:")(splitArray(0))
    assertResult("test.net")(splitArray(2))
    assertResult("customPath?q=test")(splitArray(3))
  }

  @DataProvider(name = "urlCustomParams")
  def urlCustomParams() = {
    Array(Array(mutable.Map[String, Any]("q" -> "test"), "?q=test"),
      Array(mutable.Map[String, Any]("q" -> "test", "test" -> "hello"), "?q=test&test=hello"),
      Array(mutable.Map[String, Any]("q" -> "test", "name" -> "Josh Lennon"), "?q=test&name=Josh+Lennon"),
      Array(mutable.Map[String, Any]("q" -> true), "?q=true"),
      Array(mutable.Map[String, Any]("q" -> 120), "?q=120"),
      Array(mutable.Map[String, Any]("q" -> 23.5), "?q=23.5")
    )
  }

  @Test(dataProvider = "urlCustomParams")
  def testUlrWithCustomParams(params: mutable.Map[String, Any], expectedParamsString: String) = {
    val url = internet.urlBuilder.host("test.net").path("/customPath").params(params).toString()
    assert(url.matches("^(.*:)//([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$"))
    assertResult("http://test.net/customPath" + expectedParamsString)(url)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testUrlParamsException() = {
    internet.urlBuilder.params(mutable.Map[String, Any]("x" -> BigInt.apply(10))).toString()
  }

  @Test
  def testUrlEncodeAsCharset() = {
    val url = internet.urlBuilder.host("test.com").params(mutable.Map("q"->"test 123")).encodeAs(Charset.forName("UTF-8")).toString()
    assertResult("http://test.com/getEntity%3Fq%3Dtest%2B123")(url)
  }

  @Test
  def testUrlEncodeAsString() = {
    val url = internet.urlBuilder.host("test.com").encodeAs("ISO-8859-1").toString()
    assertResult("http://test.com/getEntity%3Fq%3Dtest")(url)
  }

  @Test
  def testIp() = {
    val ip = internet.ip
    if (debugEnabled) logger.debug("Testing custom ip. Ip is " + ip)
    assert(ip != "0.0.0.0")
    assert(ip != "255.255.255.255")
    val ipValues = ip.split("\\.")
    for (i <- 0 to ipValues.size - 1) {
      assert(ipValues(i).toInt <= 256)
    }
  }

  @Test
  def testIpV6() = {
    val ipv6 = internet.ipv6
    if (debugEnabled) logger.debug("Testing custom ipv6. Ip is " + ipv6)
    assert(ipv6.matches("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$"))
  }

  @Test
  def testMacAddress() = {
    val macAddress = internet.macAddress
    val UUID = internet.UUID
    if (debugEnabled) logger.debug("Testing random mac address " + macAddress)
    assert(macAddress.matches("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$"))
  }

  @Test
  def testUUID() = {
    val UUID = internet.UUID
    if (debugEnabled) logger.debug("Testing random UUID " + UUID)
    assert(UUID.matches("[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}"))
  }

  @Test
  def testAppleToken() = {
    val token = internet.appleToken
    if (debugEnabled) logger.debug("Testing random apple push token " + token)
    assert(token.length == 64)
  }

  @DataProvider(name = "colorVariations")
  def colorVariations() = {
    Array(Array("hex", false, "^#[A-Fa-f0-9]{1,6}"),
      Array("hex", true, "^#[A-Fa-f0-9]{1,6}"),
      Array("shorthex", false, "^#[A-Fa-f0-9]{1,6}"),
      Array("shorthex", true, "^#[A-Fa-f0-9]{1,6}"),
      Array("rgb", true, "rgb\\(\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*\\)$"),
      Array("rgb", false, "rgb\\(\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*,\\s*((?:[0-2]?[0-9])?[0-9])\\s*\\)$")
    )
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testColorCodeException() = {
    val color = internet.color("wrong", grayscale = true)
  }

  @Test(dataProvider = "colorVariations")
  def testColor(codeType: String, grayscale: Boolean, regexMatch: String) = {
    for (i <- 1 to 10) {
      val color = internet.color(codeType, grayscale)
      if (debugEnabled) logger.debug("Testing random " + codeType + " color with greyscale = " + grayscale + ". Color code is : " + color)
      assert(color.matches(regexMatch), "Color " + color + " didn't match to regex : " + regexMatch)
    }
  }

  @Test
  def testTwitter() = {
    val twitter = internet.twitter
    if (debugEnabled) logger.debug("Testing random twitter account name : " + twitter)
    assert(twitter.matches("@[a-zA-Z-0-9]+"))
  }

  @Test
  def testHashtag() = {
    val hashtag = internet.hashtag
    if (debugEnabled) logger.debug("Testing random hashtag account name : " + hashtag)
    assertTrue("Hashtag: "+hashtag, hashtag.matches("#([A-Za-z0-9\\-\\_]+)"))
  }

  @Test
  def testGoogleAnalyticks() = {
    val gaCode = internet.googleAnalyticsTrackCode
    if (debugEnabled) logger.debug("Testing random google analytics tracking code : " + gaCode)
    assert(gaCode.matches("UA-\\d{4,5}-\\d{2}"))
  }

  @Test
  def testFacebookId() = {
    val facebookId = internet.facebookId
    if (debugEnabled) logger.debug("Testing random facebook id : " + facebookId)
    assert(facebookId.length == 16)
  }

  @Test
  def testAvatar() = {
    val avatar = internet.avatar
    assert(avatar.matches("http.*/[^/]+/128.jpg$"))
  }


}
