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
import timber.log.Timber;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String API_KEY = "b3e459e1e28f0b5cea62bc2e066ab5ff";
    private static final String AMOUNT_OF_ELEMENTS_IN_HOUR_FORECAST = "16";
    private static final String AMOUNT_OF_ELEMENTS_IN_DAILY_FORECAST = "8";

    private WeatherApi weatherApi;
    private AppDatabase database;
    private CityEntity cityEntity;
    private WeatherMapper weatherMapper;
    private WeatherConverter weatherConverter;

    public WeatherRepositoryImpl(WeatherApi weatherApi, AppDatabase appDatabase, WeatherMapper weatherMapper, WeatherConverter weatherConverter) {
        this.weatherApi = weatherApi;
        this.database = appDatabase;
        this.weatherMapper = weatherMapper;
        this.weatherConverter = weatherConverter;

        this.database.cityDao().getActiveCityFlowable()
                .subscribe(next -> {
                    this.cityEntity = next;
                    Timber.d("Current city in weather is %s", cityEntity.getCityTitle());
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
                    return weatherConverter.convert(mapper);
                });
    }

    @Override
    public Maybe<WeatherCombiner> getWeatherFromDb() {
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
