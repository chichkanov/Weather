package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import com.arellomobile.mvp.MvpView;

import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

interface WeatherView extends MvpView {

    void updateWeatherParameters(WeatherResponse weatherData);

    void showError();

}
