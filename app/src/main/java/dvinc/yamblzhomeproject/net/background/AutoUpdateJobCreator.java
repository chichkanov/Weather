package dvinc.yamblzhomeproject.net.background;
/*
 * Created by DV on Space 5 
 * 15.07.2017
 */

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvinc.yamblzhomeproject.data.repository.WeatherRepository;

@Singleton
public class AutoUpdateJobCreator implements JobCreator {

    private WeatherRepository weatherRepository;

    @Inject
    public AutoUpdateJobCreator(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case AutoUpdateJob.TAG:
                return new AutoUpdateJob(weatherRepository);
            default:
                return null;
        }
    }
}
