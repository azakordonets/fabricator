fabricator
==========

[![Build Status](https://travis-ci.org/azakordonets/fabricator.svg?branch=master)](https://travis-ci.org/azakordonets/fabricator) &nbsp;&nbsp;&nbsp;&nbsp;   [![Coverage Status](https://coveralls.io/repos/azakordonets/fabricator/badge.png)](https://coveralls.io/r/azakordonets/fabricator)  &nbsp;&nbsp;&nbsp;&nbsp; [![Get automatic notifications about new "fabricator" versions](https://www.bintray.com/docs/images/bintray_badge_color.png)](https://bintray.com/biercoff/Fabricator/fabricator/view?source=watch)

Fabricator is a minimalist generator of random  strings, numbers, etc. to help reduce some monotony particularly while writing automated tests or
anywhere else you need anything random. It's written on scala but fully backward compatible with Java.

## How to install :

For maven projects you need to add next dependency in your ```pom.xml``` file :

```java
<dependency>
  <groupId>com.github.azakordonets</groupId>
  <artifactId>fabricator_2.10</artifactId>
  <version>1.0.4</version>
</dependency>
```

In case you want to use it in your scala project, then just add this lines to your ```build.sbt``` file :

```scala
resolvers += "Fabricator" at "http://dl.bintray.com/biercoff/Fabricator"

libraryDependencies += "com.github.azakordonets" % "fabricator_2.10" % "1.0.4",
```

Usage
------

Fabricator consist of 9 specific modules :

* Alphanumeric - generates random numbers and strings:
* Calendar - generates random time and date:
* Contact - generates contact and person details information :
* File - generates random csv files
* Finance - generates random credit cards, bsn numbers
* Internet - generates random url's, domains, e-mails, ip's, mac addresses, color codes, social networks id's
* Location - generates random coordinates, geohash
* Mobile - generates random mobile platforms push tokens or id's
* Words - generates random words, sentences and even blocks of text

To start using it you need to import fabricator.* and then make a call to appropriate module. 

Let's look closer what each module can build :

Alphanumeric
------------
This module allows you to generate any random number or string. As for strings, you can generate either fully random string,
or you can generate string basing on a pattern.

```scala

val alpha = fabricator.alphaNumeric() // initialize alpha numeric module

alpha.numerify("###ABC") // 981ABC

alpha.letterify("???123") // LsQ123

alpha.botify("???###") // AbC329

alpha.getInteger // random integer in 0 to 1000 range

alpha.getInteger(100) // random integer in 0 to 100 range

alpha.getInteger(200, 300) // random integer in 200 to 300 range

alpha.getIntegerRangeAsScalaList(1,10,1) // will return scala List[1,2,3,4,5,6,7,8,9,10]
 
alpha.getIntegerRangeAsJavaList(1,10,1) // will return List<Object>[1,2,3,4,5,6,7,8,9,10] . Each element need to be casted to Integer

alpha.hash // d750c843c83a3a980082361e72aa41ac48975eab

alpha.guid // ed7592b7-11e4-5f7f-b83f-488733c8bc56
```

Besides integer numbers it can generate double, float, gausian, string

Calendar
--------

 This module allows you to generate random time or time.

```scala

val calendar = fabricator.calendar() // initialize calendar module

calendar.time(false) // 03:15

calendar.time(true) // 15:15

calendar.month(false) // December

calendar.month(true) // 12

calendar.date // 10-02-2014

calendar.date("dd.MM.yyyy") // 10.02.2014

calendar.date("dd/MM/yy HH:ss") // 10.02.14 12:15

calendar.date(calendar.year, 2, 13, calendar.hour, calendar.minute) // 13 February of random year with random time

calendar.dateObject // random Joda.time object

calendar.dateWithPeriod(-10, 0, -2, +3, 0, 0) // date that is - 10 years + 0 months -2 weeks + 3 days + 0 hours + 0 minutes
```

Contact
--------

  This module allows you to generate random person data

```scala

val contact = fabricator.contact()

contact.fullName // Betty Corwin

contact.birthday(25) // 26.12.1989 (current year - 25 with default format dd.MM.yyyy)

contact.bsn // 730550370

contact.eMail // Rebecca_Kohler506@yahoo.com

contact.phoneNumber // (792)273-4251 x012

contact.postCode //  44274-6580

contact.state // Alaska

contact.height(true) // 188 cm

contact.height(false) // 1.88 m

contact.weight // 108 kg

contact.bloodType // A-

contact.occupation // Craft Artist

contact.religion // sikhism

contact.zodiac // Taurus

```

File
----

This module allows you to generate random images and csv files.
For images you can specify width and height ( no more then 2560 px) and as a result you will get an image
that has text of it's dimensions on it. It's easy as :

```scala
val file = fabricator.file() // initialize file module

file.image(200,300, "drawing.png") // will create a 200x300 image in the root of the project
```

For csv there are 2 ways of generating files.
First one is by using specific codes that correspond to the methods that are available withing the lib. Here's the full list of supported codes :

* integer
* double
* hash
* guid
* time
* date
* name
* first_name
* last_name
* birthday
* email
* phone
* address
* postcode
* bsn
* height
* weight
* occupation
* visa
* master
* iban
* bic
* url
* ip
* macaddress
* uuid
* color
* twitter
* hashtag
* facebook
* google_analytics
* altitude
* depth
* latitude
* longitude
* coordinates
* geohash
* apple_token
* android
* windows7Token
* windows8Token
* word
* sentence

To create csv file using this codes you need to do next :

```scala

val file = fabricator.file() // initialize file module

val codes = Array("first_name", "last_name", "birthday", "email", "phone", "address", "bsn", "weight", "height")

file.csvFromCodes(codes, 10, csvFilePath) // this will create a csv file with 10 rows, each of it has set of data generated by fabricator methods and
                                          // stored in csvFilePath

```

In case you miss some code for your need, or you want to put your data into csv file generator, you can specify them in Set and then ask fabricator
to generate csv file with this data :

```scala

val file = fabricator.file() // initialize file module

val values = Seq(alpha.getInteger, alpha.getDouble, calendar.ampm, null)

file.csv(values, 10, csvFilePath) // csv file with 10 lines of data from Set will be generated in csvFilePath
```
  
Finance
--------
  
This module allows you to generate random finance data
  
```scala

val finance = fabricator.finance() // initialize finance module

finance.iban // GB91ROYC80901351879409

finance.bic // CLSBUS33XXX

finance.visacreditCard // 4556623851035641

finance.visacreditCard(15) // 455662622900006

finance.pincode // 1234
```

Internet
--------

This module allows you to generate random internet data

```scala

val internet = fabricator.internet() // initialize internet module

internet.appleToken // randon apple push token - ze7w6fn0omtkxjuxgw2dx50iux1ijcmkf9rmcvoshj2vnpflajdlli63g5nxwaqy

internet.url // http://ab82asdK.com

internet.url("http", "test.ru", "getUser", Map("id" -> "123", "ts" -> "09-12-10"))

internet.ip // 234.166.254.103

internet.ipv6 // c7a5:a6C4:F3C0:d4be:c2Fd:A4Dc:daA8:7fd8

internet.macAddress // F1:D9:16:93:58:C3

internet.UUID // 543d5f08-acca-426d-b649-a392cc74ce39

internet.color // #58f946

internet.color(rgb, false) // rgb(224,0,0)

internet.color(shorthex, true) // #000 greyscale

internet.twitter // @AdaSchumm

internet.hashtag // #lowofof

internet.googleAnalyticsTrackCode // UA-15137-66

internet.facebookId // 7848157865252882

```

Location
--------

This module allows you to generate random location data - coordinates, etc

```scala

val location = fabricator.location() // initialize location module

location.altitude // 8171.48498 By default maximum altitude is 8848 and accuracy is 5

location.altitude(1000, 2) // 581.83

location.depth // -2378.77726 by default maximum depth is 2550 and accuracy is 5

location.depth(-1000, 2) // -4318.73

location.coordinates // 25.54691, -26.64447

location.coordinates(2) // 25.54, -26.64

location.latitude // -46.99 by default the range is from -90 to 90

location.longitude // -171.72569

location.geohash // tc23e0uv9fdk

location.geohash(-47.44, -150.77) // 0xsf15u9ur59
```

Mobile
--------

This module allows you to generate random mobile operating systems push tokens

```scala

val mobile = fabricator.mobile() // initialize mobile module

mobile.androidGsmId // APA91fCUNiRP-xKj0qBUoJgGWYnN3zFoznbFL61BkWktXCPTYgw4Xe7phJ3zhOEVYJ4ToZvYTp2f0PPHeNSmYHajXr9fwbDarFh8zTGVz3I54ffViW4Nl8s6XLs7i9lIi3oUeRI5bOx49wIC9EF-IwBcuOT-MQ-Nrw1GUW0cJco1Dti4nAtW7Xx

mobile.applePushToken // a6cc474cd81a9697c2a232744dfdb7ec3f8c72977cd91c23e6ac8e8f75c56697

mobile.wp8_anid2 // Windows Phone 8 ANID2 - YjBUN0hmYWV1VVEyZ2xIYnZWOWMwaGFoVUhlYlFq

mobile.wp7_anid // Windows Phone 7 ANID - A=AC59226C42245673ABE85A32A8EBCACE&E=aed&W=3
```

Words
--------

This module allows you to generate random words, sentences and even blocks of text

```scala

val words = fabricator.words() // initialize word module

words.word // random word

words.words(10) // array with 10 words

words.sentence(20) // sentence out of 20 words

words.paragraph // 100 chars length block of text

words.paragraph(2000) // 2000 chars length block of text
```





