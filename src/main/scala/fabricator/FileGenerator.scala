package fabricator

import scala.util.Random
import javax.sound.midi.Sequence
import java.io.File

class FileGenerator (private val utility: UtilityService,
            private val alpha: Alphanumeric,
            private val random: Random,
            private val contact: Contact,
            private val word: Words){

  def this() {
    this(new UtilityService(),
      new Alphanumeric(),
      new Random(),
      new Contact(),
      new Words())
  }


}
