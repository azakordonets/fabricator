package fabricator

import java.math.MathContext

import com.sun.javaws.exceptions.InvalidArgumentException

import scala.util.Random

class Location ( private val utility:UtilityService,
                 private val alpha: Alphanumeric,
                 private val random: Random)  {


  def this () = {
    this( new UtilityService(), new Alphanumeric, new Random());

  }

  def altitude(): String = {
    altitude(8848, 5)
  }

  def altitude(max: Int): String = {
    altitude(max, 5)
  }

  def altitude(max: Int, accuracy: Int): String = {
    if (accuracy > 10 ) {
      throw new IllegalArgumentException("Accuracy cannot be more then 10 digits")
    }
    alpha.integer(max) + "." + alpha.integer(100000,1000000000).toString.substring(0,accuracy)
  }



}
