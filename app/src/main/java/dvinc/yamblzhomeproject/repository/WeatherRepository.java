package dvinc.yamblzhomeproject.repository;

import android.content.Context;

import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

/*
 * Created by DV on Space 5 
 * 19.07.2017
 */
interface WeatherRepository {

    WeatherResponse getDataFromCache(Context context);

    void getDataFromWeb(Context context, CallbackWeather callbackWeather);

    void updateWeatherData(Context context);
}
