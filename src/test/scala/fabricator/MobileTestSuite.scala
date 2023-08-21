package fabricator

import java.util.Base64

import org.testng.annotations.Test

class MobileTestSuite extends BaseTestSuite {

  @Test
  def testAndroidId() = {
    val androidId = mobile.androidGsmId
    if (debugEnabled) logger.debug("Testing random android ID : " + androidId)
    val expectedString = "APA910123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"
    assert(androidId.length == 183)
    for (symbol <- androidId) assert(expectedString.contains(symbol))
  }

  @Test
  def testAppleId() = {
    val appleId = mobile.applePushToken
    if (debugEnabled) logger.debug("Testing random apple ID : " + appleId)
    val expectedString = "abcdef1234567890"
    assert(appleId.length == 64)
    for (symbol <- appleId) assert(expectedString.contains(symbol))
  }

  @Test
  def testWindows8d() = {
    val windows8Id = mobile.wp8_anid2
    if (debugEnabled) logger.debug("Testing random windows 8 ID : " + windows8Id)
    val expectedString = "0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"
    val decodedString  = Base64.getDecoder.decode(windows8Id)
    for (symbol <- decodedString) assert(expectedString.contains(symbol))
  }

  @Test
  def testWindows7Id() = {
    val windows7Id = mobile.wp7_anid
    if (debugEnabled) logger.debug("Testing random windows 7 ID : " + windows7Id)
    assert(windows7Id.matches("A=\\w(.+?)&E=\\w{3}&W=\\d{1}"))
  }

  @Test
  def testBlackBerryPin() = {
    val hash = mobile.blackBerryPin
    if (debugEnabled) logger.debug("Checking random blackberry number :  " + hash)
    assert(hash.length() == 8)
  }
}
