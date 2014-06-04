package fabricator

import com.github.nscala_time.time.Imports._

/**
 * Created by Andrew Zakordonets on 03/06/14.
 */
protected class Date extends Fabricator{

  def getDate(): String = {
    DateTime.now.toString("dd-mm-yyyy")
  }






}
