package dvinc.yamblzhomeproject.data.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

import android.util.Log;

import java.util.List;

import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.DailyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.HourlyWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.mapper.WeatherMapper;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.db.entities.WeatherEntity;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.utils.WeatherUtils;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "b3e459e1e28f0b5cea62bc2e066ab5ff";
    private static final String AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST = "16";
    private static final String AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST = "8";

    private WeatherApi weatherApi;
    private AppDatabase database;
    private CityEntity cityEntity;
    private WeatherMapper weatherMapper;

    public WeatherRepositoryImpl(WeatherApi weatherApi, AppDatabase appDatabase, WeatherMapper weatherMapper) {
        this.weatherApi = weatherApi;
        this.database = appDatabase;
        this.weatherMapper = weatherMapper;

        this.database.cityDao().getActiveCityFlowable()
                .subscribe(next -> {
                    this.cityEntity = next;
                    Log.i("WeatherRepositoryCity", cityEntity.getCityTitle());
                });
    }

    @Override
    public Flowable<WeatherCombiner> getWeatherData() {
        return Maybe.concat(getWeatherFromDb(), getWeatherFromApi().toMaybe());
    }

    @Override
    public Single<List<DailyWeatherUi>> getDailyForecast() {
        return weatherApi.getDailyForecast(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST)
                .map(weather -> weatherMapper.transformDailyWeather(weather));
    }

    @Override
    public Single<List<HourlyWeatherUi>> getHourlyForecast() {
        return weatherApi.getHourForecast(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST)
                .map(weather -> weatherMapper.transformHourWeather(weather));
    }

    @Override
    public Single<CurrentWeatherUi> getCurrentWeather() {
        return weatherApi.getCurrentWeather(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale())
                .map(weather -> weatherMapper.transformCurrentWeather(weather));
    }

    @Override
    public Single<WeatherCombiner> getWeatherFromApi() {
        return Single.zip(getCurrentWeather(), getHourlyForecast(), getDailyForecast(), WeatherCombiner::new)
                .map(mapper -> {
                    mapper.setUpdatedTime(System.currentTimeMillis());
                    database.weatherDao().insertWeather(new WeatherEntity(cityEntity.getCityId(), mapper));
                    return mapper;
                });
    }

    @Override
    public Maybe<WeatherCombiner> getWeatherFromDb() {
        return database.weatherDao().getWeatherForCityId(cityEntity.getCityId())
                .map(WeatherEntity::getWeatherCombiner);
    }

}
