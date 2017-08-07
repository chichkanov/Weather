package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;
import io.reactivex.Single;

/*
 * Created by DV on Space 5 
 * 19.07.2017
 */
public interface WeatherRepository {

    Single<WeatherCombiner> getWeatherData();

    Single<WeatherForecastDailyResponse> getDailyForecast();

    Single<WeatherForecastHourlyResponse> getHourlyForecast();

    Single<WeatherResponse> getCurrentWeather();

    Single<WeatherCombiner> getWeatherFromApi();

    Single<WeatherCombiner> getWeatherFromDb();

}
