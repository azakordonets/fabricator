package fabricator

import java.util

import scala.util.Random
import scala.collection.JavaConverters._

/**
 * Created by Andrew Zakordonets on 02/06/14.
 * This class generates random numbers and strings
 */

case class Alphanumeric(private val random: Random = new Random()) {

  def getInteger: Int = random.nextInt(1000)
  
  def getInteger(max: Int): Int = random.nextInt(max)
  
  def getInteger(min: Int, max: Int): Int = random.nextInt(max - min) + min
  
  def getIntegerRangeAsJavaList(min: Int, max: Int, step: Int): java.util.List[Int] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    (min to max by step toList).asJava

  }
  def getIntegerRange(min: Int, max: Int, step: Int): List[Int] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def getDouble: Double = random.nextDouble()
  
  def getDouble(max: Double): Double = getDouble(0, max)
  
  def getDouble(min: Double, max: Double): Double = min + random.nextDouble() * (max - min)
  
  def getDoubleRangeAsJavaList(min: Double, max: Double, step: Double): java.util.List[Double] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    (min to max by step toList).asJava
  }
  
  def getDoubleRange(min: Double, max: Double, step: Double): List[Double] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def getFloat: Float = random.nextFloat()
  
  def getFloat(max: Float): Float = getFloat(0, max)
  
  def getFloat(min: Float, max: Float): Float = min + random.nextFloat() * (max - min)
  
  def getFloatRangeAsJavaList(min: Float, max: Float, step: Float): java.util.List[Float] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
      (min to max by step toList).asJava
  }
  
  def getFloatRange(min: Float, max: Float, step: Float): List[Float] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def getBoolean: Boolean = random.nextBoolean()
  
  def getGausian: Double = random.nextGaussian()

  def getString: String = string("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", 30)
  
  def getStrings: List[String] = getStrings(5, 100, 10)

  def getStringsAsJavaList: util.List[String] = getStrings(5, 100, 10).asJava
  
  def getStrings(minLength: Int, maxLength: Int, amount: Int): List[String] = {
    if (minLength != maxLength )List.fill(amount)(getString(getInteger(minLength, maxLength))) else
      List.fill(amount)(getString(minLength))
  }

  def getStringsAsJavaList(minLength: Int, maxLength: Int, amount: Int): java.util.List[String] = {
    if (minLength != maxLength )List.fill(amount)(getString(getInteger(minLength, maxLength))).asJava else
      List.fill(amount)(getString(minLength)).asJava
  }

  def getString(length: Int): String = getString("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", length)
  
  def getString(charSeq: String, max: Int) = string(charSeq, max)

  private def string(charsSequence: String, max: Int): String = {
    val builder = new StringBuilder
    var counter = 0
    while (counter < max) {
      builder.append(charsSequence.charAt(random.nextInt(charsSequence.length - 1)))
      counter += 1
    }
    builder.toString()
  }

  def hash: String = string("0123456789abcdef", 40)
  
  def hash(length: Int) = string("0123456789abcdef", length)
  
  def hashList: List[String] = hashList(40,40, 100)
  
  def hashList(minLength: Int, maxLength: Int, amount: Int): List[String] = {
    if (minLength != maxLength )List.fill(amount)(hash(getInteger(minLength, maxLength))) else
      List.fill(amount)(hash(minLength))
  }

  def hashAsJavaList = hashList(40,40, 100).asJava

  def hashAsJavaList(minLength: Int, maxLength: Int, amount: Int) = {
    if (minLength != maxLength )List.fill(amount)(hash(getInteger(minLength, maxLength))).asJava else
      List.fill(amount)(hash(minLength)).asJava
  }

  def guid: String = guid(5)
  
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

  def guidList: List[String] = List.fill(10)(guid(5))

  def guidAsJavaList = guidList.asJava
  
  def guidList(version: Int, amount: Int) = List.fill(amount)(guid(version))
  
  def guidAsJavaList(version: Int, amount: Int) = guidList(version, amount).asJava

  def botify(string: String): String = letterify(numerify(string))
  
  def botifyList(string: String, amount: Int): List[String] = List.fill(amount)(botify(string))
  
  def botifyAsJavaList(string: String, amount: Int) = botifyList(string, amount).asJava
  
  def numerify(string: String): String = {
    string.map { case '#' => random.nextInt(10).toString case letter => letter}.mkString
  }
  
  def numerifyList(string: String, amount: Int): List[String] = List.fill(amount)(numerify(string))
  
  def numerifyAsJavaList(string: String, amount: Int) = numerifyList(string, amount)

  def letterify(string: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    string.map { case '?' => chars(random.nextInt(chars.length)) case letter => letter}.mkString
  }
  
  def letterifyList(string: String, amount: Int): List[String] = List.fill(amount)(letterify(string))
  
  def letterifyAsJavaList(string: String, amount: Int) = letterifyList(string, amount)

}
