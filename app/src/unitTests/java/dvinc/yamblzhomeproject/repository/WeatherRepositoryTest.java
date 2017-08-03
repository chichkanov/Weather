package dvinc.yamblzhomeproject.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherRepositoryTest {

    @Mock
    WeatherApi weatherApi;
    @Mock
    Settings settings;

    @InjectMocks
    WeatherRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldUpdateWhenNetworkOk() {
        Observable<WeatherResponse> api = Observable.just(mock(WeatherResponse.class));

        when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString())).thenReturn(api);
        when(settings.getCurrentCityLocationLat()).thenReturn("12");
        when(settings.getCurrentCityLocationLong()).thenReturn("12");

        TestObserver<WeatherResponse> observer = TestObserver.create();
        repository.updateWeatherData().subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }

    @Test
    public void shouldReturnDataWhenCacheAndNetworkOn() {
        Observable<WeatherResponse> db = Observable.just(mock(WeatherResponse.class));
        Observable<WeatherCombiner> api = Observable.just(mock(WeatherCombiner.class));

        when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString())).thenReturn(db);
        when(settings.getCurrentCityLocationLat()).thenReturn("12");
        when(settings.getCurrentCityLocationLong()).thenReturn("12");

        TestObserver<WeatherCombiner> observer = TestObserver.create();
        repository.getData().subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(2);
    }

    @Test
    public void shouldReturnApiOnlyWhenNoCache() {
        Observable<WeatherResponse> api = Observable.just(mock(WeatherResponse.class));

        when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString())).thenReturn(api);
        when(settings.getCurrentCityLocationLat()).thenReturn("12");
        when(settings.getCurrentCityLocationLong()).thenReturn("12");

        TestObserver<WeatherCombiner> observer = TestObserver.create();
        repository.getData().subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }
}