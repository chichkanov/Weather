package dvinc.yamblzhomeproject.net.background;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.data.repository.WeatherRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AutoUpdateJob extends Job {

    public static final String TAG = "bg_job_tag";

    private WeatherRepository weatherRepository;

    @Inject
    AutoUpdateJob(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        weatherRepository.updateAllWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        error -> Timber.e("Error updating"));
        return Result.SUCCESS;
    }
}
