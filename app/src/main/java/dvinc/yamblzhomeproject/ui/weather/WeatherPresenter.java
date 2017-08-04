package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.CityWeatherEntity;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    @Inject
    WeatherRepositoryImpl repository;

    @Inject
    Settings settings;

    @Inject
    AppDatabase database;

    private Disposable dataSubscription;

    WeatherPresenter() {
        App.getComponent().inject(this);
    }

    void getWeather() {
        Log.i("WeatherPresenter", "StartLoading");
        getViewState().showLoading();
        dataSubscription = repository.getData()
                .subscribeOn(Schedulers.io())
                .doOnNext(next -> database.cityWeatherDao().insertAll(new CityWeatherEntity(null, next)))
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(next -> {
                            Log.i("LoadWeather", "Success");
                            getViewState().hideLoading();
                            getViewState().updateWeatherCurrent(next.getWeatherResponse());
                            getViewState().updateWeatherHourly(next.getWeatherForecastHourlyResponse());
                            getViewState().updateWeatherDaily(next.getWeatherForecastDailyResponse());
                            settings.saveWeather(new Gson().toJson(next));
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
