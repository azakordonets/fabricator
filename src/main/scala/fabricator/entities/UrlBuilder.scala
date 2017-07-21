package fabricator.entities

import java.net.URL
import java.nio.charset.Charset
import java.nio.{ByteBuffer, CharBuffer}

import com.sun.jndi.toolkit.url.Uri
import fabricator._

import scala.util.Random
import scala.collection.JavaConverters._
import scala.collection.mutable

class UrlBuilder() {

  private val service: UtilityService = new UtilityService()

  private val random: Random = new Random()

  private val words: Words = new Words(service, random)

  private var scheme: String = "http"

  private var host: String = words.words(2).mkString("")

  private val domain: String = service.getValueFromArray("domain_suffix")

  private var useCustomHost = false

  private var port: Int = 80

  private var params = mutable.Map[String, Any]("q" -> "test")

  private var path: String = "/getEntity"

  private var encoding: Charset = Charset.forName("UTF-8")

  private var encodeParams: Boolean = false

  def scheme(scheme: String): this.type = {
    this.scheme = scheme
    this
  }

  def host(host: String): this.type = {
    this.host = host
    this.useCustomHost = true
    this
  }

  def port(port: Int): this.type = {
    this.port = port
    this
  }

  def params(params: mutable.Map[String, Any]): this.type = {
    this.params = params
    this
  }

  def paramsInJava(params: java.util.HashMap[String, Any]): this.type = {
    this.params = params.asScala
    this
  }

  def path(path: String): this.type = {
    this.path = path
    this
  }

  def encodeAs(charset: Charset): this.type = {
    this.encoding = charset
    this.encodeParams = true
    this
  }

  def encodeAs(charsetName: String): this.type = {
    this.encoding = Charset.forName(charsetName)
    this.encodeParams = true
    this
  }

  @Override
  override def toString: String = {
    makeUrl
  }

  def toUri: Uri = {
    new Uri(makeUrl)
  }

  def toUrl: URL = {
    new URL(makeUrl)
  }

  private def makeUrl: String = {
    val builder: StringBuilder = new StringBuilder
    builder.append(scheme)
    builder.append("://")
    builder.append(host)
    if (!useCustomHost) builder.append(domain)
    if (port != 80) builder.append(":" + port.toString)
    builder.append(path)
    val paramsString = convertParamsToString(params)
    val encodedParams = urlEncode(paramsString, encoding)
    builder.append(if (encodeParams) encodedParams else paramsString)
    builder.toString()
  }

  private def urlEncode(input: String, charset: Charset): String = {
    val builder: StringBuilder = new StringBuilder()
    val charBuffer: CharBuffer = CharBuffer.allocate(1)
    for (char <- input.toCharArray) {
      char match {
        case ' ' => builder.append('+')
        case `char` if isUrlSafe(char) => builder.append(char)
        case _ => appendEncodedChar(char, builder, charBuffer, charset)
      }
    }
    builder.toString()
  }
    private def appendEncodedChar(char: Char, stringBuilder: StringBuilder, charBuffer: CharBuffer, charset: Charset) {
      charBuffer.put(0, char)
      charBuffer.rewind()
      val byteBuffer: ByteBuffer = charset.encode(charBuffer)
      for (i <- 0 until byteBuffer.limit() by 1) {
        stringBuilder.append('%')
        val placeHolder = "%1$02X"
        stringBuilder.append(placeHolder.format(byteBuffer.get(i)))
    }
  }

  private def isUrlSafe(c: Char): Boolean = {
    ('a' <= c && 'z' >= c) ||
      ('A' <= c && 'Z' >= c) ||
      ('0' <= c && '9' >= c) ||
      (c == '-' || c == '_' || c == '.' || c == '~' || c == ' ')
  }

  private def convertParamsToString(params: mutable.Map[String, Any]): String = {
    val builder: StringBuilder = new StringBuilder()
    builder.append("?")
    for ((key, value) <- params) {
      validateParam(value)
      builder.append(key + "=" + value + "&")
    }
    builder.toString().replaceAll(" ", "+")substring(0, builder.length-1)
  }

  private def validateParam(param: Any) = {
    param match {
      case z: Boolean => // accept it
      case c: Char => // accept it
      case s: Short => // accept it
      case i: Int => // accept it
      case j: Long => // accept it
      case f: Float => // accept it
      case d: Double => // accept it
      case str: String => // accept it
      case _ => throw new IllegalArgumentException("Only String ,Int, Double, Float, Boolean are acceptable for url params")
    }
  }


}
