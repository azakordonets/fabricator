package fabricator

import com.github.nscala_time.time.Imports._
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.{DataProvider, Test}

class ContactTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val contact = fabr.contact()
  val utility = new UtilityService()
  val firstNameList:Array[String] = utility.getArrayFromJson("first_name")
  val lastNameList:Array[String] = utility.getArrayFromJson("last_name")
  val phoneFormatsList: Array[String] = utility.getArrayFromJson("phone_formats")
  val streetNameList: Array[String] = utility.getArrayFromJson("street_suffix")
  val houseNumberList: Array[String] = utility.getArrayFromJson("house_number")
  val appNumberList: Array[String] = utility.getArrayFromJson("app_number")
  val postcodeList: Array[String] = utility.getArrayFromJson("postcode")
  val stateList: Array[String] = utility.getArrayFromJson("state")
  val stateAbbrList: Array[String] = utility.getArrayFromJson("state_abbr")
  val companyList: Array[String] = utility.getArrayFromJson("company_suffix")

  @Test
  def testFirstName() = {
    val name = contact.firstName()
    logger.info("Checking first name value " + name)
    assert(firstNameList.contains(name))
  }

  @Test
  def testLastName() = {
    val name = contact.lastName()
    logger.info("Checking last name value " + name)
    assert(lastNameList.contains(name))
  }

  @Test
  def testFullName() = {
    val name = contact.fullName()
    logger.info("Checking full name value " + name)
    firstNameList.contains(name.split(" ")(0))
    lastNameList.contains(name.split(" ")(1))
  }


  @Test
  def testEmail() = {
    logger.info("Checking email " + contact.eMail())
    val email = contact.eMail()
    assert(email.nonEmpty)
  }

  @Test
  def testPhoneNumber() = {
    val phone = contact.phoneNumber()
    logger.info("Checking phone number value " + phone)
    var reversedPhone = ""
    if (phone.substring(0,2).equals("1-")){
      reversedPhone = "1-" + phone.substring(2, phone.length).replaceAll("[0-9]", "#")
    }else {
      reversedPhone = phone.replaceAll("[0-9]", "#")
    }
    assert(phoneFormatsList.contains(reversedPhone))
  }

  @Test
  def testStreetName() = {
  val street = contact.streetName()
  logger.info("Checking street name value " + street)
  assert(streetNameList.contains(street))
  }

  @Test
  def testhouseNumber() = {
    val houseNumber = contact.houseNumber()
    val reversedHouseNumber = houseNumber.replaceAll("[0-9]","#")
    logger.info("Checking houseNumber value " + houseNumber)
    assert(houseNumberList.contains(reversedHouseNumber))
  }

  @Test
  def testApartmentNumber() = {
    val apartmentNumber = contact.apartmentNumber()
    val reversedApp = apartmentNumber.replaceAll("[0-9]","#")
    logger.info("Checking apartmentNumber value " + apartmentNumber)
    assert(appNumberList.contains(reversedApp))
  }

  @Test
  def testPostcode() = {
    val postCode = contact.postcode()
    val reversedPostCode = postCode.replaceAll("[0-9]","#")
    logger.info("Checking postCode value " + postCode)
    assert(postcodeList.contains(reversedPostCode))
  }

  @Test
  def testState() = {
    val state = contact.state()
    logger.info("Checking state value " + state)
    assert(stateList.contains(state))
  }

  @Test
  def testStateAbbr() = {
    val stateAbbr = contact.stateShortCode()
    logger.info("Checking stateAbbr value " + stateAbbr)
    assert(stateAbbrList.contains(stateAbbr))
  }

  @Test
  def testCompany() = {
    val company = contact.company()
    logger.info("Checking company value " + company)
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
    logger.info("Checking when is the birthday of an " + age + " years old. It's " + contact.birthday(age))
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
    logger.info("Checking when is the birthday of an " + age + " years old and format " + format + " . It's " + contact.birthday(age, format))
    val birthDate = contact.birthday(age, format)
    val currentDay = DateTimeFormat.forPattern(format).print(DateTime.now - age.years)
    assert(birthDate == currentDay)
  }

  @Test
  def testBsn() = {
    for (i <- 0 to 100) {
      var bsn = contact.bsn().toInt
      logger.info("Testing random bsn number : " + bsn)
      assert(bsn < 999999999 && bsn >= 9999999)
      var sum: Int = -1 * bsn % 10;
      for (multiplier <- 2 to 100; if bsn > 0) {
        bsn = bsn / 10
        var value = bsn % 10
        sum = sum + (multiplier * value)
      }
      assert(sum != 0 && sum % 11 == 0)
    }
  }


}
