package fabricator.entities

import java.io.File

import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable
import scala.io.Source

object RandomDataKeeper {

  private val randomDataStorage: mutable.Map[String, String] = mutable.HashMap[String, String]()

  private def getResourcesList: Set[String] = {
    val resourceFolderPath = new File(getClass.getClassLoader.getResource("us.json").toString).getParentFile.getPath.substring(5)
    val resourceFilesList = new File(resourceFolderPath).listFiles()
                                                        .filter(_.isFile)
                                                        .map(_.getName)
                                                        .filter(fileName => fileName.matches(".*\\.json"))
                                                        .toSet
    resourceFilesList
  }

  private def parseLanguageDataFiles() = {
    val filesList = getResourcesList
    filesList.foreach(fileName => randomDataStorage(fileName.split("\\.")(0)) = parseFile(fileName))
  }

  private def parseFile(fileName: String): String  = {
    Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(fileName))("UTF-8").mkString
  }

  def getJson(locale: String = "us"): JsValue = {
    if (randomDataStorage.isEmpty) {
      parseLanguageDataFiles()
    }
    val jsonString = randomDataStorage.getOrElse(locale, throw new IllegalArgumentException(s"You're trying to load '$locale' language file that doesn't exist"))
    Json.parse(jsonString)
  }

}
