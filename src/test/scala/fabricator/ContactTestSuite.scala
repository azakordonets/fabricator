package fabricator

import com.github.nscala_time.time.Imports._
import fabricator.enums.DateFormat
import org.testng.annotations.{DataProvider, Test}

class ContactTestSuite extends BaseTestSuite {

  lazy val firstNameList: Array[String] = util.getArrayFromJson("first_name")
  lazy val lastNameList: Array[String] = util.getArrayFromJson("last_name")
  lazy val prefixList: Array[String] = util.getArrayFromJson("prefix")
  lazy val suffixList: Array[String] = util.getArrayFromJson("suffix")
  lazy val phoneFormatsList: Array[String] = util.getArrayFromJson("phone_formats")
  lazy val streetNameList: Array[String] = util.getArrayFromJson("street_suffix")
  lazy val houseNumberList: Array[String] = util.getArrayFromJson("house_number")
  lazy val appNumberList: Array[String] = util.getArrayFromJson("app_number")
  lazy val postcodeList: Array[String] = util.getArrayFromJson("postcode")
  lazy val countryList: Array[String] = util.getArrayFromJson("country")
  lazy val stateList: Array[String] = util.getArrayFromJson("state")
  lazy val stateAbbrList: Array[String] = util.getArrayFromJson("state_abbr")
  lazy val cityPrefixList: Array[String] = util.getArrayFromJson("city_prefix")
  lazy val citySuffixList: Array[String] = util.getArrayFromJson("city_suffix")
  lazy val companyList: Array[String] = util.getArrayFromJson("company_suffix")
  lazy val religionList: Array[String] = util.getArrayFromJson("religion")
  lazy val zodiacList: Array[String] = util.getArrayFromJson("zodiac")
  lazy val bloodTypeList: Array[String] = util.getArrayFromJson("blood_type")
  lazy val occupationList: Array[String] = util.getArrayFromJson("occupation")

  @DataProvider(name = "languageDp")
  def languageDp(): Array[Array[Any]] = {
    Array(Array("nl"),
      Array("de")
    )
  }

  @Test(dataProvider = "languageDp")
  def testCustomConstructor(lang: String)  {
    val customContact = fabricator.Contact(lang)
    assert(customContact != null)
  }

  @Test
  def testPrefix() = {
    val prefix = contact.prefix
    assert(prefixList.contains(prefix))
  }

  @Test
  def testSuffix() = {
    val suffix = contact.suffix
    assert(suffixList.contains(suffix))
  }
  
  @Test
  def testFirstName() = {
    val name = contact.firstName
    if (debugEnabled) logger.debug("Checking first name value " + name)
    assert(firstNameList.contains(name))
  }

  @Test
  def testLastName() = {
    val name = contact.lastName
    if (debugEnabled) logger.debug("Checking last name value " + name)
    assert(lastNameList.contains(name))
  }

  @Test
  def testFullName() = {
    // no prefix and suffix
    val name = contact.fullName(setPrefix = false, setSuffix = false)
    if (debugEnabled) logger.debug("Checking full name value " + name)
    firstNameList.contains(name.split(" ")(0))
    lastNameList.contains(name.split(" ")(1))
    // with prefix
    val nameWithPrefix = contact.fullName(setPrefix = true, setSuffix = false)
    if (debugEnabled) logger.debug("Checking full name value " + nameWithPrefix)
    prefixList.contains(nameWithPrefix.split(" ")(0))
    firstNameList.contains(nameWithPrefix.split(" ")(1))
    lastNameList.contains(nameWithPrefix.split(" ")(2))
    //with suffix
    val nameWithSuffix = contact.fullName(setPrefix = false, setSuffix = true)
    if (debugEnabled) logger.debug("Checking full name value " + nameWithSuffix)
    firstNameList.contains(nameWithSuffix.split(" ")(0))
    lastNameList.contains(nameWithSuffix.split(" ")(1))
    suffixList.contains(nameWithSuffix.split(" ")(2))
    //with prefix and suffix 
    val nameWithPrefixAndSuffix = contact.fullName(setPrefix = true, setSuffix = true)
    if (debugEnabled) logger.debug("Checking full name value " + nameWithPrefixAndSuffix)
    prefixList.contains(nameWithPrefixAndSuffix.split(" ")(0))
    firstNameList.contains(nameWithPrefixAndSuffix.split(" ")(1))
    lastNameList.contains(nameWithPrefixAndSuffix.split(" ")(2))
    suffixList.contains(nameWithPrefixAndSuffix.split(" ")(3))
  }

  @Test
  def testEmail() = {
    if (debugEnabled) logger.debug("Checking email " + contact.eMail)
    val email = contact.eMail
    assert(email.nonEmpty)
  }

  @Test
  def testPhoneNumber() = {
    val phone = contact.phoneNumber
    if (debugEnabled) logger.debug("Checking phone number value " + phone)
    var reversedPhone = ""
    if (phone.substring(0, 2).equals("1-")) {
      reversedPhone = "1-" + phone.substring(2, phone.length).replaceAll("[0-9]", "#")
    } else {
      reversedPhone = phone.replaceAll("[0-9]", "#")
    }
    assert(phoneFormatsList.contains(reversedPhone))
  }

  @Test
  def testStreetName() = {
    val street = contact.streetName
    if (debugEnabled) logger.debug("Checking street name value " + street)
    assert(streetNameList.contains(street))
  }

  @Test
  def testhouseNumber() = {
    val houseNumber = contact.houseNumber
    val reversedHouseNumber = houseNumber.replaceAll("[0-9]", "#")
    if (debugEnabled) logger.debug("Checking houseNumber value " + houseNumber)
    assert(houseNumberList.contains(reversedHouseNumber))
  }

  @Test
  def testApartmentNumber() = {
    val apartmentNumber = contact.apartmentNumber
    val reversedApp = apartmentNumber.replaceAll("[0-9]", "#")
    if (debugEnabled) logger.debug("Checking apartmentNumber value " + apartmentNumber)
    assert(appNumberList.contains(reversedApp))
  }

  @Test
  def testAddress() = {
    var address = contact.address
    if (debugEnabled) logger.debug("Checking address value " + address)
    address = address.replaceAll(",", "")
    val addressArray = address.split(" ")
    assert(streetNameList.contains(addressArray(0)))
    assert(houseNumberList.contains(addressArray(1).replaceAll("[0-9]", "#")))
    assert(appNumberList.contains((addressArray(2) + " " + addressArray(3)).replaceAll("[0-9]", "#")))
  }

  @Test
  def testPostcode() = {
    val postCode = contact.postcode
    val reversedPostCode = postCode.replaceAll("[0-9]", "#")
    if (debugEnabled) logger.debug("Checking postCode value " + postCode)
    assert(postcodeList.contains(reversedPostCode))
  }
  
  @Test
  def testCountry() = {
    val country = contact.country
    if (debugEnabled) logger.debug("Checking country value " + country)
    assert(countryList.contains(country))
  }

  @Test
  def testState() = {
    val state = contact.state
    if (debugEnabled) logger.debug("Checking state value " + state)
    assert(stateList.contains(state))
  }

  @Test
  def testStateAbbr() = {
    val stateAbbr = contact.stateShortCode
    if (debugEnabled) logger.debug("Checking stateAbbr value " + stateAbbr)
    assert(stateAbbrList.contains(stateAbbr))
  }
  
  @Test
  def testCity() = {
    val city = contact.city
    if (debugEnabled) logger.debug("Checking city value " + city)
    var prefixAvailability = 0
    var suffixAvailability = 0
    for (prefix <- cityPrefixList){
      if (city.contains(prefix)) prefixAvailability = prefixAvailability + 1
    }
    for (suffix <- citySuffixList){
      if (city.contains(suffix)) suffixAvailability = suffixAvailability + 1
    }
    assert(prefixAvailability >= 1)
    assert(suffixAvailability >= 1)
  }
  

  @Test
  def testCompany() = {
    val company = contact.company
    if (debugEnabled) logger.debug("Checking company value " + company)
    assert(companyList.contains(company))
  }

  @DataProvider(name = "birthdayDP")
  def birthdayDP(): Array[Array[Any]] = {
    Array(Array(0),
      Array(10),
      Array(25),
      Array(50),
      Array(100),
      Array(1000)
    )
  }

  @Test(dataProvider = "birthdayDP")
  def testBirthDay(age: Int) = {
    if (debugEnabled) logger
      .debug("Checking when is the birthday of an " + age + " years old. It's " + contact.birthday(age))
    val birthDate = contact.birthday(age)
    val currentDay = DateTimeFormat.forPattern("dd-MM-yyyy").print(DateTime.now - age.years)
    assert(birthDate == currentDay)
  }

  @DataProvider(name = "birthdayWithFormatsDP")
  def birthdayWithFormatsDP(): Array[Array[Any]] = {
    Array(Array(21, DateFormat.dd_mm_yyyy_SEMICOLON),
      Array(25, DateFormat.dd_mm_yyyy_SEMICOLON),
      Array(40, DateFormat.dd_mm_yyyy_SEMICOLON),
      Array(50, DateFormat.dd_MM_yyyy_SEMICOLON),
      Array(30, DateFormat.dd_MM_YYYY_SEMICOLON),
      Array(100, DateFormat.dd_MM_YYYY_BACKSLASH),
      Array(0, DateFormat.dd_MM_YY_BACKSLASH),
      Array(80, DateFormat.dd_MM_yyyy),
      Array(23, DateFormat.dd_MM_yyyy_DOT),
      Array(33, DateFormat.dd_M_yyyy_DOT),
      Array(59, DateFormat.dd_MM_yyyy_HH),
      Array(30, DateFormat.dd_MM_yyyy_HH_mm),
      Array(20, DateFormat.dd_MM_yyyy_HH_mm_ss),
      Array(18, DateFormat.dd_MM_yyyy_H_m_s),
      Array(5, DateFormat.dd_MM_yyyy_H_m_s_a)
    )
  }

  @Test(dataProvider = "birthdayWithFormatsDP")
  def testBirthDayWithFormat(age: Int, format: DateFormat) = {
    if (debugEnabled) logger
      .debug("Checking when is the birthday of an " + age + " years old and format " + format + " . It's " + contact
      .birthday(age, format))
    val birthDate = contact.birthday(age, format)
    val currentDay = DateTimeFormat.forPattern(format.getFormat).print(DateTime.now - age.years)
    assert(birthDate == currentDay)
  }

  @Test
  def testBsn() = {
    var bsn = contact.bsn.toInt
    if (debugEnabled) logger.debug("Testing random bsn number : " + bsn)
    assert(bsn < 999999999 && bsn >= 9999999)
    var sum: Int = -1 * bsn % 10
    for ( multiplier <- 2 to 100; if bsn > 0 ) {
      bsn = bsn / 10
      val value = bsn % 10
      sum = sum + (multiplier * value)
    }
    assert(sum != 0 && sum % 11 == 0)
  }

  @Test
  def testReligion() = {
    val religion = contact.religion
    if (debugEnabled) logger.debug("Checking religion value " + religion)
    assert(religionList.contains(religion))
  }

  @Test
  def testZodiac() = {
    val zodiac = contact.zodiac
    if (debugEnabled) logger.debug("Checking zodiac value " + zodiac)
    assert(zodiacList.contains(zodiac))
  }

  @DataProvider(name = "zodiacDP")
  def zodiacDP(): Array[Array[Any]] = {
    Array(
      Array("22-12-2001", "Capricorn"),
      Array("10-01-2001", "Capricorn"),
      Array("19-01-2001", "Capricorn"),
      Array("20-01-2001", "Aquarius"),
      Array("25-01-2001", "Aquarius"),
      Array("18-02-2001", "Aquarius"),
      Array("19-02-2001", "Pisces"),
      Array("25-02-2001", "Pisces"),
      Array("20-03-2001", "Pisces"),
      Array("21-03-2001", "Aries"),
      Array("25-03-2001", "Aries"),
      Array("19-04-2001", "Aries"),
      Array("20-04-2061", "Taurus"),
      Array("25-04-2001", "Taurus"),
      Array("20-05-2001", "Taurus"),
      Array("21-05-2001", "Gemini"),
      Array("25-05-2001", "Gemini"),
      Array("20-06-2001", "Gemini"),
      Array("21-06-2041", "Cancer"),
      Array("25-06-2001", "Cancer"),
      Array("22-07-2001", "Cancer"),
      Array("23-07-2001", "Leo"),
      Array("22-08-2021", "Leo"),
      Array("22-08-2001", "Leo"),
      Array("23-08-2001", "Virgo"),
      Array("25-08-2001", "Virgo"),
      Array("22-09-2011", "Virgo"),
      Array("23-09-2001", "Libra"),
      Array("25-09-2001", "Libra"),
      Array("22-10-2010", "Libra"),
      Array("23-10-2001", "Scorpio"),
      Array("25-10-2001", "Scorpio"),
      Array("21-11-2001", "Scorpio"),
      Array("22-11-2001", "Sagittarius"),
      Array("25-11-2001", "Sagittarius"),
      Array("21-12-2001", "Sagittarius")
    )
  }

  @Test(dataProvider = "zodiacDP")
  def testZodiacByDate(date: String, expectedZodiac: String) = {
    val zodiac = contact.zodiac(date)
    if (debugEnabled) logger.debug("Testing zodiac for specific date: " + date + " . Zodiac is " + zodiac)
    assertResult(expectedZodiac)(zodiac)
  }

  @Test
  def testHeight() = {
    if (debugEnabled) logger.debug("Testing random height in cm " + contact.height(cm = true))
    if (debugEnabled) logger.debug("Testing random height in m " + contact.height(cm = false))
    val heightInCm = contact.height(cm = true).split(" ")(0).toDouble
    val heightInM = contact.height(cm = false).split(" ")(0).toInt
    assert(heightInCm >= 1.50 && heightInCm <= 2.20)
    assert(heightInM >= 150 && heightInM <= 220)
  }

  @Test
  def testWeight() = {
    val weight = contact.weight(metric = true)
    if (debugEnabled) logger.debug("Testing random weight value : " + weight)
    assert(weight.split(" ")(1).equals("kg"))
    assert(weight.split(" ")(0).toInt >= 50 && weight.split(" ")(0).toInt <= 110)
    val weightPounds = contact.weight(metric = false)
    assert(weightPounds.split(" ")(1).equals("lbs"))
    if (debugEnabled) logger.debug("Testing random weight value : " + weightPounds)
    assert(weightPounds.split(" ")(0).toInt >= 30 && weightPounds.split(" ")(0).toInt <= 90)
  }

  @Test
  def testBloodType() = {
    val bloodType = contact.bloodType
    if (debugEnabled) logger.debug("Testing random blood type: " + bloodType)
    assert(bloodTypeList.contains(bloodType))
  }

  @Test
  def testOccupation() = {
    val occupation = contact.occupation
    if (debugEnabled) logger.debug("Testing random occupation value: " + occupation)
    assert(occupationList.contains(occupation))
  }

}
