package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import javax.inject.Inject;

import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import dvinc.yamblzhomeproject.utils.WeatherUtils;
import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";
    private static final String AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST = "16";

    @Inject
    Settings settings;
    @Inject
    WeatherApi weatherApi;

    @Inject
    public WeatherRepositoryImpl() {

    }

    @Override
    public Observable<WeatherCombiner> getData() {
        Observable<WeatherResponse> observableCurrentWeather = weatherApi.getCurrentWeather(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY, WeatherUtils.getLocale());
        Observable<WeatherForecastDailyResponse> observableHourForecastWeather = weatherApi.getHourForecast(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST);

        Observable<WeatherCombiner> observableCombined = Observable.zip(observableCurrentWeather, observableHourForecastWeather, WeatherCombiner::new);

        WeatherResponse cache = settings.getWeather();

        return observableCombined;

    }

    @Override
    public Observable<WeatherResponse> updateWeatherData() {
        return weatherApi.getCurrentWeather(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY, WeatherUtils.getLocale());
    }
}
