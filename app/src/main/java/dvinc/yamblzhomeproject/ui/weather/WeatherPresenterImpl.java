package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.content.Context;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.repository.CallbackWeather;
import dvinc.yamblzhomeproject.repository.model.WeatherResponse;

class WeatherPresenterImpl<T extends WeatherView> implements WeatherPresenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getWeatherFromInternet(Context context) {
        App.get(context).getRepositoryImpl().getDataFromWeb(context, new CallbackWeather() {
            @Override
            public void onSuccess(WeatherResponse weatherResponse) {
                view.updateWeatherParameters(weatherResponse);
            }

            @Override
            public void onError() {
                view.showError("Network error");
            }
        });
    }

    @Override
    public void getWeatherDataFromCache(Context context) {
        WeatherResponse weatherResponse = App.get(context).getRepositoryImpl().getDataFromCache(context);
        view.updateWeatherParameters(weatherResponse);
    }
}
