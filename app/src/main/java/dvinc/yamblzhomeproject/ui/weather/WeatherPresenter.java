package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import dvinc.yamblzhomeproject.repository.WeatherRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.getDefault());

    private WeatherRepository repository;
    private Disposable dataSubscription;

    public WeatherPresenter(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        getWeather();
    }

    void getWeather() {
        Log.i("WeatherPresenter", "StartLoading");
        getViewState().showLoading();
        dataSubscription = repository.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> {
                            Log.i("LoadWeather", "Success");
                            getViewState().hideLoading();
                            getViewState().updateWeatherCurrent(next.getWeatherResponse());
                            getViewState().updateWeatherHourly(next.getWeatherForecastHourlyResponse());
                            getViewState().updateWeatherDaily(next.getWeatherForecastDailyResponse());
                            getViewState().updateLastUpdateTime(dateFormat.format(new Date(next.getUpdatedTime())));
                        },
                        error -> {
                            getViewState().hideLoading();
                            Log.e("Error", error.getMessage());
                            getViewState().showError();
                        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataSubscription != null) {
            dataSubscription.dispose();
        }
    }
}
