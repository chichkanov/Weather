package dvinc.yamblzhomeproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

import static android.content.Context.MODE_PRIVATE;

@Singleton
public class Settings {

    private final static String DEFAULT_CITY_LAT = "55.7522200";
    private final static String DEFAULT_CITY_LONG = "37.6155600";

    private final static String CURRENT_CITY_KEY = "current_city";
    private final static String CURRENT_CITY_LAT_KEY = "current_city_lat";
    private final static String CURRENT_CITY_LONG_KEY = "current_city_long";

    private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    private static final String JSON = "JSON";
    private static final String LAST_UPDATE_TIME = "LAST UPDATE TIME";

    private static SharedPreferences prefsDefault;
    private static SharedPreferences str;
    private Context context;

    public Settings(Context context) {
        App.getComponent().inject(this);
        prefsDefault = PreferenceManager.getDefaultSharedPreferences(context);
        str = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        this.context = context;
    }

    public void saveWeather(String json) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
        editor.putString(JSON, json);
        long currentTimeMillis = System.currentTimeMillis();
        editor.putLong(LAST_UPDATE_TIME, currentTimeMillis);
        editor.apply();
    }

    public WeatherResponse getWeather() {
        String string = str.getString(JSON, "");
        Gson jsonObject = new Gson();
        return jsonObject.fromJson(string, WeatherResponse.class);
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
