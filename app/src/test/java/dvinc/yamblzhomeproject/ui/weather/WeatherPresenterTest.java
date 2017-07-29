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

import static org.mockito.Mockito.mock;
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

    private WeatherPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new WeatherPresenter();
        presenter.setViewState(weatherViewState);
    }

    @Test
    public void shouldLoadAndShowWeatherFromCache() {
        WeatherResponse response = mock(WeatherResponse.class);
        when(repository.getDataFromCache()).thenReturn(response);
        presenter.getWeatherDataFromCache();
        verify(weatherViewState).updateWeatherParameters(response);
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