package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import javax.inject.Inject;

import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "21cd7fe880c848c7d533498d2413f293";

    @Inject
    Settings settings;
    @Inject
    WeatherApi weatherApi;

    @Inject
    public WeatherRepositoryImpl(){
        
    }

    @Override
    public WeatherResponse getDataFromCache() {
        return settings.getWeather();
    }

    @Override
    public Observable<WeatherResponse> getDataFromWeb() {
        Observable<WeatherResponse> internetObserbavle = weatherApi.getTranslate(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY);
        Observable<WeatherResponse> dbObservable = Observable.just(settings.getWeather());
        return Observable.concat(internetObserbavle, dbObservable);
    }

    @Override
    public Observable<WeatherResponse> updateWeatherData() {
        return weatherApi.getTranslate(settings.getCurrentCityLocationLat(), settings.getCurrentCityLocationLong(), API_KEY);
    }
}
