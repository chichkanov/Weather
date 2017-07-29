package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.repository.CallbackWeather;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    @Inject
    WeatherRepositoryImpl repository;

    WeatherPresenter(){
        App.getComponent().inject(this);
    }

    void getWeatherFromInternet() {
        repository.getDataFromWeb(new CallbackWeather() {
            @Override
            public void onSuccess(WeatherResponse weatherResponse) {
                getViewState().updateWeatherParameters(weatherResponse);
            }

            @Override
            public void onError() {
                getViewState().showError("Network error");
            }
        });
    }

    void getWeatherDataFromCache() {
        WeatherResponse weatherResponse = repository.getDataFromCache();
        getViewState().updateWeatherParameters(weatherResponse);
    }
}
