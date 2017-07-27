package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dvinc.yamblzhomeproject.App;
import dvinc.yamblzhomeproject.repository.CallbackWeather;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

@InjectViewState
public class WeatherPresenterImpl extends MvpPresenter<WeatherView> {

    void getWeatherFromInternet(Context context) {
        App.get(context).getRepositoryImpl().getDataFromWeb(context, new CallbackWeather() {
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

    void getWeatherDataFromCache(Context context) {
        WeatherResponse weatherResponse = App.get(context).getRepositoryImpl().getDataFromCache(context);
        getViewState().updateWeatherParameters(weatherResponse);
    }
}
