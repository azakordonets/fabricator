package fabricator.entities

import io.github.classgraph._
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

object RandomDataKeeper {

  private val randomDataStorage: mutable.Map[String, String] = mutable.HashMap[String, String]()
  private val dataPath = "data_files/"

  private def getResourcesList: Set[String] = {
    val fileNames = new ListBuffer[String]
    val scanResult = new ClassGraph().acceptPaths("data_files").scan
    scanResult
      .getResourcesWithExtension("json")
      .forEachByteArrayThrowingIOException(
        (res: Resource, content: Array[Byte]) => fileNames += res.getPath.replaceAll(dataPath, "")
      )
    scanResult.close()
    fileNames.toSet
  }

  private def parseLanguageDataFiles() = {
    val filesList = getResourcesList
    filesList.foreach(fileName => randomDataStorage(fileName.split("\\.")(0)) = parseFile(fileName))
  }

  private def parseFile(fileName: String): String = {
    Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(dataPath + fileName))("UTF-8").mkString
  }

  def getJson(locale: String = "us"): JsValue = {
    if (randomDataStorage.isEmpty) {
      parseLanguageDataFiles()
    }
    val jsonString = randomDataStorage.getOrElse(locale, throw new IllegalArgumentException(s"You're trying to load '$locale' language file that doesn't exist"))
    Json.parse(jsonString)
  }
}
