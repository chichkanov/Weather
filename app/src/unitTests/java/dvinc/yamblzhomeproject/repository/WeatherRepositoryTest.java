package dvinc.yamblzhomeproject.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dvinc.yamblzhomeproject.net.WeatherApi;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.mock;

public class WeatherRepositoryTest {

    @Mock
    WeatherApi weatherApi;

    @InjectMocks
    WeatherRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldUpdateWhenNetworkOk() {
        Observable<WeatherResponse> api = Observable.just(mock(WeatherResponse.class));

        //when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString())).thenReturn(api);


        TestObserver<WeatherResponse> observer = TestObserver.create();

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }

    @Test
    public void shouldReturnDataWhenCacheAndNetworkOn() {
        Observable<WeatherResponse> db = Observable.just(mock(WeatherResponse.class));
        Observable<WeatherCombiner> api = Observable.just(mock(WeatherCombiner.class));

        //when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString())).thenReturn(db);

        TestObserver<WeatherCombiner> observer = TestObserver.create();

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(2);
    }

    @Test
    public void shouldReturnApiOnlyWhenNoCache() {
        Observable<WeatherResponse> api = Observable.just(mock(WeatherResponse.class));

        //when(weatherApi.getCurrentWeather(anyString(), anyString(), anyString(), anyString())).thenReturn(api);

        TestObserver<WeatherCombiner> observer = TestObserver.create();

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValueCount(1);
    }
}