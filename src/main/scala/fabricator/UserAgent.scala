package fabricator

object UserAgent {
  def apply(): UserAgent = {
    new UserAgent(UtilityService(), Alphanumeric(), Calendar())
  }
}

class UserAgent(private val utility: UtilityService,
                 private val alpha: Alphanumeric,
                 private val calendar: Calendar) {

  def macProcessor: String = utility.getValueFromArray("mac_processor")

  def linuxProcessor: String = utility.getValueFromArray("linux_processor")

  def browserName: String = utility.getValueFromArray("browser")

  def windowsPlatformToken: String = utility.getValueFromArray("windows")

  def linuxPlatformToken: String = "X11; Linux %s".format(utility.getValueFromArray("linux_processor"))

  def macPlatformToken: String = "Macintosh; %s Mac OS X 10_%d_%d".format(utility.getValueFromArray("mac_processor"),
                                                                            alpha.randomInt(5, 8),
                                                                            alpha.randomInt(0, 9))
  def chrome: String = {
    val saf = s"${alpha.randomInt(531, 536)}${alpha.randomInt(0, 2)}"
    val template = "(%s) AppleWebKit/%s (KHTML, like Gecko) Chrome/%d.0.%d.0 Safari/%s"
    val platforms = Array(
      template.format(linuxPlatformToken, saf, alpha.randomInt(13, 15), alpha.randomInt(800, 899), saf),
      template.format(windowsPlatformToken, saf, alpha.randomInt(13, 15), alpha.randomInt(800, 899), saf),
      template.format(macPlatformToken, saf, alpha.randomInt(13, 15), alpha.randomInt(800, 899), saf)
    )
    "Mozilla/5.0 " + utility.getRandomArrayElement(platforms)
  }

  def firefox: String = {
    val version = Array("Gecko/%s Firefox/%d.0".format(calendar.randomDate.inYear(2011).asString, alpha.randomInt(4, 15)),
                        "Gecko/%s Firefox/3.6.%d".format(calendar.randomDate.inYear(2011).asString, alpha.randomInt(1, 20)),
                        "Gecko/%s Firefox/3.8".format(calendar.randomDate.inYear(2011).asString)
                        )
    val windowsTemplate: String = "(%s; %s; rv:1.9.%d.20) %s"
    val linuxTemplate: String = "(%s; rv:1.9.%d.20) %s"
    val macTemplate: String = "(%s; rv:1.9.%d.20) %s"

    val platforms = Array(
      windowsTemplate.format(windowsPlatformToken, utility.getValueFromArray("langs"), alpha.randomInt(0, 2),
                       utility.getRandomArrayElement(version)),
      linuxTemplate.format(linuxPlatformToken, alpha.randomInt(5, 7), utility.getRandomArrayElement(version)),
      macTemplate.format(macPlatformToken, alpha.randomInt(2, 6), utility.getRandomArrayElement(version))
      )
    "Mozilla/5.0 " + utility.getRandomArrayElement(platforms)
  }

  def opera: String = {
    val template = "(%s; %s) Presto/2.9.%d Version/%d.00"
    val langs = utility.getArrayFromJson("langs")
    val platforms = Array(
      template.format(linuxPlatformToken,
        utility.getRandomArrayElement(langs),
        alpha.randomInt(160, 190),
        alpha.randomInt(10, 12)),
      template.format(windowsPlatformToken,
        utility.getRandomArrayElement(langs),
        alpha.randomInt(160, 190),
        alpha.randomInt(10, 12))
      )
    "Opera/%d.%d.%s".format(alpha.randomInt(8, 9),
      alpha.randomInt(10, 99),
      utility.getRandomArrayElement(platforms))
  }

  def internet_explorer: String = {
    val template = "Mozilla/5.0 (compatible; MSIE %d.0; %s; Trident/%d.%d)"
    template.format(alpha.randomInt(5, 9),
      windowsPlatformToken,
      alpha.randomInt(3, 5),
      alpha.randomInt(0, 1))
  }

  def browser: String = {
    val browser_user_agents = Array(chrome, firefox, opera, internet_explorer)
    browser_user_agents(alpha.randomInt(0, browser_user_agents.length - 1))
  }





}
