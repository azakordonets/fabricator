package fabricator

object UserAgent {
  def apply(): UserAgent = {
    new UserAgent(UtilityService(), Alphanumeric(), Calendar())
  }
}

class UserAgent(private val utility: UtilityService,
                 private val alpha: Alphanumeric,
                 private val calendar: Calendar) {

  def mac_processor: String = utility.getValueFromArray("mac_processor")

  def linux_processor: String = utility.getValueFromArray("linux_processor")

  def browser_name: String = utility.getValueFromArray("browser")

  def windows_platform_token: String = utility.getValueFromArray("windows")

  def linux_platform_token: String = "X11; Linux %s".format(utility.getValueFromArray("linux_processor"))

  def mac_platform_token: String = "Macintosh; %s Mac OS X 10_%d_%d".format(utility.getValueFromArray("mac_processor"),
                                                                            alpha.getInteger(5, 8),
                                                                            alpha.getInteger(0, 9))
  def chrome: String = {
    val saf = "%d%d".format(alpha.getInteger(531, 536), alpha.getInteger(0,2))
    val template = "(%s) AppleWebKit/%s (KHTML, like Gecko) Chrome/%d.0.%d.0 Safari/%s"
    val platforms = Array(
    template.format(linux_platform_token, saf, alpha.getInteger(13, 15), alpha.getInteger(800, 899), saf),
    template.format(windows_platform_token, saf, alpha.getInteger(13, 15), alpha.getInteger(800, 899), saf),
    template.format(mac_platform_token, saf, alpha.getInteger(13, 15), alpha.getInteger(800, 899), saf)
    )
    "Mozilla/5.0 " + utility.getRandomArrayElement(platforms)
  }

  def firefox: String = {
    val version = Array("Gecko/%s Firefox/%d.0".format(calendar.date.inYear(2011).asString(), alpha.getInteger(4, 15)),
                        "Gecko/%s Firefox/3.6.%d".format(calendar.date.inYear(2011).asString(), alpha.getInteger(1, 20)),
                        "Gecko/%s Firefox/3.8".format(calendar.date.inYear(2011).asString())
                        )
    val windows_template: String = "(%s; %s; rv:1.9.%d.20) %s"
    val linux_template: String = "(%s; rv:1.9.%d.20) %s"
    val mac_template: String = "(%s; rv:1.9.%d.20) %s"

    val platforms = Array(
      windows_template.format(windows_platform_token, utility.getValueFromArray("langs"), alpha.getInteger(0, 2),
                       utility.getRandomArrayElement(version)),
      linux_template.format(linux_platform_token, alpha.getInteger(5, 7), utility.getRandomArrayElement(version)),
      mac_template.format(mac_platform_token, alpha.getInteger(2, 6), utility.getRandomArrayElement(version))
      )
    "Mozilla/5.0 " + utility.getRandomArrayElement(platforms)
  }

  def opera: String = {
    val template = "(%s; %s) Presto/2.9.%d Version/%d.00"
    val langs = utility.getArrayFromJson("langs")
    val platforms = Array(
      template.format(linux_platform_token,
        utility.getRandomArrayElement(langs),
        alpha.getInteger(160, 190),
        alpha.getInteger(10, 12)),
      template.format(windows_platform_token,
        utility.getRandomArrayElement(langs),
        alpha.getInteger(160, 190),
        alpha.getInteger(10, 12))
      )
    "Opera/%d.%d.%s".format(alpha.getInteger(8, 9),
      alpha.getInteger(10, 99),
      utility.getRandomArrayElement(platforms))
  }

  def internet_explorer: String = {
    val template = "Mozilla/5.0 (compatible; MSIE %d.0; %s; Trident/%d.%d)"
    template.format(alpha.getInteger(5, 9),
      windows_platform_token,
      alpha.getInteger(3, 5),
      alpha.getInteger(0, 1))
  }

  def browser: String = {
    val browser_user_agents = Array(chrome, firefox, opera, internet_explorer)
    browser_user_agents(alpha.getInteger(0, browser_user_agents.length - 1))
  }





}
