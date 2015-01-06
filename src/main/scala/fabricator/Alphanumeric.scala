package fabricator

import scala.util.Random

/**
 * Created by Andrew Zakordonets on 02/06/14.
 * This class generates random numbers and strings
 */
object Alphanumeric {
  def apply(): Alphanumeric = {
    new Alphanumeric(new Random())
  }
}

class Alphanumeric(private val random: Random) {

  def getInteger: Int = random.nextInt(1000)
  def getInteger(max: Int): Int = random.nextInt(max)
  def getInteger(min: Int, max: Int): Int = random.nextInt(max - min) + min

  def getDouble: Double = random.nextDouble()
  def getDouble(max: Double): Double = getDouble(0, max)
  def getDouble(min: Double, max: Double): Double = min + random.nextDouble() * (max - min)

  def getFloat: Float = random.nextFloat()
  def getFloat(max: Float): Float = getFloat(0, max)
  def getFloat(min: Float, max: Float): Float = min + random.nextFloat() * (max - min)

  def getBoolean: Boolean = random.nextBoolean()
  def getGausian: Double = random.nextGaussian()

  def getString: String = {
    string("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", 30)
  }

  def getString(length: Int): String = {
    string("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", length)
  }

  def string(charsSequence: String, max: Int): String = {
    val builder = new StringBuilder
    var counter = 0
    while (counter < max) {
      builder.append(charsSequence.charAt(random.nextInt(charsSequence.length - 1)))
      counter += 1
    }
    builder.toString()
  }

  def hash(): String = {
    string("0123456789abcdef", 40)
  }

  def hash(length: Int) = {
    string("0123456789abcdef", length)
  }

  def guid(): String = guid(5)
  def guid(version: Int): String = {
    val guid_pool = "abcdef1234567890"
    val variant_pool = "ab89"
    string(guid_pool, 8) + "-" +
      string(guid_pool, 4) + "-" +
      // The Version
      version +
      string(guid_pool, 3) + "-" +
      // The Variant
      string(variant_pool, 1) +
      string(guid_pool, 3) + "-" +
      string(guid_pool, 12)
  }

  def botify(string: String): String = {
    letterify(numerify(string))
  }

  def numerify(string: String): String = {
    string.map { case '#' => random.nextInt(10).toString case letter => letter}.mkString
  }

  def letterify(string: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    string.map { case '?' => chars(random.nextInt(chars.length)) case letter => letter}.mkString
  }

}
