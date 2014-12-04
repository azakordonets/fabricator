import fabricator.{Internet, Words}

import scala.util.Random


val alphabet = "abcdefABCDEF0123456789"
(1 to 8).map(i => "" + (1 to 4).map(y=>"" +alphabet.charAt(Random.nextInt(alphabet.length))).mkString).mkString(":")

//val word = new Words
//val internet = new Internet()
//(1 to 8).map(i => "" + Random.alphanumeric.take(4).mkString).mkString(":")
//(1 to 16).map(i => Random.nextInt(10)).mkString

//val words = word.word()

//(1 to 3).map(i => "#"+ word.word())



