package fabricator

import play.libs.Json

/**
 * Created by Andrew Zakordonets on 22/09/14.
 */
class Internet (private val utility: UtilityService, private val alpha: Alphanumeric){

  def this() {
    this(new UtilityService(), new Alphanumeric())
  }

  protected var internetMap = utility.getListFromJson("internet")

  def url (): String = {
    alpha.botify("????#####??????.")+utility.getValueFromArray("domain_suffix")
  }

  def url(host:String, callName:String, params:Json):String = {
    ""
  }

}

