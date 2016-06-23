package fabricator

import org.testng.annotations.Test

class UserAgentTestSuite extends BaseTestSuite {

  lazy val macProcessorList: Array[String] = util.getArrayFromJson("mac_processor")
  lazy val linuxProcessorList: Array[String] = util.getArrayFromJson("linux_processor")
  lazy val browsersList: Array[String] = util.getArrayFromJson("browser")
  lazy val windowsPlatformList: Array[String] = util.getArrayFromJson("windows")
  lazy val userAgent = Fabricator.userAgent()

  @Test
  def testMacProcessor() = {
    val macProcessor = userAgent.macProcessor
    assert(macProcessorList.contains(macProcessor))
  }

  @Test
  def testLinuxProcessor() = {
    val linuxProcessor = userAgent.linuxProcessor
    assert(linuxProcessorList.contains(linuxProcessor))
  }

  @Test
  def testBrowserName() = {
    val browserName = userAgent.browserName
    assert(browsersList.contains(browserName))
  }

  @Test
  def testBrowser() = {
    val browser = userAgent.browser
    assert(browser.contains("Chrome")
    || browser.contains("Firefox")
    || browser.contains("Gecko")
    || browser.contains("MSIE")
    || browser.contains("Presto")
    )
  }

  @Test
  def testWindowsPlatform() = {
    val windowsPlatform = userAgent.windowsPlatformToken
    assert(windowsPlatformList.contains(windowsPlatform))
  }

  @Test
  def testLinuxPlatformToken() = {
    val linuxPlatform = userAgent.linuxPlatformToken
    val splitLinuxPlatform = linuxPlatform.split("Linux")
    assert(splitLinuxPlatform(0).equals("X11; "))
    assert(linuxProcessorList.contains(splitLinuxPlatform(1).trim))
  }

  @Test
  def testMacPlatformToken() = {
    val macPlatformToken = userAgent.macPlatformToken
    val splitMacPlatform = macPlatformToken.split(" Mac OS X ")
    assert(splitMacPlatform(0).matches("Macintosh; " + getListElementsOptionalRegex(macProcessorList)))
    assert(splitMacPlatform(1).matches("10_[5-8]_[0-9]"))
  }

  private def getListElementsOptionalRegex(array: Array[String]): String = {
    "(" + array.mkString("|") + ")"
  }

  @Test
  def testChrome() = {
    val chrome = userAgent.chrome
    val keyWords = Array("(KHTML, like Gecko)", "Chrome", "AppleWebKit", "Safari", "Mozilla/5.0")
    keyWords.foreach(keyWord => assert(chrome.contains(keyWord)))
  }

  @Test
  def testFirefox() = {
    val firefox = userAgent.firefox
    val keyWords = Array("Gecko", "Firefox", "rv:", "Mozilla/5.0")
    keyWords.foreach(keyWord => assert(firefox.contains(keyWord)))
  }

  @Test
  def testOpera() = {
    val opera = userAgent.opera
    val keyWords = Array("Presto", "Opera", "Version")
    keyWords.foreach(keyWord => assert(opera.contains(keyWord)))
    assert(opera.contains("en-US") || opera.contains("sl-SI") || opera.contains("it-IT"))
  }

  @Test
  def testIE() = {
    val ie = userAgent.internet_explorer
    val keyWords = Array("Mozilla/5.0 (compatible; MSIE", "Trident")
    keyWords.foreach(keyWord => assert(ie.contains(keyWord)))
  }


}
