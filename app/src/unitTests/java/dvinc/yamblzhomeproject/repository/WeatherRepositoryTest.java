package dvinc.yamblzhomeproject.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
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

        when(weatherApi.getWeather(anyString(), anyString(), anyString())).thenReturn(api);
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
        Observable<WeatherResponse> api = Observable.just(mock(WeatherResponse.class));

        when(weatherApi.getWeather(anyString(), anyString(), anyString())).thenReturn(Observable.concat(db, api));
        when(settings.getCurrentCityLocationLat()).thenReturn("12");
        when(settings.getCurrentCityLocationLong()).thenReturn("12");

        TestObserver<WeatherResponse> observer = TestObserver.create();
        repository.getData().subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(2);
    }

    @Test
    public void shouldReturnApiOnlyWhenNoCache() {
        Observable<WeatherResponse> api = Observable.just(mock(WeatherResponse.class));

        when(weatherApi.getWeather(anyString(), anyString(), anyString())).thenReturn(api);
        when(settings.getCurrentCityLocationLat()).thenReturn("12");
        when(settings.getCurrentCityLocationLong()).thenReturn("12");

        TestObserver<WeatherResponse> observer = TestObserver.create();
        repository.getData().subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }
}