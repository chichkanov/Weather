package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.evernote.android.job.JobManager;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import dvinc.yamblzhomeproject.di.AppComponent;
import dvinc.yamblzhomeproject.di.DaggerAppComponent;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.net.background.BGJobCreator;
import dvinc.yamblzhomeproject.net.background.BGSyncJob;

public class App extends Application {

    private static AppComponent appComponent;

    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

        JobManager.create(this).addJobCreator(new BGJobCreator());
        SharedPreferences str = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);

        if (str.getInt("UPDATE TIME", 0) == 0) {
            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
            editor.putInt("UPDATE TIME", 15);
            editor.putBoolean("AUTOUPDATE", true);
            editor.apply();

            BGSyncJob.schedulePeriodic(15);
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    @VisibleForTesting
    public static void setAppComponent(AppComponent component) {
        appComponent = component;
    }
}
