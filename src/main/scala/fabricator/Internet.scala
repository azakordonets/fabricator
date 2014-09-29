package fabricator

import play.libs.Json

/**
 * Created by Andrew Zakordonets on 22/09/14.
 */
protected class Internet extends Fabricator {

  protected var internetMap = getListFromJson("internet")

  def url (): String = {
    alphaNumeric().botify("????#####??????.")+getValueFromArray("domain_suffix")
  }

  def url(host:String, callName:String, params:Json):String = {
    ""
  }

}

