package dvinc.yamblzhomeproject.ui.weather;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;

@StateStrategyType(AddToEndSingleStrategy.class)
interface WeatherView extends MvpView {

    void updateWeatherCurrent(CurrentWeatherUi currentWeather);

    void updateWeatherHourly(List<HourlyWeatherUi> hourWeather);

    void updateWeatherDaily(List<DailyWeatherUi> dayWeather);

    void updateLastUpdateTime(long date);

    void showCityName(String title);

    @StateStrategyType(SkipStrategy.class)
    void showError();

    void showLoading();

    void hideLoading();

}
