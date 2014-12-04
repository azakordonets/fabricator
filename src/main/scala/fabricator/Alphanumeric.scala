package fabricator

import scala.util.Random

/**
 * Created by Andrew Zakordonets on 02/06/14.
 * This class generates random numbers and strings
 */
class Alphanumeric(private val random: Random) {

  def this () {
    this(new Random())
  }


  def integer(): Int = {
    random.nextInt(1000)
  }

  def integer(max: Int):Int = {
    random.nextInt(max)
  }

  def integer(min: Int, max: Int): Int = {
    random.nextInt(max - min) + min
  }

  def double(): Double = {
    random.nextDouble()
  }
  
  def double(max:Double): Double = {
    double(0, max)
  }
  
  def double(min: Double, max: Double): Double = {
    min + ((random.nextDouble()*(max - min)))
  }

  def float() :Float = {
    random.nextFloat()
  }
  
  def float(max: Float): Float = {
    float(0, max)
  }
  
  def float(min: Float, max: Float): Float = {
    min + ((random.nextFloat()*(max - min)))
  }

  def boolean() : Boolean = {
    random.nextBoolean()
  }

  def gausian(): Double  = {
    random.nextGaussian()
  }

  def string(): String = {
    random.nextString(30)
  }

  def string (max: Int): String = {
    random.nextString(max)
  }


  def numerify(string: String): String = {
    string.map(letter=>letter match {case '#' => random.nextInt(10).toString case _  => letter}).mkString
  }

  def letterify(string: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    string.map(letter=>letter match {case '?' => chars(random.nextInt(chars.length)) case _  => letter}).mkString
  }

  def botify(string: String): String = {
    letterify(numerify(string))
  }

}
