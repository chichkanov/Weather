package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

/*
 * Created by DV on Space 5 
 * 19.07.2017
 */
public interface WeatherRepository {

    WeatherResponse getDataFromCache();

    void getDataFromWeb(CallbackWeather callbackWeather);

    void updateWeatherData();
}
