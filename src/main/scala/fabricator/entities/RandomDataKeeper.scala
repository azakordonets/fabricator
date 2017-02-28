package fabricator.entities

import java.io.{IOException, InputStream}

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import io.github.lukehutch.fastclasspathscanner.matchprocessor.FileMatchProcessor
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

object RandomDataKeeper {

  private val randomDataStorage: mutable.Map[String, String] = mutable.HashMap[String, String]()

  private def getResourcesList: Set[String] = {
    val fileNames = new ListBuffer[String]
    new FastClasspathScanner("data_files")
      .matchFilenamePattern(".*\\.json", new FileMatchProcessor() {
        @throws[IOException]
        def processMatch(relativePath: String, inputStream: InputStream, l: Long) {
          fileNames += relativePath.replaceAll("data_files/", "")
        }
      })
      .scan()
    fileNames.toSet
  }

  private def parseLanguageDataFiles() = {
    val filesList = getResourcesList
    filesList.foreach(fileName => randomDataStorage(fileName.split("\\.")(0)) = parseFile(fileName))
  }

  private def parseFile(fileName: String): String = {
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
