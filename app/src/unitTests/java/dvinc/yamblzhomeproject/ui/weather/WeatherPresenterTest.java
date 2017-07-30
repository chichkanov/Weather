package dvinc.yamblzhomeproject.ui.weather;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import dvinc.yamblzhomeproject.di.AppComponent;
import dvinc.yamblzhomeproject.di.TestComponent;
import dvinc.yamblzhomeproject.di.TestComponentRule;
import dvinc.yamblzhomeproject.repository.WeatherRepositoryImpl;
import dvinc.yamblzhomeproject.repository.model.weather.WeatherResponse;
import dvinc.yamblzhomeproject.utils.Settings;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class WeatherPresenterTest {

    @Rule
    public TestComponentRule testComponentRule = new TestComponentRule(testAppComponent());

    @Mock
    WeatherView$$State weatherViewState;
    @Mock
    WeatherRepositoryImpl repository;
    @Mock
    Settings settings;

    private WeatherPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new WeatherPresenter();
        presenter.setViewState(weatherViewState);

        RxAndroidPlugins.setMainThreadSchedulerHandler(v -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(v -> Schedulers.trampoline());
    }

    @Test
    public void shouldLoadAndShowWeatherWhenNetworkOnAndCacheExist() {
        WeatherResponse response = mock(WeatherResponse.class);
        WeatherResponse response2 = mock(WeatherResponse.class);
        Observable<WeatherResponse> db = Observable.just(response);
        Observable<WeatherResponse> api = Observable.just(response2);

        when(repository.getData()).thenReturn(Observable.concat(db, api));
        presenter.getWeather();

        verify(repository).getData();
        verify(weatherViewState).updateWeatherParameters(any());
    }

    @Test
    public void shouldNotCrashOnFirstStartWithoutInternet() {
        Observable<WeatherResponse> api = Observable.error(new Throwable());

        when(repository.getData()).thenReturn(api);
        presenter.getWeather();

        verify(repository).getData();
        verify(weatherViewState).showError();
    }

    @Test
    public void shouldShowErrorWhenNetworkError() {
        when(repository.getData()).thenReturn(Observable.error(new Throwable()));

        presenter.getWeather();

        verify(repository).getData();
        verify(weatherViewState, only()).showError();
    }

    @Test
    public void shouldNotCrashWhenViewDetached(){
        when(repository.getData()).thenReturn(Observable.error(new Throwable()));

        presenter.getWeather();
        presenter.detachView(weatherViewState);
        presenter.destroyView(weatherViewState);

        verify(weatherViewState).showError();
    }

    private AppComponent testAppComponent() {
        return new TestComponent() {
            @Override
            public void inject(WeatherPresenter weatherPresenter) {
                weatherPresenter.repository = repository;
            }
        };
    }

}