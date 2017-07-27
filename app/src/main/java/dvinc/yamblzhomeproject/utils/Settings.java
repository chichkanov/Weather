package dvinc.yamblzhomeproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.R;

@Singleton
public class Settings {

    private final static String CURRENT_CITY_KEY = "current_city";

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

}
