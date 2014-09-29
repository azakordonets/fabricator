package fabricator

/**
 * Created by Andrew Zakordonets on 02/06/14.
 * This class generates random numbers and strings
 */
protected class Alphanumeric extends Fabricator{


  def number(): Int = {
    rand.nextInt(1000)
  }

  def number(max: Int):Int = {
    rand.nextInt(max)
  }

  def number(min: Int, max: Int): Int = {
    rand.nextInt(max - min) + min
  }

  def double(): Double = {
    rand.nextDouble()
  }
  
  def double(max:Double): Double = {
    double(0, max)
  }
  
  def double(min: Double, max: Double): Double = {
    min + ((getRandomDouble()*(max - min)))
  }

  def float() :Float = {
    rand.nextFloat()
  }
  
  def float(max: Float): Float = {
    float(0, max)
  }
  
  def float(min: Float, max: Float): Float = {
    min + ((getRandomFloat()*(max - min)))
  }

  def boolean() : Boolean = {
    rand.nextBoolean()
  }

  def gausian(): Double  = {
    rand.nextGaussian()
  }

  def string(): String = {
    rand.nextString(30)
  }

  def string (max: Int): String = {
    rand.nextString(max)
  }


  def numerify(string: String): String = {
    string.map(letter=>letter match {case '#' => getRandomValue(10).toString case _  => letter}).mkString
  }

  def letterify(string: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    string.map(letter=>letter match {case '?' => chars(getRandomInt(chars.length)) case _  => letter}).mkString
  }

  def botify(string: String): String = {
    letterify(numerify(string))
  }

}
