package dvinc.yamblzhomeproject.data.repository;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/*
 * Created by DV on Space 5 
 * 19.07.2017
 */
public interface WeatherRepository {

    Flowable<WeatherCombiner> getWeatherData();

    Single<List<DailyWeatherUi>> getDailyForecast();

    Single<List<HourlyWeatherUi>> getHourlyForecast();

    Single<CurrentWeatherUi> getCurrentWeather();

    Single<WeatherCombiner> getWeatherFromApi();

    Maybe<WeatherCombiner> getWeatherFromDb();

}
