package fabricator

import java.io.FileInputStream
import java.util.Properties

import play.api.libs.json.Json

import scala.io.Source
import scala.util.Random

/**
 * Created by Andrew Zakordonets on 05/06/14.
 */
case class UtilityService(lang: String = "us", private val random: Random = new Random()) {

  private val valuesJson = Json.parse(Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(lang + ".json"))("UTF-8").mkString)

  if (valuesJson == null) throw new Exception(lang + ".json doesn't exist. ")

  def getValueFromArray(key: String): String = {
    val array = (valuesJson \\ key)(0).asOpt[Array[String]].get
    val random_index = random.nextInt(array.length)
    array(random_index)
  }

  def getArrayFromJson(key: String): Array[String] = {
    (valuesJson \\ key)(0).asOpt[Array[String]].get
  }

  def getListFromJson(key: String): List[Array[String]] = {
    (valuesJson \\ key)(0).asOpt[List[Array[String]]].get
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
