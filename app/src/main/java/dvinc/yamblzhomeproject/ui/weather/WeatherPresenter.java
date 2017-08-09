package dvinc.yamblzhomeproject.ui.weather;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.R;
import dvinc.yamblzhomeproject.data.repository.WeatherRepository;
import dvinc.yamblzhomeproject.utils.converter.ConverterValues;
import dvinc.yamblzhomeproject.utils.converter.WeatherConverter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private WeatherRepository repository;
    private Disposable dataSubscription;
    private WeatherConverter weatherConverter;

    @Inject
    public WeatherPresenter(WeatherRepository repository, WeatherConverter weatherConverter) {
        this.repository = repository;
        this.weatherConverter = weatherConverter;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        getViewState().showCityName();
    }

    void getWeather() {
        Log.i("WeatherPresenter", "StartLoading");
        getViewState().showLoading();
        dataSubscription = repository.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(next -> {
                            Log.i("LoadWeather", "Success");
                            getViewState().updateWeatherCurrent(next.getCurrentWeather());
                            getViewState().updateWeatherHourly(next.getHourWeather());
                            getViewState().updateWeatherDaily(next.getDayWeather());
                            getViewState().updateLastUpdateTime(next.getUpdatedTime());
                        },
                        error -> {
                            getViewState().hideLoading();
                            Log.e("Error", error.getMessage());
                            getViewState().showError();
                        }, () -> getViewState().hideLoading());
    }

    @Override
    public void detachView(WeatherView view) {
        super.detachView(view);
        Log.i("WeatherPresenter", "DetachView");
        if (dataSubscription != null) {
            dataSubscription.dispose();
        }
    }

    int getWindSpeedUnitText() {
        switch (weatherConverter.getWindValue()) {
            case ConverterValues.WIND_SPEED_KMH: {
                return R.string.weather_wind_speed_km;
            }
            case ConverterValues.WIND_SPEED_MILESH: {
                return R.string.weather_wind_speed_miles;
            }
        }
        return R.string.weather_wind_speed_metr;
    }

    int getPressureUnitText() {
        if (weatherConverter.getPressureValue().equals(ConverterValues.PRESSURE_MM)) {
            return R.string.weather_pressure_mm;
        }
        return R.string.weather_pressure_hpa;
    }

}
