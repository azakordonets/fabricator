package fabricator
import fabricator.Fabricator

/**
 * Created by Andrew Zakordonets on 29/05/14.
 */
object Main {

  def main(args: Array[String]) = {

    val fabr = new Fabricator();
    println(fabr.firstName())
//    println(fabr.string())

  }


}
