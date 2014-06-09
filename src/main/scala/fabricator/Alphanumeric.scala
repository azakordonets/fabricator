package fabricator

/**
 * Created by Andrew Zakordonets on 02/06/14.
 */
protected class Alphanumeric extends Fabricator{

  def getNumber(): Any = {
    getAnyNumber(0, 1000)
  }

  def getNumber(max: Any):Any = {
    getRandomValue(max)
  }

  def getAnyNumber(min: Any, max: Any):Any = (min, max) match {
    case (min: Int, max: Int) => getRandomInt(max - min) + min
    case (min: Double, max: Double) => min + ((getRandomDouble()*(max - min)))
    case (min: Float, max: Float) => min + ((getRandomFloat()*(max - min)))
    case _ => throw new Exception("Invalid arguments were passed. Their types are incorrect or don't match")
  }


  def intoNumbers(string: String): String = {
    string.map(letter=>letter match {case '#' => getRandomValue(10).toString case _  => letter}).mkString
  }

  def intoLetters(string: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    string.map(letter=>letter match {case '?' => chars(getRandomInt(chars.length)) case _  => letter}).mkString
  }

}
