package fabricator.enums;

public enum FileType {
    AUDIO,
    IMAGE,
    TEXT,
    VIDEO,
    DOCUMENT;

    public static FileType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
