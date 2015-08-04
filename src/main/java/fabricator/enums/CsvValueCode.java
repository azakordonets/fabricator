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
    HEIGHT("Ssn"),
    WEIGHT("Height"),
    OCCUPATION("Weight"),
    VISA("Occupation"),
    MASTER("Visa"),
    IBAN("Master"),
    BIC("Iban"),
    SSN("Bic"),
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
    USER_AGENT("User Agent"),
    WORD("Word"),
    SENTENCE("Sentence");

    private String title;

    CsvValueCode(String title) {
        this.title = title;
    }

    public String getTitle()  {
        return this.title;
    }
}
