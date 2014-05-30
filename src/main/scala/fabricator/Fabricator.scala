package fabricator

import scala.io.Source
import play.api.libs.json._
import scala.util.Random


class Fabricator(lang: String) {

  val valuesJson = Json.parse(Source.fromFile("src/main/resources/"+lang+".json").mkString)
  val rand = new Random(System.currentTimeMillis());

  def this() = {
    this("en")
  }

  private def getValueFromArray(key: String): String = {
    val array = (valuesJson \ key).asOpt[JsArray]
    val random_index = rand.nextInt(array.get.value.size);
    array.get(random_index).toString()
  }

  def firstName() =  {
    getValueFromArray("first_name")
  }

  def string() = {
    Json.stringify(valuesJson)
  }

  def lastName() = {
    getValueFromArray("last_name")
  }



  def readFile() = {

    val result = Json.parse(Source.fromFile("src/main/resources/en.json").mkString)
    println(result);

  }

}
