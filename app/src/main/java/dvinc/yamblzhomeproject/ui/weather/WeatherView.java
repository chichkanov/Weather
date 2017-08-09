package dvinc.yamblzhomeproject.ui.weather;


import com.arellomobile.mvp.MvpView;

import java.util.List;

import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;

interface WeatherView extends MvpView {

    void updateWeatherCurrent(CurrentWeatherUi currentWeather);

    void updateWeatherHourly(List<HourlyWeatherUi> hourWeather);

    void updateWeatherDaily(List<DailyWeatherUi> dayWeather);

    void updateLastUpdateTime(long date);

    void showCityName();

    void showError();

    void showLoading();

    void hideLoading();

}
