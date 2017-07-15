package dvinc.yamblzhomeproject;
/*
 * Created by DV on Space 5 
 * 14.07.2017
 */

import android.app.Application;

import com.evernote.android.job.JobManager;

import dvinc.yamblzhomeproject.net.background.BGJobCreator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new BGJobCreator());
    }
}
