package fabricator

import java.io.FileInputStream
import java.util.Properties

import fabricator.entities.RandomDataKeeper

import scala.util.Random

case class UtilityService(lang: String = "us", private val random: Random = new Random()) {

  private lazy val valuesJson = RandomDataKeeper.getJson(lang)

  private lazy val wordsJson = RandomDataKeeper.getJson("words_" + lang)

  if (valuesJson == null) throw new Exception(lang + ".json doesn't exist. ")

  if (wordsJson == null) throw new Exception("words_" + lang + ".json file doesn't exist")

  def getValueFromArray(key: String): String = {
    val array = (valuesJson \\ key).head.asOpt[Array[String]].get
    val random_index = random.nextInt(array.length)
    array(random_index)
  }

  def getArrayFromJson(key: String): Array[String] = {
    (valuesJson \\ key).head.asOpt[Array[String]].get
  }

  def getWordsArray: Array[String] = {
    (wordsJson \\ "words").head.asOpt[Array[String]].get
  }

  def getListFromJson(key: String): List[Array[String]] = {
    (valuesJson \\ key).head.asOpt[List[Array[String]]].get
  }

  def getRandomArrayElement(array: Array[String]): String = {
    val random_index = random.nextInt(array.length)
    array(random_index)
  }

  def getProperty(name: String): String = {
    try {
      val properties = new Properties() //Source.fromInputStream(getClass().getClassLoader().getResourceAsStream(lang + ".json")
      properties.load(new FileInputStream(getClass.getClassLoader.getResource("config.properties").getPath))
      properties.getProperty(name)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        sys.exit(1)
    }
  }

  def isLess(a: Any, b: Any): Boolean = (a, b) match {
    case (a: Int, b: Int) => a < b
    case (a: Double, b: Double) => a < b
    case (a: Float, b: Float) => a < b
    case _ => throw new Exception("Invalid arguments were passed .They should be either int, double, float and both same type")
  }

  def isLessOrEqual(a: Any, b: Any): Boolean = (a, b) match {
    case (a: Int, b: Int) => a <= b
    case (a: Double, b: Double) => a <= b
    case (a: Float, b: Float) => a <= b
    case _ => throw new Exception("Invalid arguments were passed .They should be either int, double, float and both same type")
  }


}
