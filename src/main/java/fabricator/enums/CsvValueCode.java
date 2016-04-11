package fabricator.enums;

public enum CsvValueCode {
    INTEGER("Integer"),
    DOUBLE("Double"),
    HASH("Hash"),
    GUID("Guid"),
    TIME("Time"),
    DATE("Date"),
    NAME("Name"),
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    BIRTHDAY("Birthday"),
    EMAIL("Email"),
    PHONE("Phone"),
    ADDRESS("Address"),
    POSTCODE("Postcode"),
    BSN("Bsn"),
    HEIGHT("Height"),
    WEIGHT("Weight"),
    OCCUPATION("Occupation"),
    VISA("Visa"),
    MASTER("Master"),
    IBAN("Iban"),
    BIC("Bic"),
    SSN("Ssn"),
    URL("Url"),
    IP("Ip"),
    MACADDRESS("Mac Address"),
    UUID("Uuid"),
    COLOR("Color"),
    TWITTER("Twitter"),
    HASHTAG("Hashtag"),
    FACEBOOK("Facebook"),
    GOOGLE_ANALYTICS("Google Analytics"),
    ALTITUDE("Altitude"),
    DEPTH("Depth"),
    LATITUDE("Latitude"),
    LONGITUDE("Longitude"),
    COORDINATES("Coordinates"),
    GEOHASH("Geohash"),
    APPLE_TOKEN("Apple Token"),
    ANDROID("Android Token"),
    WINDOWS7TOKEN("Windows 7 token"),
    WINDOWS8TOKEN("Windows 8 token"),
    USER_AGENT_CHROME("User Agent"),
    USER_AGENT_FIREFOX("User Agent"),
    USER_AGENT_IE("User Agent"),
    USER_AGENT_OPERA("User Agent"),
    WORD("Word"),
    SENTENCE("Sentence");

    private final String title;

    CsvValueCode(String title) {
        this.title = title;
    }

    public String getTitle()  {
        return this.title;
    }
}
