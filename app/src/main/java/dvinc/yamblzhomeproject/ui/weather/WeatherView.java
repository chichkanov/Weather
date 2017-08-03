package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import com.arellomobile.mvp.MvpView;

import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastDailyResponse;

interface WeatherView extends MvpView {

    void updateWeatherParameters(WeatherResponse weatherData);

    void updateWeatherHourly(WeatherForecastDailyResponse weatherForecastDailyResponse);

    void showError();

    void showLoading();

    void hideLoading();

}
