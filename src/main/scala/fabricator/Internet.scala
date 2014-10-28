package fabricator

import play.libs.Json

import scala.util.Random

/**
 * Created by Andrew Zakordonets on 22/09/14.
 */
class Internet (private val utility: UtilityService, private val alpha: Alphanumeric){

  def this() {
    this(new UtilityService(), new Alphanumeric())
  }

  def url (): String = {
    alpha.botify("????#####??????.")+utility.getValueFromArray("domain_suffix")
  }

  def url(protocol: String, host:String, callName:String, params:Map[String, String]):String = {
    var paramsString: String = ""
    for ((key, value) <- params) {
      paramsString += key + "=" + value + "&"
    }
    protocol + "://" + host + "/" + callName + "?" + paramsString.substring(0, paramsString.size-1)
  }

  def ip(): String = {
    val random = new Random()
    val ip = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256)
    if (ip == "0.0.0.0" || ip == "255.255.255.255") this.ip()
    else ip
  }


}

