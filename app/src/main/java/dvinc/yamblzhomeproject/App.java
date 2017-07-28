package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;

import dvinc.yamblzhomeproject.di.AppComponent;
import dvinc.yamblzhomeproject.di.DaggerAppComponent;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.net.background.BGJobCreator;
import dvinc.yamblzhomeproject.net.background.BGSyncJob;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static AppComponent appComponent;

    private WeatherApi api;
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private WeatherRepositoryImpl repositoryImpl;

    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    public WeatherApi getApi() {
        return api;
    }

    public WeatherRepositoryImpl getRepositoryImpl() {
        return repositoryImpl;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WeatherApi.class);

        JobManager.create(this).addJobCreator(new BGJobCreator());
        SharedPreferences str = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);

        // Initial setup of the application
        if (str.getInt("UPDATE TIME", 0) == 0) {
            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
            editor.putInt("UPDATE TIME", 15);
            editor.putBoolean("AUTOUPDATE", true);
            editor.apply();
            // Run background task
            BGSyncJob.schedulePeriodic(15);
        }

        repositoryImpl = new WeatherRepositoryImpl();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}
