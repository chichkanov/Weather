package dvinc.yamblzhomeproject.ui.weather;
/*
 * Created by DV on Space 5 
 * 20.07.2017
 */

import com.arellomobile.mvp.MvpView;

import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;

interface WeatherView extends MvpView {

    void updateWeatherCurrent(WeatherResponse weatherData);

    void updateWeatherHourly(WeatherForecastHourlyResponse weatherForecastHourlyResponse);

    void updateWeatherDaily(WeatherForecastDailyResponse weatherForecastDailyResponse);

    void updateLastUpdateTime(String date);

    void showCityName(String title);

    void showError();

    void showLoading();

    void hideLoading();

}
