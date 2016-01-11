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
  private final val MARIAN_TRENCH_DEEP: Int = -11022
  private final val EVEREST_MOUNTAIN_HEIGHT: Int = 8849

  def altitude: String = altitude(8848, 5)

  def altitude(max: Int, accuracy: Int): String = {
    checkAccuracyLength(accuracy)
    if (max > EVEREST_MOUNTAIN_HEIGHT) {
      val errorMessage: String = ("The highest point on Earth is mountain Everest, which is %d meters high." +
        " You can' go more then that").format(EVEREST_MOUNTAIN_HEIGHT)
      throw new scala.IllegalArgumentException(errorMessage)
    }
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
    if (min < MARIAN_TRENCH_DEEP) {
      val errorMessage: String = "The deepest place in the world is Marian Trench and it's %d meters deep. You can't go lower then this".format(MARIAN_TRENCH_DEEP)
      throw new scala.IllegalArgumentException(errorMessage)
    }
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
