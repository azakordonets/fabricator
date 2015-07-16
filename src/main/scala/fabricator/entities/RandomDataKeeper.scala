package fabricator.entities

import java.util.regex.Pattern

import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.util.{ClasspathHelper, ConfigurationBuilder}
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable
import scala.io.Source

object RandomDataKeeper {

  private val randomDataStorage: mutable.Map[String, String] = mutable.HashMap[String, String]()

  private def getResourcesList: Set[String] = {
    val reflections = new Reflections(new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage("fabricator"))
      .setScanners(new ResourcesScanner()))
    val fileNames = reflections.getResources(Pattern.compile(".*\\.json")).toArray.map(_.toString.replaceAll("data_files/", ""))
    fileNames.toSet[String]
  }

  private def parseLanguageDataFiles() = {
    val filesList = getResourcesList
    filesList.foreach(fileName => randomDataStorage(fileName.split("\\.")(0)) = parseFile(fileName))
  }

  private def parseFile(fileName: String): String  = {
    Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("data_files/" + fileName))("UTF-8").mkString
  }

  def getJson(locale: String = "us"): JsValue = {
    if (randomDataStorage.isEmpty) {
      parseLanguageDataFiles()
    }
    val jsonString = randomDataStorage.getOrElse(locale, throw new IllegalArgumentException(s"You're trying to load '$locale' language file that doesn't exist"))
    Json.parse(jsonString)
  }

}
