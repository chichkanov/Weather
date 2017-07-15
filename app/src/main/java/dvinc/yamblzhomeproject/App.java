package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {

    private static SharedPreferences sharedPreferences;
    public Context contex;
    public static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contex = this;
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }
}
