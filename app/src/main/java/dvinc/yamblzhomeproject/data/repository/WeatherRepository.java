package dvinc.yamblzhomeproject.data.repository;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/*
 * Created by DV on Space 5 
 * 19.07.2017
 */
public interface WeatherRepository {

    Flowable<WeatherCombiner> getWeatherData(CityEntity cityEntity);

    Single<List<DailyWeatherUi>> getDailyForecast(CityEntity cityEntity);

    Single<List<HourlyWeatherUi>> getHourlyForecast(CityEntity cityEntity);

    Single<CurrentWeatherUi> getCurrentWeather(CityEntity cityEntity);

    Single<WeatherCombiner> getWeatherFromApi(CityEntity cityEntity);

    Maybe<WeatherCombiner> getWeatherFromDb(CityEntity cityEntity);

    Completable updateAllWeather();
}
