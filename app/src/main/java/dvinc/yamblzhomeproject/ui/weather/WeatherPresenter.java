package dvinc.yamblzhomeproject.ui.weather;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dvinc.yamblzhomeproject.data.repository.WeatherRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private WeatherRepository repository;
    private Disposable dataSubscription;

    public WeatherPresenter(WeatherRepository repository) {
        this.repository = repository;
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
}
