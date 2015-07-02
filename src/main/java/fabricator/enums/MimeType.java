package fabricator.enums;

public enum MimeType {
    APPLICATION,
    AUDIO,
    IMAGE,
    MESSAGE,
    MODEL,
    MULTIPART,
    TEXT,
    VIDEO;

    public static MimeType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }

}
