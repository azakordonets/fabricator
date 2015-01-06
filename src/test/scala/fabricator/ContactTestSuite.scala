package fabricator

import com.github.nscala_time.time.Imports._
import org.testng.annotations.{DataProvider, Test}

class ContactTestSuite extends BaseTestSuite {

  val firstNameList: Array[String] = util.getArrayFromJson("first_name")
  val lastNameList: Array[String] = util.getArrayFromJson("last_name")
  val phoneFormatsList: Array[String] = util.getArrayFromJson("phone_formats")
  val streetNameList: Array[String] = util.getArrayFromJson("street_suffix")
  val houseNumberList: Array[String] = util.getArrayFromJson("house_number")
  val appNumberList: Array[String] = util.getArrayFromJson("app_number")
  val postcodeList: Array[String] = util.getArrayFromJson("postcode")
  val stateList: Array[String] = util.getArrayFromJson("state")
  val stateAbbrList: Array[String] = util.getArrayFromJson("state_abbr")
  val companyList: Array[String] = util.getArrayFromJson("company_suffix")
  val religionList: Array[String] = util.getArrayFromJson("religion")
  val zodiacList: Array[String] = util.getArrayFromJson("zodiac")
  val bloodTypeList: Array[String] = util.getArrayFromJson("blood_type")
  val occupationList: Array[String] = util.getArrayFromJson("occupation")

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
    val name = contact.fullName(false)
    if (debugEnabled) logger.debug("Checking full name value " + name)
    firstNameList.contains(name.split(" ")(0))
    lastNameList.contains(name.split(" ")(1))
    val nameWithPrefix = contact.fullName(false)
    if (debugEnabled) logger.debug("Checking full name value " + nameWithPrefix)
    firstNameList.contains(nameWithPrefix.split(" ")(1))
    lastNameList.contains(nameWithPrefix.split(" ")(2))
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
    Array(Array(21, "dd:mm:yyyy"),
      Array(25, "mm:dd:yyyy"),
      Array(40, "dd:mm:yyyy"),
      Array(50, "dd:MM:yyyy"),
      Array(30, "dd:MM:YYYY"),
      Array(100, "dd/MM/YYYY"),
      Array(0, "dd/MM/YY"),
      Array(80, "dd-MM-yyyy"),
      Array(23, "dd.MM.yyyy"),
      Array(33, "dd.M.yyyy"),
      Array(59, "dd-MM-yyyy HH"),
      Array(30, "dd-MM-yyyy HH:mm"),
      Array(20, "dd-MM-yyyy HH:mm:ss"),
      Array(18, "dd-MM-yyyy H:m:s"),
      Array(5, "dd-MM-yyyy H:m:s a")
    )
  }

  @Test(dataProvider = "birthdayWithFormatsDP")
  def testBirthDayWithFormat(age: Int, format: String) = {
    if (debugEnabled) logger
      .debug("Checking when is the birthday of an " + age + " years old and format " + format + " . It's " + contact
      .birthday(age, format))
    val birthDate = contact.birthday(age, format)
    val currentDay = DateTimeFormat.forPattern(format).print(DateTime.now - age.years)
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
    if (debugEnabled) logger.debug("Testing random height in cm " + contact.height(true))
    if (debugEnabled) logger.debug("Testing random height in m " + contact.height(false))
    val heightInCm = contact.height(true).split(" ")(0).toDouble
    val heightInM = contact.height(false).split(" ")(0).toDouble
    assert(heightInCm >= 1.50 && heightInCm <= 2.20)
    assert(heightInM >= 150 && heightInM <= 220)
  }

  @Test
  def testWeight() = {
    val weight = contact.weight()
    if (debugEnabled) logger.debug("Testing random weight value : " + weight)
    assert(weight.split(" ")(0).toInt >= 50 && weight.split(" ")(0).toInt <= 110)
  }

  @Test
  def testBloodType() = {
    val bloodType = contact.bloodType()
    if (debugEnabled) logger.debug("Testing random blood type: " + bloodType)
    assert(bloodTypeList.contains(bloodType))
  }

  @Test
  def testOccupation() = {
    val occupation = contact.occupation()
    if (debugEnabled) logger.debug("Testing random occupation value: " + occupation)
    assert(occupationList.contains(occupation))
  }

}
