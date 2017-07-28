package dvinc.yamblzhomeproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;

@Singleton
public class Settings {

    private final static String DEFAULT_CITY_LAT = "55.7522200";
    private final static String DEFAULT_CITY_LONG = "37.6155600";

    private final static String CURRENT_CITY_KEY = "current_city";
    private final static String CURRENT_CITY_LAT_KEY = "current_city_lat";
    private final static String CURRENT_CITY_LONG_KEY = "current_city_long";

    private static SharedPreferences prefsDefault;
    private Context context;

    public Settings(Context context) {
        App.getComponent().inject(this);
        prefsDefault = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setCurrentCity(String city) {
        prefsDefault.edit().putString(CURRENT_CITY_KEY, city).apply();
    }

    public String getCurrentCity() {
        return prefsDefault.getString(CURRENT_CITY_KEY, context.getResources().getString(R.string.default_city));
    }

    public void setCurrentCityLocationLong(double lng) {
        prefsDefault.edit().putString(CURRENT_CITY_LONG_KEY, String.valueOf(lng)).apply();
    }

    public void setCurrentCityLocationLat(double lat) {
        prefsDefault.edit().putString(CURRENT_CITY_LAT_KEY, String.valueOf(lat)).apply();
    }

    public String getCurrentCityLocationLong() {
        return prefsDefault.getString(CURRENT_CITY_LONG_KEY, DEFAULT_CITY_LONG);
    }

    public String getCurrentCityLocationLat() {
        return prefsDefault.getString(CURRENT_CITY_LAT_KEY, DEFAULT_CITY_LAT);
    }
}
