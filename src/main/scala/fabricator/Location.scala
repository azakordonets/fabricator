package fabricator

import com.spatial4j.core.io.GeohashUtils

import scala.util.Random

object Location {

  def apply(): Location = {
    new Location(UtilityService(), Alphanumeric(), new Random())
  }

  def apply(locale: String): Location = {
    new Location(UtilityService(locale), Alphanumeric(), new Random())
  }
}

class Location(private val utility: UtilityService,
               private val alpha: Alphanumeric,
               private val random: Random) {


  def altitude: String = altitude(8848, 5)

  def altitude(max: Int, accuracy: Int): String = {
    checkAccuracyLength(accuracy)
    getFakeCoordinateValue(0, max, accuracy)
  }

  private def checkAccuracyLength(accuracy: Int): Unit = {
    if (accuracy > 10) {
      throw new scala.IllegalArgumentException("Accuracy cannot be more then 10 digits")
    }
  }

  def altitude(max: Int): String = altitude(max, 5)

  def depth: String = depth(-2550, 5)

  def depth(min: Int): String = depth(min, 5)
    
  def depth(min: Int, accuracy: Int): String = {
    checkAccuracyLength(accuracy)
    "-" + alpha.randomInt(0, Math.abs(min)) + "." + alpha.randomInt(100000, 1000000000).toString.substring(0, accuracy)
  }

  def coordinates: String = latitude + ", " + longitude

  def coordinates(accuracy: Int): String = latitude(accuracy) + ", " + longitude(accuracy)

  def latitude(accuracy: Int): String = latitude(-89, 89, accuracy)

  def longitude(accuracy: Int): String = longitude(-179, 179, accuracy)

  def geohash: String = GeohashUtils.encodeLatLon(latitude.toDouble, longitude.toDouble)

  def latitude: String = latitude(-89, 89)

  def latitude(min: Int, max: Int): String = latitude(min, max, 5)

  def latitude(min: Int, max: Int, accuracy: Int): String = {
    checkAccuracyLength(accuracy)
    getFakeCoordinateValue(min, max, accuracy)
  }

  def getFakeCoordinateValue(min: Int, max: Int, accuracy: Int): String = {
    alpha.randomInt(min, max) + "." + alpha.randomInt(100000, 1000000000).toString.substring(0, accuracy)
  }

  def longitude: String = longitude(-179, 179)

  def longitude(min: Int, max: Int): String = longitude(min, max, 5)

  def longitude(min: Int, max: Int, accuracy: Int): String = {
    checkAccuracyLength(accuracy)
    getFakeCoordinateValue(min, max, accuracy)
  }

  def geohash(latitude: Double, longitude: Double): String = GeohashUtils.encodeLatLon(latitude, longitude)


}
