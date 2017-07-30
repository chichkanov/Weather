package dvinc.yamblzhomeproject.net.background;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BGSyncJob extends Job {

    public static final String TAG = "bg_job_tag";

    @Inject
    Settings settings;

    BGSyncJob() {
        App.getComponent().inject(this);
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        App.get(getContext()).getRepositoryImpl().updateWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> settings.saveWeather(new Gson().toJson(next)));
        return Result.SUCCESS;
    }

    public static void schedulePeriodic(int minutes) {
        new JobRequest.Builder(BGSyncJob.TAG)
                .setUpdateCurrent(true)
                .setPeriodic(TimeUnit.MINUTES.toMillis(minutes), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .build()
                .schedule();
    }
}
