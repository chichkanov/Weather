package dvinc.yamblzhomeproject.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.util.Log;

import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.repository.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.repository.model.weather.hourForecast.WeatherForecastHourlyResponse;
import dvinc.yamblzhomeproject.utils.WeatherUtils;
import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "b3e459e1e28f0b5cea62bc2e066ab5ff3";
    private static final String AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST = "16";
    private static final String AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST = "8";

    private WeatherApi weatherApi;
    private AppDatabase database;
    private CityEntity cityEntity;

    public WeatherRepositoryImpl(WeatherApi weatherApi, AppDatabase appDatabase) {
        this.weatherApi = weatherApi;
        this.database = appDatabase;
        Log.i("WeatherRepo", "CONSTRUCT");
        this.database.cityDao().getActiveCityFlowable()
                .subscribe(next -> {
                    Log.i("WeatherRepo", "NEW CITY");
                    this.cityEntity = next;
                    Log.i("WeatherRepo", cityEntity.getCityTitle());
                }, error -> Log.e("WeatherRepo", "NEW CITY"));
    }

    @Override
    public Single<WeatherCombiner> getWeatherData() {
        return getWeatherFromApi();
    }

    @Override
    public Single<WeatherForecastDailyResponse> getDailyForecast() {
        return weatherApi.getDailyForecast(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST);
    }

    @Override
    public Single<WeatherForecastHourlyResponse> getHourlyForecast() {
        return weatherApi.getHourForecast(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST);
    }

    @Override
    public Single<WeatherResponse> getCurrentWeather() {
        return weatherApi.getCurrentWeather(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale());
    }

    @Override
    public Single<WeatherCombiner> getWeatherFromApi() {
        return Single.zip(getCurrentWeather(), getHourlyForecast(), getDailyForecast(), WeatherCombiner::new);
    }

    @Override
    public Single<WeatherCombiner> getWeatherFromDb() {
        return Single.defer(() -> Single.just(database.weatherDao()
                .getWeatherForCityId(cityEntity.getCityId())
                .getWeatherCombiner()));
    }

}
