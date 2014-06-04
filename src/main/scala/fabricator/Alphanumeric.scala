package fabricator

/**
 * Created by Andrew Zakordonets on 02/06/14.
 */
protected class Alphanumeric extends Fabricator{

  def number(){
    number(0, 100)
  }

  def number(min: Int, max: Int): Int = {
    rand.nextInt(max - min) + min
  }


  def intoNumbers(string: String): String = {
    string.map(letter=>letter match {case '#' => rand.nextInt(10).toString case _  => letter}).mkString
  }

  def intoLetters(string: String): String = {
    val chars = ('a' to 'z') ++ ('A' to 'Z')
    string.map(letter=>letter match {case '?' => chars(rand.nextInt(chars.length)) case _  => letter}).mkString
  }

}
