package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.di.AppComponent;
import dvinc.yamblzhomeproject.di.DaggerAppComponent;
import dvinc.yamblzhomeproject.di.modules.ApplicationModule;
import dvinc.yamblzhomeproject.utils.UpdateSettings;
import timber.log.Timber;

public class App extends Application {

    private static AppComponent appComponent;

    @Inject
    UpdateSettings weatherSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initTimber();
        initStetho();
        initLeakCanary();
        initAutoUpdate();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initAutoUpdate() {
        weatherSettings.scheduleAutoUpdate();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
        appComponent.inject(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
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
