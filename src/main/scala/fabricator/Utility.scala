package fabricator

import play.api.libs.json.Json
import scala.io.Source
import scala.util.Random

/**
 * Created by Andrew Zakordonets on 05/06/14.
 */
class Utility(lang: String) {

  protected val valuesJson = Json.parse(Source.fromFile("src/main/resources/"+lang+".json").mkString)
  protected val rand = new Random()

  protected def getRandomValue(value: Any) = value match{
    case value: Int => getRandomInt(value)
    case value: Long => getRandomLong()
    case value: Double => getRandomDouble()
    case value: Float => getRandomFloat()
    case value: Boolean => getRandomBoolean()
    case value: Array[Byte] => getRandomByte(value)
    case value: String => getRandomString(value.toString.length)
    case _ => throw new Exception("Wrong value. You can pass int, boolean, float, double, string only")
  }

  protected def getRandomInt(number: Int):Int = {
    rand.nextInt(number)
  }

  protected def getRandomLong():Long = {
    rand.nextLong()
  }

  protected def getRandomDouble():Double = {
    rand.nextDouble()
  }

  protected def getRandomFloat():Float = {
    rand.nextFloat()
  }

  protected def getRandomString(length: Int):String = {
    rand.nextString(length)
  }

  protected def getRandomBoolean():Boolean = {
    rand.nextBoolean()
  }

  protected def getRandomByte(bytes: Array[Byte]) = {
    rand.nextBytes(bytes)
  }


  protected def getValueFromArray(key: String): String = {
    val array = (valuesJson \\ key)(0).asOpt[Array[String]].get
    val random_index = getRandomInt(array.length);
    array(random_index)
  }

  protected def getArrayFromJson(key: String): Array[String] = {
    (valuesJson \\ key)(0).asOpt[Array[String]].get
  }

  protected def getListFromJson(key: String):List[Array[String]] = {
    (valuesJson \\ key)(0).asOpt[List[Array[String]]].get
  }

  def less(a: Any, b: Any):Boolean = (a, b) match{
    case (a: Int, b: Int) => a < b
    case (a: Double, b: Double) => a < b
    case (a: Float, b: Float) => a < b
    case _ => throw new Exception("Invalid arguments were passed .They should be either int, double, float and both same type")
  }


}
