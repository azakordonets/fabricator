package fabricator

import org.testng.annotations.Test

class UserAgentTestSuite extends BaseTestSuite {

  val macProcessorList: Array[String] = util.getArrayFromJson("mac_processor")
  val linuxProcessorList: Array[String] = util.getArrayFromJson("linux_processor")
  val browsersList: Array[String] = util.getArrayFromJson("browser")
  val windowsPlatformList: Array[String] = util.getArrayFromJson("windows")
  val userAgent = UserAgent()

  @Test
  def testMacProcessor() = {
    val macProcessor = userAgent.mac_processor
    assert(macProcessorList.contains(macProcessor))
  }

  @Test
  def testLinuxProcessor() = {
    val linuxProcessor = userAgent.linux_processor
    assert(linuxProcessorList.contains(linuxProcessor))
  }

  @Test
  def testBrowser() = {
    val browser = userAgent.browser
    assert(browsersList.contains(browser))
  }

  @Test
  def testWindowsPlatform() = {
    val windowsPlatform = userAgent.windows_platform_token
    assert(windowsPlatformList.contains(windowsPlatform))
  }

  @Test
  def testLinuxPlatformToken() = {
    val linuxPlatform = userAgent.linux_platform_token
    val splitLinuxPlatform = linuxPlatform.split("Linux")
    assert(splitLinuxPlatform(0).equals("X11; "))
    assert(linuxProcessorList.contains(splitLinuxPlatform(1).trim))
  }

  @Test
  def testMacPlatformToken() = {
    val macPlatformToken = userAgent.mac_platform_token
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
