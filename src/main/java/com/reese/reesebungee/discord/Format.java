package com.reese.reesebungee.discord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Format {
    public static String bold(String text) {
        return "**" + text + "**";
    }

    public static String italic(String text) {
        return "_" + text + "_";
    }

    public static String underline(String text) {
        return "__" + text + "__";
    }

    public static String strikethrough(String text) {
        return "~~" + text + "~~";
    }

    public static String code(String text) {
        return "`" + text + "`";
    }

    public static String multiCode(String text) {
        return "```" + text + "```";
    }

    public static String timestamp() {
        TimeZone tz = TimeZone.getTimeZone("PDT");
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance(tz).getTime());
        return "[" + timeStamp + "]";
    }
}
