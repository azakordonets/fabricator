package fabricator

import java.io.FileInputStream
import java.util.Properties

import fabricator.entities.RandomDataKeeper
import play.api.libs.json.JsValue

import scala.util.{Random, Try}

case class UtilityService(lang: String = "us", private val random: Random = new Random()) {

  private lazy val valuesJson:JsValue = RandomDataKeeper.getJson(lang)

  private lazy val wordsJson:JsValue = RandomDataKeeper.getJson("words_" + lang)

  if (valuesJson == null) throw new Exception(lang + ".json doesn't exist. ")

  if (wordsJson == null) throw new Exception("words_" + lang + ".json file doesn't exist")

  def getArrayFromJson(json: JsValue, key: String): Array[String] = {
    (json \\ key).head.asOpt[Array[String]].get
  }

  def getArrayFromJson(key: String): Array[String] = {
    getArrayFromJson(valuesJson, key)
  }

  def getWordsArray: Array[String] = {
    getArrayFromJson(wordsJson, "words")
  }

  def getValueFromArray(key: String): String = {
    val array = getArrayFromJson(key)
    getRandomArrayElement(array)
  }

  def getRandomArrayElement(array: Array[String]): String = {
    val randomIndex = random.nextInt(array.length)
    array(randomIndex)
  }

  def getProperty(name: String): String = {
    val properties = new Properties()
    Try(properties.load(new FileInputStream(getClass.getClassLoader.getResource("config.properties").getPath)))
    properties.getProperty(name)
  }

  def isLess(a: Any, b: Any): Boolean = (a, b) match {
    case (a: Int, b: Int) => a < b
    case (a: Double, b: Double) => a < b
    case (a: Float, b: Float) => a < b
    case (a: Long, b: Long) => a < b
    case _ => throw new Exception("Invalid arguments were passed .They should be either int, double, float and both same type")
  }

  def isLessOrEqual(a: Any, b: Any): Boolean = (a, b) match {
    case (a: Int, b: Int) => a <= b
    case (a: Double, b: Double) => a <= b
    case (a: Float, b: Float) => a <= b
    case (a: Long, b: Long) => a <= b
    case _ => throw new Exception("Invalid arguments were passed .They should be either int, double, float and both same type")
  }


}
