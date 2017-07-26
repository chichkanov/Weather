package dvinc.yamblzhomeproject.utils;

import java.util.Locale;

public class WeatherUtils {
    public static String getLocale() {
        return Locale.getDefault().getLanguage().equals("ru") ? "ru" : "en";
    }
}
