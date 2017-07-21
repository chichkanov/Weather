package dvinc.yamblzhomeproject.net.background;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import dvinc.yamblzhomeproject.App;

public class BGSyncJob extends Job {

    public static final String TAG = "bg_job_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        App.get(getContext()).getRepositoryImpl().updateWeatherData(getContext());
        //Log.v("BACKGROUND", "onRunJob is start");
        return Result.SUCCESS;
    }

    public static void schedulePeriodic(int minutes) {
        //Log.v("BACKGROUND", "schedulePeriodic is start");
        new JobRequest.Builder(BGSyncJob.TAG)
                .setUpdateCurrent(true)
                .setPeriodic(TimeUnit.MINUTES.toMillis(minutes), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .build()
                .schedule();
    }
}
