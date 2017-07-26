package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;

public interface CallbackWeather {
    void onSuccess(WeatherResponse weatherResponse);

    void onError();
}
