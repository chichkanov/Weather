package dvinc.yamblzhomeproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvinc.yamblzhomeproject.net.background.AutoUpdateJob;
import timber.log.Timber;

@Singleton
public class UpdateSettings implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String AUTO_UPDATE_KEY = "refresh_update";

    private SharedPreferences prefs;
    private JobManager jobManager;

    @Inject
    UpdateSettings(JobManager jobManager, Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.jobManager = jobManager;
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void scheduleAutoUpdate() {
        int minutes = Integer.parseInt(prefs.getString(AUTO_UPDATE_KEY, "0"));
        Timber.d("Auto update time is %s", minutes);
        if (minutes != 0 && jobManager.getAllJobRequestsForTag(AutoUpdateJob.TAG).isEmpty()) {
            jobManager.schedule(new JobRequest.Builder(AutoUpdateJob.TAG)
                    .setUpdateCurrent(true)
                    .setPeriodic(TimeUnit.MINUTES.toMillis(minutes), TimeUnit.MINUTES.toMillis(5))
                    .setPersisted(true)
                    .build());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Timber.d("Prefs Changed");
        jobManager.cancelAllForTag(AutoUpdateJob.TAG);
        scheduleAutoUpdate();
    }
}
