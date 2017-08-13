package dvinc.yamblzhomeproject.data.repository;
/*
 * Created by DV on Space 5 
 * 19.07.2017
 */

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
import dvinc.yamblzhomeproject.utils.converter.WeatherConverter;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "b3e459e1e28f0b5cea62bc2e066ab5ff";
    private static final String AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST = "16";
    private static final String AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST = "8";

    private WeatherApi weatherApi;
    private AppDatabase database;
    private WeatherMapper weatherMapper;
    private WeatherConverter weatherConverter;

    public WeatherRepositoryImpl(WeatherApi weatherApi, AppDatabase appDatabase, WeatherMapper weatherMapper, WeatherConverter weatherConverter) {
        this.weatherApi = weatherApi;
        this.database = appDatabase;
        this.weatherMapper = weatherMapper;
        this.weatherConverter = weatherConverter;
    }

    @Override
    public Flowable<WeatherCombiner> getWeatherData(CityEntity cityEntity) {
        return Maybe.concat(getWeatherFromDb(cityEntity), getWeatherFromApi(cityEntity).toMaybe());
    }

    @Override
    public Single<List<DailyWeatherUi>> getDailyForecast(CityEntity cityEntity) {
        return weatherApi.getDailyForecast(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST)
                .map(weather -> weatherMapper.transformDailyWeather(weather));
    }

    @Override
    public Single<List<HourlyWeatherUi>> getHourlyForecast(CityEntity cityEntity) {
        return weatherApi.getHourForecast(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale(), AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST)
                .map(weather -> weatherMapper.transformHourWeather(weather));
    }

    @Override
    public Single<CurrentWeatherUi> getCurrentWeather(CityEntity cityEntity) {
        return weatherApi.getCurrentWeather(String.valueOf(cityEntity.getLocation().getLatitude()),
                String.valueOf(cityEntity.getLocation().getLongitude()), API_KEY, WeatherUtils.getLocale())
                .map(weather -> weatherMapper.transformCurrentWeather(weather));
    }

    @Override
    public Single<WeatherCombiner> getWeatherFromApi(CityEntity cityEntity) {
        return Single.zip(getCurrentWeather(cityEntity),
                getHourlyForecast(cityEntity),
                getDailyForecast(cityEntity),
                WeatherCombiner::new)
                .map(mapper -> {
                    mapper.setUpdatedTime(System.currentTimeMillis());
                    database.weatherDao().insertWeather(new WeatherEntity(cityEntity.getCityId(), mapper));
                    return weatherConverter.convert(mapper);
                });
    }

    @Override
    public Maybe<WeatherCombiner> getWeatherFromDb(CityEntity cityEntity) {
        return database.weatherDao().getWeatherForCityId(cityEntity.getCityId())
                .map(weather -> weatherConverter.convert(weather.getWeatherCombiner()));
    }

    @Override
    public Completable updateAllWeather() {
        return Completable.fromAction(() -> {
            List<CityEntity> cityEntities = database.cityDao().getAllCitiesSync();
            for (int i = 0; i < cityEntities.size(); i++) {
                database.weatherDao().getWeatherForCityId(cityEntities.get(i).getCityId())
                        .subscribe(weatherEntity -> database.weatherDao().updateWeather(weatherEntity));
            }
        });
    }

}
