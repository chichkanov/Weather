package dvinc.yamblzhomeproject.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import dvinc.yamblzhomeproject.data.model.predictions.predictionInfo.Location;
import dvinc.yamblzhomeproject.data.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.data.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.data.model.weather.dailyForecast.WeatherForecastDailyResponse;
import dvinc.yamblzhomeproject.data.model.weather.hourForecast.WeatherForecastHourlyResponse;
import dvinc.yamblzhomeproject.data.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.data.uiModel.CurrentWeatherUi;
import dvinc.yamblzhomeproject.data.uiModel.mapper.WeatherMapper;
import dvinc.yamblzhomeproject.db.AppDatabase;
import dvinc.yamblzhomeproject.db.dao.WeatherDao;
import dvinc.yamblzhomeproject.db.entities.CityEntity;
import dvinc.yamblzhomeproject.db.entities.WeatherEntity;
import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.utils.converter.WeatherConverter;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherRepositoryTest {

    @Mock
    WeatherApi weatherApi;
    @Mock
    AppDatabase database;
    @Mock
    WeatherMapper weatherMapper;
    @Mock
    WeatherConverter weatherConverter;

    @InjectMocks
    WeatherRepositoryImpl weatherRepository;

    private CityEntity cityEntity;
    private WeatherEntity weatherEntity;
    private WeatherCombiner weatherCombiner;

    @Before
    public void setUp() throws Exception {
        cityEntity = new CityEntity(new Location(12.2, 12.3), "id", "Title", false);
        weatherCombiner = new WeatherCombiner(new CurrentWeatherUi(),
                new ArrayList<>(), new ArrayList<>());
        weatherEntity = new WeatherEntity();
        weatherEntity.setWeatherCombiner(weatherCombiner);

        when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(new WeatherResponse()));
        when(weatherApi.getHourForecast(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(new WeatherForecastHourlyResponse()));
        when(weatherApi.getDailyForecast(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(new WeatherForecastDailyResponse()));


        when(weatherMapper.transformCurrentWeather(any())).thenReturn(new CurrentWeatherUi());
        when(weatherMapper.transformDailyWeather(any())).thenReturn(new ArrayList<>(1));
        when(weatherMapper.transformHourWeather(any())).thenReturn(new ArrayList<>(1));
        when(weatherConverter.convert(weatherCombiner)).thenReturn(weatherCombiner);
        when(database.weatherDao()).thenReturn(mock(WeatherDao.class));
    }

    @Test
    public void shouldLoadFromDbAndApi() {
        when(database.weatherDao().getWeatherForCityId(anyString())).thenReturn(Maybe.just(new WeatherEntity()));
        TestSubscriber<WeatherCombiner> testObserver = weatherRepository.getWeatherData(cityEntity).test();
        testObserver.assertSubscribed();
        verify(weatherApi, times(1)).getCurrentWeather(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void shouldLoadFromApi() {
        TestObserver<WeatherCombiner> testObserver = weatherRepository.getWeatherFromApi(cityEntity).test();
        testObserver.assertSubscribed();
        verify(weatherConverter, times(1)).convert(any(WeatherCombiner.class));
    }

    @Test
    public void shouldLoadFromDb() {
        when(database.weatherDao().getWeatherForCityId(anyString())).thenReturn(Maybe.just(weatherEntity));
        TestObserver<WeatherCombiner> testObserver = weatherRepository.getWeatherFromDb(cityEntity).test();

        testObserver.assertSubscribed()
                .assertNoErrors()
                .assertValueCount(1);
        verify(weatherConverter, times(1)).convert(any(WeatherCombiner.class));
    }

    @Test
    public void shouldLoadCurrentWeatherWhenNetworkOn() {
        TestObserver<CurrentWeatherUi> testObserver = weatherRepository.getCurrentWeather(cityEntity).test();
        verify(weatherApi).getCurrentWeather(anyString(), anyString(), anyString(), anyString());

        testObserver.assertSubscribed()
                .assertNoErrors()
                .assertValueCount(1);

        verify(weatherMapper, times(1)).transformCurrentWeather(any(WeatherResponse.class));
    }

    @Test
    public void shouldLoadHourlyWeatherWhenNetworkOn() {
        TestObserver testObserver = weatherRepository.getHourlyForecast(cityEntity).test();
        verify(weatherApi).getHourForecast(anyString(), anyString(), anyString(), anyString(), anyString());

        testObserver.assertSubscribed()
                .assertNoErrors()
                .assertValueCount(1);
        Assert.assertEquals(1, testObserver.values().size());
        verify(weatherMapper, times(1)).transformHourWeather(any(WeatherForecastHourlyResponse.class));
    }

    @Test
    public void shouldLoadDailyWeatherWhenNetworkOn() {
        TestObserver testObserver = weatherRepository.getDailyForecast(cityEntity).test();
        verify(weatherApi).getDailyForecast(anyString(), anyString(), anyString(), anyString(), anyString());

        testObserver.assertSubscribed()
                .assertNoErrors()
                .assertValueCount(1);
        Assert.assertEquals(1, testObserver.values().size());
        verify(weatherMapper, times(1)).transformDailyWeather(any(WeatherForecastDailyResponse.class));
    }

}