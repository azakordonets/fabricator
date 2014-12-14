package fabricator

import com.spatial4j.core.context.SpatialContext
import com.spatial4j.core.io.GeohashUtils
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.{DataProvider, Test}

class LocationTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val util = new UtilityService()
  val alpha = fabr.alphaNumeric()
  val contact = fabr.contact()
  val calendar = fabr.calendar()
  var wordsFaker = fabr.words()
  val internet = fabr.internet()
  val finance = fabr.finance()
  val location = fabr.location()

  @DataProvider(name = "altitudeDP")
  def wordsCountDP() = {
    Array(Array("10"),
      Array("100"),
      Array("1000"),
      Array("4000"),
      Array("10000"),
      Array("100000")
    )
  }

  @Test
  def testAltitude() = {
    val altitude = location.altitude().toDouble
    logger.info("Testing random altitude: " + altitude)
    assert(altitude >= 0 && altitude <= 8848)
  }

  @Test
  def testAltitudeMaxValue() = {
    val altitude = location.altitude(10000).toDouble
    logger.info("Testing random altitude with max value 1000 : " + altitude)
    assert(altitude >= 0 && altitude <= 10000)
  }

  @Test
  def testAltitudeAccuracy() = {
    val altitude = location.altitude(10000, 2)
    logger.info("Testing random altitude with accuracy = 2 : " + altitude)
    assert(altitude.toDouble >= 0 && altitude.toDouble <= 10000)
    assert(altitude.toString.split("\\.")(1).length == 2)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testAltitudeException() = {
    val altitude = location.altitude(10000, 20)
    logger.info("Testing random altitude with accuracy = 2 : " + altitude)
  }

  @Test
  def testDepth() = {
    val depth = location.depth().toDouble
    logger.info("Testing random depth: " + depth)
    assert(depth <= 0 && depth >= -2550)
  }

  @Test
  def testDepthMaxValue() = {
    val depth = location.depth(10000).toDouble
    logger.info("Testing random depth with max value 10000 : " + depth)
    assert(depth <= 0 && depth >= -10000)
  }

  @Test
  def testDepthAccuracy() = {
    val depth = location.depth(10000, 2)
    logger.info("Testing random depth with accuracy = 2 : " + depth)
    assert(depth.toDouble <= 0 && depth.toDouble >= -10000)
    assert(depth.toString.split("\\.")(1).length == 2)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testDepthException() = {
    val depth = location.depth(10000, 20)
    logger.info("Testing custom (10000) random depth with accuracy = 2 : " + depth)
  }

  @Test
  def testCoordinates() = {
    val coordinates = location.coordinates()
    val latitude = coordinates.split(",")(0)
    val longitude = coordinates.split(",")(1)
    assert(latitude.toDouble >= -90 && latitude.toDouble <= 90)
    assert(longitude.toDouble >= -180 && longitude.toDouble <= 180)
    assert(latitude.split("\\.")(1).length == 5)
    assert(longitude.split("\\.")(1).length == 5)
  }

  @Test
  def testCoordinatesAccuracy() = {
    val coordinates = location.coordinates(2)
    val latitude = coordinates.split(",")(0)
    val longitude = coordinates.split(",")(1)
    assert(latitude.toDouble >= -90 && latitude.toDouble <= 90)
    assert(longitude.toDouble >= -180 && longitude.toDouble <= 180)
    assert(latitude.split("\\.")(1).length == 2)
    assert(longitude.split("\\.")(1).length == 2)
  }

  @Test
  def testLatitude() = {
    val latitude = location.latitude().toDouble
    logger.info("Testing random latitude: " + latitude)
    assert(latitude >= -90 && latitude <= 90)
  }

  @Test
  def testLatitudeMaxValue() = {
    val latitude = location.latitude(-60, 60).toDouble
    logger.info("Testing random latitude with max value 1000 : " + latitude)
    assert(latitude >= -60 && latitude <= 60)
  }

  @Test
  def testLatitudeAccuracy() = {
    val latitude = location.latitude(-60, 60, 2)
    logger.info("Testing random latitude with accuracy = 2 : " + latitude)
    assert(latitude.toDouble >= -60 && latitude.toDouble <= 60)
    assert(latitude.toString.split("\\.")(1).length == 2)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testLatitudeException() = {
    val latitude = location.latitude(-60, 60, 20)
  }

  @Test
  def testLongitude() = {
    val longitude = location.longitude().toDouble
    logger.info("Testing random longitude: " + longitude)
    assert(longitude >= -180 && longitude <= 180)
  }

  @Test
  def testLongitudeMaxValue() = {
    val longitude = location.longitude(-60, 60).toDouble
    logger.info("Testing random longitude with max value 1000 : " + longitude)
    assert(longitude >= -60 && longitude <= 60)
  }

  @Test
  def testLongitudeAccuracy() = {
    val longitude = location.longitude(-60, 60, 2)
    logger.info("Testing random longitude with accuracy = 2 : " + longitude)
    assert(longitude.toDouble >= -60 && longitude.toDouble <= 60)
    assert(longitude.toString.split("\\.")(1).length == 2)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def testLongitudeException() = {
    val longitude = location.longitude(-60, 60, 20)
  }

  @Test
  def testGeohash() = {
    val geohash = location.geohash()
    logger.info("Testing random geohash: " + geohash)
  }

  @Test
  def testGeoHashWithSpecificValues() = {
    val latitude = location.latitude(2).toDouble
    val longitude = location.longitude(2).toDouble
    val geohash = location.geohash(latitude, longitude)
    val decodedValue = GeohashUtils.decode(geohash, SpatialContext.GEO)
    assert(Math.abs(decodedValue.getX - longitude) <= 0.2)
    assert(Math.abs(decodedValue.getY - latitude) <= 0.2)
  }

}
