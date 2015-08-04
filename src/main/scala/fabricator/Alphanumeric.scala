package fabricator

import java.util

import scala.collection.JavaConverters._
import scala.language.postfixOps
import scala.util.Random

/**
 * Created by Andrew Zakordonets on 02/06/14.
 * This class generates random numbers and strings
 */

case class Alphanumeric(private val random: Random = new Random()) {

  def randomInt: Int = random.nextInt(1000)
  
  def randomInt(max: Int): Int = random.nextInt(max)
  
  def randomInt(min: Int, max: Int): Int = random.nextInt(max - min) + min
  
  def randomIntsRangeAsJavaList(min: Int, max: Int, step: Int): java.util.List[Int] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    (min to max by step toList).asJava

  }
  def randomIntsRange(min: Int, max: Int, step: Int): List[Int] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def randomLong: Long = random.nextLong()

  def randomLong(max: Long): Long = randomLong(2147483648L, max)

  def randomLong(min: Long, max: Long): Long = min + (Math.random() * (max - min)).toLong


  def randomLongsRange(min: Long, max: Long, step: Long): List[Long] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def randomLongsRangeAsJavaList(min: Long, max: Long, step: Long) = {
    randomLongsRange(min, max, step).asJava
  }

  def randomDouble: Double = random.nextDouble()
  
  def randomDouble(max: Double): Double = randomDouble(0, max)
  
  def randomDouble(min: Double, max: Double): Double = min + random.nextDouble() * (max - min)
  
  def randomDoublesRangeAsJavaList(min: Double, max: Double, step: Double): java.util.List[Double] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    (min to max by step toList).asJava
  }
  
  def randomDoublesRange(min: Double, max: Double, step: Double): List[Double] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def randomFloat: Float = random.nextFloat()
  
  def randomFloat(max: Float): Float = randomFloat(0, max)
  
  def randomFloat(min: Float, max: Float): Float = min + random.nextFloat() * (max - min)
  
  def randomFloatsRangeAsJavaList(min: Float, max: Float, step: Float): java.util.List[Float] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
      (min to max by step toList).asJava
  }
  
  def randomFloatsRange(min: Float, max: Float, step: Float): List[Float] = {
    if (step <= 0) throw new IllegalArgumentException("Step should be more then 0")
    min to max by step toList
  }

  def randomBoolean: Boolean = random.nextBoolean()
  
  def randomGausian: Double = random.nextGaussian()

  def randomString: String = string("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", 30)
  
  def randomStrings: List[String] = randomStrings(5, 100, 100)

  def randomStringsAsJavaList: util.List[String] = randomStrings(5, 100, 100).asJava
  
  def randomStrings(minLength: Int, maxLength: Int, amount: Int): List[String] = {
    if (minLength != maxLength )List.fill(amount)(randomString(randomInt(minLength, maxLength))) else
      List.fill(amount)(randomString(minLength))
  }

  def randomStringsAsJavaList(minLength: Int, maxLength: Int, amount: Int): java.util.List[String] = {
    if (minLength != maxLength )List.fill(amount)(randomString(randomInt(minLength, maxLength))).asJava else
      List.fill(amount)(randomString(minLength)).asJava
  }

  def randomString(length: Int): String = randomString("0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_", length)
  
  def randomString(charSeq: String, max: Int) = string(charSeq, max)

  private def string(charsSequence: String, max: Int): String = {
    val builder = new StringBuilder
    var counter = 0
    while (counter < max) {
      builder.append(charsSequence.charAt(random.nextInt(charsSequence.length - 1)))
      counter += 1
    }
    builder.toString()
  }

  def randomHash: String = string("0123456789abcdef", 40)
  
  def randomHash(length: Int) = string("0123456789abcdef", length)
  
  def randomHashList: List[String] = randomHashList(40,40, 100)
  
  def randomHashList(minLength: Int, maxLength: Int, amount: Int): List[String] = {
    if (minLength != maxLength )List.fill(amount)(randomHash(randomInt(minLength, maxLength))) else
      List.fill(amount)(randomHash(minLength))
  }

  def randomHashAsJavaList = randomHashList(40,40, 100).asJava

  def randomHashAsJavaList(minLength: Int, maxLength: Int, amount: Int) = {
    if (minLength != maxLength )List.fill(amount)(randomHash(randomInt(minLength, maxLength))).asJava else
      List.fill(amount)(randomHash(minLength)).asJava
  }

  def randomGuid: String = randomGuid(5)
  
  def randomGuid(version: Int): String = {
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

  def randomGuidList: List[String] = List.fill(100)(randomGuid(5))

  def randomGuidAsJavaList = randomGuidList.asJava
  
  def randomGuidList(version: Int, amount: Int) = List.fill(amount)(randomGuid(version))
  
  def randomGuidAsJavaList(version: Int, amount: Int) = randomGuidList(version, amount).asJava

  def botify(pattern: String): String = letterify(numerify(pattern))
  
  def botifyList(pattern: String, amount: Int): List[String] = List.fill(amount)(botify(pattern))
  
  def botifyAsJavaList(pattern: String, amount: Int) = botifyList(pattern, amount).asJava
  
  def numerify(pattern: String): String = {
    pattern.map { case '#' => random.nextInt(10).toString case letter => letter}.mkString
  }
  
  def numerifyList(pattern: String, amount: Int): List[String] = List.fill(amount)(numerify(pattern))
  
  def numerifyAsJavaList(pattern: String, amount: Int) = numerifyList(pattern, amount).asJava

  def letterify(pattern: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    pattern.map { case '?' => chars(random.nextInt(chars.length)) case letter => letter}.mkString
  }
  
  def letterifyList(pattern: String, amount: Int): List[String] = List.fill(amount)(letterify(pattern))
  
  def letterifyAsJavaList(pattern: String, amount: Int) = letterifyList(pattern, amount).asJava

}
