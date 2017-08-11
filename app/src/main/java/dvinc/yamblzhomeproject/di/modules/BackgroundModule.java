package dvinc.yamblzhomeproject.di.modules;

import android.content.Context;

import com.evernote.android.job.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvinc.yamblzhomeproject.net.background.AutoUpdateJobCreator;

@Module
public class BackgroundModule {

    @Provides
    @Singleton
    JobManager provideJobManager(Context context, AutoUpdateJobCreator creator) {
        JobManager jobManager = JobManager.create(context);
        jobManager.addJobCreator(creator);
        return jobManager;
    }
}
