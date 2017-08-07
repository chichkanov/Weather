package dvinc.yamblzhomeproject.ui.weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherCombiner;
import dvinc.yamblzhomeproject.repository.model.weather.current.WeatherResponse;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class WeatherPresenterTest {

    @Mock
    WeatherView$$State weatherViewState;
    @Mock
    WeatherRepositoryImpl repository;

    private WeatherPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter.setViewState(weatherViewState);

        RxAndroidPlugins.setMainThreadSchedulerHandler(v -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(v -> Schedulers.trampoline());
    }

    @Test
    public void shouldLoadAndShowWeatherWhenNetworkOnAndCacheExist() {
        WeatherResponse response = mock(WeatherResponse.class);
        WeatherCombiner response2 = mock(WeatherCombiner.class);
        Observable<WeatherResponse> db = Observable.just(response);
        Observable<WeatherCombiner> api = Observable.just(response2);

        //when(repository.getData()).thenReturn(api);
        presenter.getWeather();

        //verify(repository).getData();
        verify(weatherViewState).updateWeatherDaily(any());
    }

    @Test
    public void shouldNotCrashOnFirstStartWithoutInternet() {
        Observable<WeatherCombiner> api = Observable.error(new Throwable());

        //when(repository.getData()).thenReturn(api);
        presenter.getWeather();

        //verify(repository).getData();
        verify(weatherViewState).showError();
    }

    @Test
    public void shouldShowErrorWhenNetworkError() {
        //when(repository.getData()).thenReturn(Observable.error(new Throwable()));

        presenter.getWeather();

        //verify(repository).getData();
        verify(weatherViewState, only()).showError();
    }

    @Test
    public void shouldNotCrashWhenViewDetached(){
        //when(repository.getData()).thenReturn(Observable.error(new Throwable()));

        presenter.getWeather();
        presenter.detachView(weatherViewState);
        presenter.destroyView(weatherViewState);

        verify(weatherViewState).showError();
    }

}