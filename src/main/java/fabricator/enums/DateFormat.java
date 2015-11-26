package fabricator.enums;

public enum DateFormat {
    dd("dd"),
    dd_MM("dd-MM"),
    dd_MM_yy("dd-MM-yy"),
    dd_MM_yy_HH_mm("dd-MM-yy HH:mm"),
    dd_MM_yyyy("dd-MM-yyyy"), // default date format
    dd_MM_yyyy_SEMICOLON("dd:MM:yyyy"), // default date format
    dd_MM_yyyy_HH_mm("dd-MM-yyyy HH:mm"),
    dd_MM_yyyy_HH_mm_ss("dd-MM-yyyy HH:mm:ss"),
    dd_MM_yyyy_H_m_s("dd-MM-yyyy H:m:s"),
    dd_MM_yyyy_H_m_s_a("dd-MM-yyyy H:m:s a"),
    dd_mm_yyyy_SEMICOLON("dd:mm:yyyy"),
    mm_dd_yyyy_SEMICOLON("mm:dd:yyyy"),
    dd_MM_YYYY_SEMICOLON("dd:MM:YYYY"),
    dd_MM_YYYY_BACKSLASH("dd/MM/YYYY"),
    dd_MM_yyyy_BACKSLASH("dd/MM/yyyy"),
    dd_MM_YY_BACKSLASH("dd/MM/YY"),
    dd_MM_yyyy_DOT("dd.MM.yyyy"),
    dd_M_yyyy_DOT("dd.M.yyyy"),
    dd_MM_yyyy_HH("dd-MM-yyyy HH"),
    dd_MM_yyyy_HH_SEMICOLON("dd:MM:yyyy HH"),
    d_MMM_SPACE("d MMM"),
    dd_MMMM_yyyy_SPACE("dd MMMM yyyy"),
    HH_mm("HH:mm"),
    MM_yy("MM-yy"),
    MM_yyyy("MM-yyyy"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyMMdd("yyMMdd"),
    yyyyMMdd("yyyyMMdd"),
    yyyyMMddHHmm("yyyyMMddHHmm");

    private final String format;

    DateFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
