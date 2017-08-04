package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import javax.inject.Inject;

import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import dvinc.yamblzhomeproject.utils.WeatherUtils;
import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";
    private static final String AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST = "16";
    private static final String AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST = "8";

    @Inject
    Settings settings;
    @Inject
    WeatherApi weatherApi;
    @Inject
    AppDatabase database;

    @Inject
    public WeatherRepositoryImpl() {

    }

    @Override
    public Observable<WeatherCombiner> getData() {
        Observable<WeatherResponse> currentWeather = weatherApi.getCurrentWeather(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY, WeatherUtils.getLocale());

        Observable<WeatherForecastHourlyResponse> hourlyForecast = weatherApi.getHourForecast(settings.getCurrentCityLocationLat(),
                settings.getCurrentCityLocationLong(),
                API_KEY, WeatherUtils.getLocale(),
                AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST);

        Observable<WeatherForecastDailyResponse> dailyForecast = weatherApi.getDailyForecast(settings.getCurrentCityLocationLat(),
                settings.getCurrentCityLocationLong(),
                API_KEY, WeatherUtils.getLocale(),
                AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST);

        // Get data from db and api
        /*Observable<WeatherCombiner> observableDb = Observable.just(database.cityWeatherDao().getAll().get(0).getWeatherCombiner());
        return Observable.concat(observableDb, observableCombined);*/

        return Observable.zip(currentWeather, hourlyForecast, dailyForecast, WeatherCombiner::new);

    }

    @Override
    public Observable<WeatherResponse> updateWeatherData() {
        return weatherApi.getCurrentWeather(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY, WeatherUtils.getLocale());
    }
}
