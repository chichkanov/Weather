package dvinc.yamblzhomeproject.utils;

import java.util.Locale;

import dvinc.yamblzhomeproject.R;

public class WeatherUtils {
    public static String getLocale() {
        return Locale.getDefault().getLanguage();
    }

    public static int getMainIcon(String openWeatherIcon) {
        openWeatherIcon = openWeatherIcon.substring(0, 2);
        switch (openWeatherIcon) {
            case "01": {
                return R.string.wi_day_sunny;
            }
            case "02": {
                return R.string.wi_day_sunny_overcast;
            }
            case "03": {
                return R.string.wi_cloud;
            }
            case "04": {
                return R.string.wi_cloudy;
            }
            case "09": {
                return R.string.wi_rain;
            }
            case "10": {
                return R.string.wi_day_rain;
            }
            case "11": {
                return R.string.wi_thunderstorm;
            }
            case "13": {
                return R.string.wi_snow;
            }
            case "50": {
                return R.string.wi_fog;
            }
        }
        return 0;
    }

    public static int getForecastIcon(String openWeatherIcon) {
        openWeatherIcon = openWeatherIcon.substring(0, 2);
        switch (openWeatherIcon) {
            case "01": {
                return R.drawable.wi_day_sunny;
            }
            case "02": {
                return R.drawable.wi_day_sunny_overcast;
            }
            case "03": {
                return R.drawable.wi_cloud;
            }
            case "04": {
                return R.drawable.wi_cloudy;
            }
            case "09": {
                return R.drawable.wi_rain;
            }
            case "10": {
                return R.drawable.wi_day_rain;
            }
            case "11": {
                return R.drawable.wi_thunderstorm;
            }
            case "13": {
                return R.drawable.wi_snow;
            }
            case "50": {
                return R.drawable.wi_fog;
            }
        }
        return 0;
    }
}
