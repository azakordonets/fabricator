import fabricator._

import scala.util.Random

val alpha = new Alphanumeric()
val calendar = new Calendar()
val contact = new Contact()
val finance = new Finance()
val internet = new Internet()
val location = new Location()
val mobile = new Mobile()
val words = new Words()

val alphabet = "abcdefABCDEF0123456789"
(1 to 8).map(i => "" + (1 to 4).map(y => "" + alphabet.charAt(Random.nextInt(alphabet.length))).mkString).mkString(":")

//val word = new Words
//val internet = new Internet()
//(1 to 8).map(i => "" + Random.alphanumeric.take(4).mkString).mkString(":")
//(1 to 16).map(i => Random.nextInt(10)).mkString

//val words = word.word()

//(1 to 3).map(i => "#"+ word.word())

val values = Array("first_name", "last_name", "birthday", "email", "phone", "address", "bsn", "weight", "height")
values.map(x => generateValue(x))



private def generateValue(code: String): String = {
  code match {
    case "integer" => alpha.integer().toString
    case "double" => alpha.double().toString
    case "hash" => alpha.hash()
    case "guid" => alpha.guid()
    case "time" => calendar.time(true)
    case "date" => calendar.date()
    case "name" => contact.fullName()
    case "first_name" => contact.firstName()
    case "last_name" => contact.lastName()
    case "birthday" => contact.birthday(alpha.integer(21, 80))
    case "email" => contact.eMail()
    case "phone" => contact.phoneNumber()
    case "address" => contact.streetName() + " " + contact.houseNumber() + ", " + contact.apartmentNumber()
    case "postcode" => contact.postcode()
    case "bsn" => contact.bsn()
    case "height" => contact.height(false)
    case "weight" => contact.weight()
    case "occupation" => contact.occupation()
    case "visa" => finance.visacreditCard()
    case "master" => finance.mastercreditCard()
    case "iban" => finance.iban()
    case "bic" => finance.bic()
    case "url" => internet.url()
    case "ip" => internet.ip()
    case "macaddress" => internet.macAddress()
    case "uuid" => internet.UUID()
    case "color" => internet.color()
    case "twitter" => internet.twitter()
    case "hashtag" => internet.hashtag()
    case "facebook" => internet.facebookId()
    case "google_analytics" => internet.googleAnalyticsTrackCode()
    case "altitude" => location.altitude()
    case "depth" => location.depth()
    case "latitude" => location.latitude()
    case "longitude" => location.longitude()
    case "coordinates" => location.coordinates()
    case "geohash" => location.geohash()
    case "apple_token" => mobile.applePushToken()
    case "android" => mobile.androidGsmId()
    case "windows7Token" => mobile.wp7_anid()
    case "windows8Token" => mobile.wp8_anid2()
    case "word" => words.word()
    case "sentence" => words.sentence(10)
    case _ => throw new IllegalArgumentException(code + " is an incorrect code value")
  }
}


