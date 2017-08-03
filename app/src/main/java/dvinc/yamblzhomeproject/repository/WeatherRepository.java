package dvinc.yamblzhomeproject.repository;

import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import io.reactivex.Observable;

/*
 * Created by DV on Space 5 
 * 19.07.2017
 */
public interface WeatherRepository {

    Observable<WeatherCombiner> getData();

    Observable<WeatherResponse> updateWeatherData();
}
